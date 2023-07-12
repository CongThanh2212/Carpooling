package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.*;
import com.carpooling.frequentroute.gripmap.MapUtility;
import com.carpooling.frequentroute.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.carpooling.frequentroute.gripmap.MapUtility.timeLate;

@RestController
@CrossOrigin
@RequestMapping("/share_frequent")
public class ShareFrequent {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FrequentRouteRepostory frequentRouteRepostory;

    @Autowired
    private FrequentPointRepostory frequentPointRepostory;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private GroupFrequentRepository groupFrequentRepository;

    @Autowired
    private RequestRideRepository requestRideRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private List<Route> driverRoute = new ArrayList<>();
    private List<Route> passengerRoute = new ArrayList<>();

    @Transactional
    @PutMapping("/route/is_shared")
    public void updateIsShared(@RequestParam int is_shared, @RequestParam String type_shared, @RequestParam int id, @RequestParam int account_id) {
        if (is_shared == 1) {
            routeRepository.updateShare(is_shared, type_shared, id);

            Account user = accountRepository.findById(account_id).get();
            Route currentRoute = routeRepository.findById(id).get();
            List<FrequentRoute> listFR = new ArrayList<>(); // List các account khác cx có frequent route

            if (type_shared.equals("Participant")) {
                listFR = frequentRouteRepostory.getFrequentRouteParticipant(account_id);
            } else if (type_shared.equals("Passenger")) {
                // Là passenger thì search frequent khác là participant / driver
                listFR = frequentRouteRepostory.getFrequentRoutePassengerAndDiver(account_id, "Driver");
            } else {
                // Là driver thì search frequent khác là participant / passenger
                listFR = frequentRouteRepostory.getFrequentRoutePassengerAndDiver(account_id, "Passenger");
            }

            if (!listFR.isEmpty()) {
                matchingRoute(currentRoute.getFrequent_route_id(), listFR, type_shared);
            }

            boolean flag_continue = true;
            // current user đc system chỉ định vai trò là passenger
            if (!driverRoute.isEmpty()) {
                for (Route route : driverRoute) {
                    int group_id = 0;
                    // If driver này chưa có chuyến thì tạo group_frequent
                    if (route.getGroup_id() == 0) {
                        Account driver = accountRepository.findById(route.getAccount_id()).get();
                        group_id = createGroupFrequent(driver, route);
                    }
                    // Tạo request cho current user
                    if (createRequestPassengerFrequent(user, currentRoute, group_id)) {
                        flag_continue = false;
                        break;
                    }
                }
            }

            // If flag_continue => có nghĩa là current user chưa đc serve
            // current user đc system chỉ định vai trò là driver
            if (!passengerRoute.isEmpty() && flag_continue) {

                int group_id = createGroupFrequent(user, currentRoute); // Tạo chuyến đi ms vs driver là current user
                for (Route route : passengerRoute) {
                    Account anotherUser = accountRepository.findById(route.getAccount_id()).get();
                    createRequestPassengerFrequent(anotherUser, route, group_id);
                }
            }
        } else {
            Account user = accountRepository.findById(account_id).get();
            Route currentRoute = routeRepository.findById(id).get();
            if (currentRoute.getIs_shared() == 1) {
                int group_id = currentRoute.getGroup_id();
                /*
                    if là trường hợp user đc chỉ định vai trò driver
                    else là trường hợp chưa đc ghép
                 */
                if (group_id != 0) {
                    groupFrequentRepository.deleteById(currentRoute.getGroup_id());
                }
                else routeRepository.updateShare(is_shared, type_shared, id);
            } else cancelShare2(currentRoute, user);
        }
    }


    private void matchingRoute(int currentFrId, List<FrequentRoute> listFR, String type_shared) {

        List<FrequentPoint> currentPoint = frequentPointRepostory.findAllByFrequentRoute(currentFrId);

        for (FrequentRoute fr : listFR) {

            List<FrequentPoint> anotherPoint = frequentPointRepostory.findAllByFrequentRoute(fr.getId());
            List<String> currentString = new ArrayList<>();
            List<String> anotherString = new ArrayList<>();
            MapUtility.covertFrequentPointToString(currentPoint, currentString);
            MapUtility.covertFrequentPointToString(anotherPoint, anotherString);
            if (type_shared.equals("Passenger")) {
                if (anotherString.containsAll(currentString)) {
                    Route route = routeRepository.findAllByFrequent_route_id(fr.getId());
                    driverRoute.add(route);
                }
            } else if (type_shared.equals("Driver")) {
                if (currentString.containsAll(anotherString)) {
                    Route route = routeRepository.findAllByFrequent_route_id(fr.getId());
                    passengerRoute.add(route);
                }
            } else {
                if (currentString.containsAll(anotherString)) {
                    Route route = routeRepository.findAllByFrequent_route_id(fr.getId());
                    passengerRoute.add(route);
                } else {
                    if (anotherString.containsAll(currentString)) {
                        Route route = routeRepository.findAllByFrequent_route_id(fr.getId());
                        driverRoute.add(route);
                    }
                }
            }
        }
    }

