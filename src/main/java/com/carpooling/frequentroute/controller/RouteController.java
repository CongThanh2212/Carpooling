package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.Account;
import com.carpooling.frequentroute.model.booked.BookedRequestRide;
import com.carpooling.frequentroute.model.booked.BookedResponse;
import com.carpooling.frequentroute.model.booked.BookedRoute;
import com.carpooling.frequentroute.model.ride.RideResponse;
import com.carpooling.frequentroute.repository.AccountRepository;
import com.carpooling.frequentroute.repository.RequestRideRepository;
import com.carpooling.frequentroute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RequestRideRepository requestRideRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/booked_by_id")
    @ResponseBody
    public List<BookedResponse> getAllBookedByAccountId(@RequestParam("account_id") int account_id) {
        List<BookedRoute> routes = routeRepository.getAllBookedRouteByAccountId(account_id);
        List<BookedRequestRide> requestRides = requestRideRepository.getAllBookedRequestByAccountId(account_id);

        List<BookedResponse> result = new ArrayList<>();
        for (BookedRoute r : routes) {
            Account driverAccount = accountRepository.getByGroupId(r.getGroup_id());
            Account driver = accountRepository.findById(driverAccount.getAccount_id()).get();
            result.add(new BookedResponse(r.getId(), account_id, r.getUser_name(), driver.getSeat(), r.getAddress_end(), r.getAddress_start(),
                    r.getGroup_id(), r.getTime_start(), "Every day", driver.getLicense_plate(), r.getCost(), 1, true));
        }
        for (BookedRequestRide r : requestRides) {
            Account driver = accountRepository.findById(r.getAccount_id()).get();
            boolean accepted = false;
            if (r.getGroup_id() != 0) accepted = true;
            result.add(new BookedResponse(r.getId(), account_id, r.getUser_name(), driver.getSeat(), r.getAddress_end(), r.getAddress_start(),
                    r.getGroup_id(), "", "Now", driver.getLicense_plate(), r.getCost(), r.getCapacity(), accepted));
        }
        return result;
    }

    @GetMapping("/ride_by_id")
    @ResponseBody
    public List<RideResponse> getAllRideByAccountId(@RequestParam("account_id") int account_id) {
        Account driver = accountRepository.findById(account_id).get();
        List<BookedRoute> routes = routeRepository.getAllRideByAccountId(account_id);
        List<RideResponse> result = new ArrayList<>();
        for (BookedRoute r : routes) {
            result.add(new RideResponse(r.getId(), driver.getUser_name(), r.getAddress_start(), r.getAddress_end(), "Every day",
                    r.getTime_start(), driver.getSeat(), driver.getAccount_id()));
        }
        return result;
    }

}
