package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.*;
import com.carpooling.frequentroute.model.ride.RideResponse;
import com.carpooling.frequentroute.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.carpooling.frequentroute.gripmap.MapUtility.convertTimeFromDouble;
import static com.carpooling.frequentroute.gripmap.MapUtility.timeLate;

@RestController
@CrossOrigin
@RequestMapping("/request_ride")
public class RequestRideController {

    @Autowired
    private RequestRideRepository rideRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/basic_insert")
    @ResponseBody
    public RideResponse getCandidateRide(RequestRide request) {
        RequestRide req = rideRepository.save(request);
        List<Route> listRide = routeRepository.getAllRide();

        if (listRide.size() == 0) return null;
        double deltaDistance = -1;
        int indexOrigin = 0, indexDes = 0;
        Route rideOptimal = null;
        List<Schedule> scheduleOfOptimal = null;
        List<Edge> edgeList = new ArrayList<>();
        for (Route r : listRide) {
            List<Schedule> schedules = scheduleRepository.getByGroupId(r.getGroup_id());
            int length = schedules.size();
            // Không insert vào sau last point of schedule
            for (int i = 0; i < length - 1; i++) {
                for (int j = i ; j < length - 1; i++) {
                    if (i == j) {
                        Edge iToOrigin = edgeRepository.getDuration(schedules.get(i).getLat(), schedules.get(i).getLng(), req.getLat_origin(), req.getLng_origin());
                        Edge originToDes = edgeRepository.getDuration(req.getLat_origin(), req.getLng_origin(), req.getLat_destination(), req.getLng_destination());
                        Edge desToI1 = edgeRepository.getDuration(req.getLat_destination(), req.getLng_destination(), schedules.get(i + 1).getLat(), schedules.get(i + 1).getLng());
                        Edge iToI1 = edgeRepository.getDuration(schedules.get(i).getLat(), schedules.get(i).getLng(), schedules.get(i + 1).getLat(), schedules.get(i + 1).getLng());
                        // Check time, k cần check capacity
                        if (!checkTimeSpecialCase(schedules, i, req, iToOrigin, originToDes, desToI1)) continue;
                        edgeList.clear();
                        edgeList.add(iToOrigin);
                        edgeList.add(originToDes);
                        edgeList.add(desToI1);
                        edgeList.add(iToI1);
                    } else {
                        Edge iToOrigin = edgeRepository.getDuration(schedules.get(i).getLat(), schedules.get(i).getLng(), req.getLat_origin(), req.getLng_origin());
                        Edge originToI1 = edgeRepository.getDuration(req.getLat_origin(), req.getLng_origin(), schedules.get(i + 1).getLat(), schedules.get(i + 1).getLng());
                        Edge jToDes = edgeRepository.getDuration(schedules.get(j).getLat(), schedules.get(j).getLng(), req.getLat_destination(), req.getLng_destination());
                        Edge desToJ1 = edgeRepository.getDuration(req.getLat_destination(), req.getLng_destination(), schedules.get(j + 1).getLat(), schedules.get(j + 1).getLng());
                        Edge iToI1 = edgeRepository.getDuration(schedules.get(i).getLat(), schedules.get(i).getLng(), schedules.get(i + 1).getLat(), schedules.get(i + 1).getLng());
                        Edge jToJ1 = edgeRepository.getDuration(schedules.get(j).getLat(), schedules.get(j).getLng(), schedules.get(j + 1).getLat(), schedules.get(j + 1).getLng());
                        // Check time and capacity
                        if (!checkTime(schedules, i, j, req, iToOrigin, originToI1, jToDes, desToJ1) ||
                            !checkCapacity(schedules, i, j, req)) continue;
                        edgeList.clear();
                        edgeList.add(iToOrigin);
                        edgeList.add(originToI1);
                        edgeList.add(jToDes);
                        edgeList.add(desToJ1);
                        edgeList.add(iToI1);
                        edgeList.add(jToJ1);
                    }

                    double newDelta = calculatorDelta(i, j, edgeList);
                    if (deltaDistance == -1 || newDelta < deltaDistance) {
                        deltaDistance = newDelta;
                        rideOptimal = r;
                        scheduleOfOptimal = schedules;
                        indexOrigin = i;
                        indexDes = j;
                    }
                }
            }
        }
        if (deltaDistance == -1) {
            rideRepository.updateStatus(1, 0);
            return null;
        }
        insertSchedule(rideOptimal, indexOrigin, indexDes, req, scheduleOfOptimal, edgeList);
        Account driver = accountRepository.findById(rideOptimal.getAccount_id()).get();
        return new RideResponse(rideOptimal.getId(), driver.getUser_name(), req.getAddress_start(), req.getAddress_end(), "Now", convertTimeFromDouble(req.getPick_up_time()), driver.getSeat(), driver.getAccount_id());
    }