    public Boolean createRequestPassengerFrequent(Account user, Route currentRoute, int groupId) {
        double expected_time_origin = convertTimeFromStringToDouble(currentRoute.getTime_start());
        double expected_time_destination = convertTimeFromStringToDouble(currentRoute.getTime_end());
        // 1. Update schedule: origin and destination
        // 1.1 Check xem từ origin -> destination có vi phạm capacity không nếu không thì update capacity các point trong đó
        List<Schedule> schedules = scheduleRepository.findBetweenOriginAndDes(groupId, expected_time_origin, expected_time_destination);
        for (Schedule schedule : schedules) {
            if (schedule.getCapacity_available() < 1) return false;
        }
        scheduleRepository.updateCapacity(groupId, expected_time_origin, expected_time_destination);

        // 1.2 Create origin
        // Tìm point gần nhất với origin point cần insert để lấy ra capacity_available của point gần nhất đó
        Schedule scheduleNearest = scheduleRepository.findByGroupAndSort(groupId, expected_time_origin);
        // If point gần nhất không còn chỗ => Không thể insert origin của point hiện tại
        if (scheduleNearest.getCapacity_available() <= 0) return false;
        int capacityOrigin = scheduleNearest.getCapacity_available() - 1;
        double time_late_origin = timeLate(expected_time_origin);
        Schedule origin = new Schedule(groupId, user.getAccount_id(), currentRoute.getLat_start(), currentRoute.getLng_start(), time_late_origin, time_late_origin, 1, 1, capacityOrigin);

        // 1.3 Create destination
        // If ở giữa origin và destination không có point nào thì capacity of destination = capacity of origin + 1
        int capacityDes = capacityOrigin + 1;
        if (schedules.size() > 0) {
            Schedule scheduleNearestDes = scheduleRepository.findByGroupAndSort(groupId, expected_time_origin);
            capacityDes = scheduleNearestDes.getCapacity_available() + 1;
        }
        double time_late_destination = timeLate(expected_time_destination);
        Schedule destination = new Schedule(groupId, user.getAccount_id(), currentRoute.getLat_end(), currentRoute.getLng_end(), time_late_destination, expected_time_destination, 2, 1, capacityDes);


        // 1.4 Insert origin and destination vào schedule
        scheduleRepository.save(origin);
        scheduleRepository.save(destination);

        // 2. Update group_id, cost and is_shared of current user in route
        double cost = currentRoute.getLength_route() * MapUtility.COST_OF_KM;
        routeRepository.updateGroupAndCostAndShared(groupId, cost, 2, currentRoute.getId());

        return true;
    }

    // return về id của group frequent vừa create
    public int createGroupFrequent(Account user, Route currentRoute) {
        // 1. Create group_frequent
        GroupFrequent groupFrequent = groupFrequentRepository.save(new GroupFrequent(currentRoute.getId(), currentRoute.getLat_start(), currentRoute.getLng_start()));
        // 2. Update route of user
        double cost = currentRoute.getLength_route() * MapUtility.COST_OF_KM;
        routeRepository.updateGroupAndCostAndShared(groupFrequent.getId(), cost, 1, currentRoute.getId());

        // 3. Update schedule: origin and destination
        // 3.1 Create origin
        double expected_time_origin = convertTimeFromStringToDouble(currentRoute.getTime_start());
        double time_late_origin = timeLate(expected_time_origin);
        Schedule origin = new Schedule(groupFrequent.getId(), user.getAccount_id(), currentRoute.getLat_start(), currentRoute.getLng_start(), time_late_origin, expected_time_origin, 1, 1, user.getSeat() - 1);

        // 3.2 Create destination
        double expected_time_destination = convertTimeFromStringToDouble(currentRoute.getTime_end());
        double time_late_destination = timeLate(expected_time_destination);
        Schedule destination = new Schedule(groupFrequent.getId(), user.getAccount_id(), currentRoute.getLat_end(), currentRoute.getLng_end(), time_late_destination, expected_time_destination, 2, 1, user.getSeat());

        // 3.3 Insert origin and destination vào schedule
        scheduleRepository.save(origin);
        scheduleRepository.save(destination);

        return groupFrequent.getId();
    }

    private void cancelShare2(Route currentRoute, Account user) {
        List<Route> frequent = routeRepository.findAllByGroupId(currentRoute.getGroup_id(), user.getAccount_id());
        List<RequestRide> notFrequent = requestRideRepository.findAllByGroupId(currentRoute.getGroup_id());
        if ((frequent.size() + notFrequent.size()) == 1) groupFrequentRepository.deleteById(currentRoute.getGroup_id());
        else {
            routeRepository.updateOffShareById(currentRoute.getId());
            scheduleRepository.deleteOriginAndDestination(currentRoute.getGroup_id(), user.getAccount_id());
        }
    }

    private double convertTimeFromStringToDouble(String expectedTime) {
        LocalTime localTime = LocalTime.parse(expectedTime, DateTimeFormatter.ofPattern("HH:mm"));
        return localTime.getHour() + (localTime.getMinute() / 60.0);
    }
}