    private void insertSchedule(Route rideOptimal, int origin, int des, RequestRide req, List<Schedule> scheduleOfOptimal, List<Edge> edges) {
        // 1. Insert and update schedule table
        // 1.1 insert
        // Origin
        double expectedTimeOrigin = scheduleOfOptimal.get(origin).getExpected_time() + edges.get(0).getDuration(); // Calculator time
        int capacityOfOrigin = scheduleOfOptimal.get(origin).getCapacity_available() - req.getCapacity(); // Calculator capacity
        Schedule scheduleOrigin = new Schedule(rideOptimal.getGroup_id(), req.getPassenger_id(), req.getLat_origin(), req.getLng_origin(), timeLate(expectedTimeOrigin), expectedTimeOrigin, 1, 2, capacityOfOrigin);
        scheduleRepository.save(scheduleOrigin);
        // Destination
        if (origin == des) {
            double expectedTimeDes = expectedTimeOrigin + edges.get(1).getDuration();
            int capacityOfDes = capacityOfOrigin + req.getCapacity();
            Schedule scheduleDes = new Schedule(rideOptimal.getGroup_id(), req.getPassenger_id(), req.getLat_destination(), req.getLng_destination(), 24.0, expectedTimeDes, 2, 2, capacityOfDes);
            scheduleRepository.save(scheduleDes);
        } else {
            // insert
            double deltaTimeOrigin = edges.get(0).getDuration() + edges.get(1).getDuration() - edges.get(4).getDuration();
            double deltaTimeDes = edges.get(2).getDuration() + edges.get(3).getDuration() - edges.get(5).getDuration();
            double expectedTimeDes = scheduleOfOptimal.get(des).getExpected_time() + deltaTimeOrigin + edges.get(2).getDuration();
            int capacityOfDes = scheduleOfOptimal.get(des).getCapacity_available();
            Schedule scheduleDes = new Schedule(rideOptimal.getGroup_id(), req.getPassenger_id(), req.getLat_destination(), req.getLng_destination(), 24.0, expectedTimeDes, 2, 2, capacityOfDes);
            scheduleRepository.save(scheduleDes);

            // update
            for (int i = origin + 1; i <= des; i++) {
                scheduleRepository.updateCapacityAndTime(req.getCapacity(), deltaTimeOrigin, scheduleOfOptimal.get(i).getSchedule_id());
            }
            for (int i = des + 1; i < scheduleOfOptimal.size(); i++) {
                scheduleRepository.updateCapacityAndTime(- req.getCapacity(), deltaTimeOrigin + deltaTimeDes, scheduleOfOptimal.get(i).getSchedule_id());
            }
        }

        // 2. Update request_ride table
        rideRepository.updateStatus(2, rideOptimal.getGroup_id());
    }

    private double calculatorDelta(int origin, int destination, List<Edge> edges) {
        double deltaDistance = 0;
        if (origin == destination) {
            for (int i = 0; i < 3; i++) deltaDistance += edges.get(i).getDistance();
            deltaDistance -= edges.get(3).getDistance();
        } else {
            for (int i = 0; i < 4; i++) deltaDistance += edges.get(i).getDistance();
            deltaDistance = deltaDistance - edges.get(4).getDistance() - edges.get(5).getDistance();
        }
        return deltaDistance;
    }

    private boolean checkCapacity(List<Schedule> schedules, int origin, int destination, RequestRide request) {
        for (int i = origin + 1; i <= destination + 1; i++) {
            int check = schedules.get(i).getCapacity_available() - request.getCapacity();
            if (check < 0) return false;
        }
        return true;
    }

    private boolean checkTime(List<Schedule> schedules, int origin, int destination, RequestRide request, Edge iToOrigin, Edge originToI1, Edge jToDes, Edge desToJ1) {
        double deltaTimeOrigin = checkTimeOrigin(schedules, origin, destination, request, iToOrigin, originToI1);
        if (deltaTimeOrigin == - 1) return false;
        return checkTimeDestination(schedules, destination, deltaTimeOrigin, jToDes, desToJ1);
    }

    private boolean checkTimeSpecialCase(List<Schedule> schedules, int indexInsert, RequestRide request, Edge iToOrigin, Edge originToDes, Edge desToI1) {
        double previous = schedules.get(indexInsert).getExpected_time();
        double pickUp = request.getPick_up_time();
        if (previous >= pickUp) return false;

        double next = schedules.get(indexInsert + 1).getExpected_time();
        double deltaTime = iToOrigin.getDuration() + originToDes.getDuration() + desToI1.getDuration() - (next - previous);

        for (int i = indexInsert + 1; i < schedules.size(); i++) {
            double expectedTime = schedules.get(i).getExpected_time();
            double lateTime = schedules.get(i).getTime_late();
            if ((expectedTime + deltaTime) > lateTime) return false;
        }

        return true;
    }

    /*
        return -1: vi phạm ràng buộc time
        return != -1: time tăng thêm do insert origion
     */
    private double checkTimeOrigin(List<Schedule> schedules, int origin, int destination, RequestRide request, Edge iToOrigin, Edge originToI1) {
        double previousOrigin = schedules.get(origin).getExpected_time();
        double pickUp = request.getPick_up_time();
        if (previousOrigin >= pickUp) return -1;

        double nextSiblingOrigin = schedules.get(origin + 1).getExpected_time();
        double deltaTime = iToOrigin.getDuration() + originToI1.getDuration() - (nextSiblingOrigin - previousOrigin);

        for (int i = origin + 1; i <= destination; i++) {
            double expectedTime = schedules.get(i).getExpected_time();
            double lateTime = schedules.get(i).getTime_late();
            if ((expectedTime + deltaTime) > lateTime) return -1;
        }
        return deltaTime;
    }

    // Kiểm tra xem destination có vi phạm ràng buộc time không
    private boolean checkTimeDestination(List<Schedule> schedules, int destination, double deltaTimeOrigin, Edge jToDes, Edge desToJ1) {
        double previousDestination = schedules.get(destination).getExpected_time();
        double nextSiblingDestination = schedules.get(destination + 1).getExpected_time();
        double deltaTime = jToDes.getDuration() + desToJ1.getDuration() - (nextSiblingDestination - previousDestination) + deltaTimeOrigin;

        for (int i = destination + 1; i < schedules.size(); i++) {
            double expectedTime = schedules.get(i).getExpected_time();
            double lateTime = schedules.get(i).getTime_late();
            if ((expectedTime + deltaTime) > lateTime) return false;
        }
        return true;
    }
}
