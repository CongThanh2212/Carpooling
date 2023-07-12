package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.*;
import com.carpooling.frequentroute.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class DataController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TripRepostory tripRepostory;

    @Autowired
    private GridTripRepostory gridTripRepostory;

    @Autowired
    private WaypointRepostory waypointRepostory;

    @Autowired
    private GridPointRepostory gridPointRepostory;

    @Autowired
    private FrequentRouteRepostory frequentRouteRepostory;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/account")
    @ResponseBody
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @GetMapping("/account/{account_id}")
    @ResponseBody
    public Account getAccountById(@PathVariable("account_id") int accountId) {
        return accountRepository.findById(accountId).get();
    }

    @PostMapping("/account")
    @ResponseBody
    public Account addAccount(@RequestBody Account account) {
        accountRepository.save(account);
        return account;
    }

    @GetMapping("/trip")
    @ResponseBody
    public List<Trip> getAllTrip() {
        return tripRepostory.findAll();
    }

    @GetMapping("/trip/{trip_id}")
    @ResponseBody
    public Trip getTripById(@PathVariable("trip_id") int tripId) {
        return tripRepostory.findById(tripId).get();
    }

    @GetMapping("/trip/account_owner")
    @ResponseBody
    public List<Trip> getTripByAccountId(@RequestParam int account_owner) {
        return tripRepostory.findAllByAccount_owner(account_owner);
    }

    @PostMapping("/trip")
    @ResponseBody
    public Trip addTrip(@RequestBody Trip tripBody) {
        tripRepostory.save(tripBody);
        return tripBody;
    }

    @GetMapping("/gridtrip")
    @ResponseBody
    public List<GridTrip> getAllGridTrip() {
        return gridTripRepostory.findAll();
    }

    @GetMapping("/gridtrip/{grid_trip_id}")
    @ResponseBody
    public GridTrip getGridTripById(@PathVariable("grid_trip_id") int gridtripId) {
        return gridTripRepostory.findById(gridtripId).get();
    }

    @GetMapping("/gridtrip/account_id")
    public List<GridTrip> getGridTripByAccountId(@RequestParam int account_id) {
        return gridTripRepostory.findAllByAccount_id(account_id);
    }

    @GetMapping("/gridtrip/trip_id")
    public List<GridTrip> getGridTripByTripId(@RequestParam int trip_id) {
        return gridTripRepostory.findAllByTrip_id(trip_id);
    }

    @GetMapping("/gridtrip/account_trip_id")
    public List<GridTrip> getGridTripByAccountIdAndTripId(@RequestParam int account_id, @RequestParam int trip_id) {
        return gridTripRepostory.findAllByAccount_idAndTrip_id(account_id, trip_id);
    }

    @PostMapping("/gridtrip")
    @ResponseBody
    public GridTrip addGridTrip(@RequestBody GridTrip gridTrip) {
        gridTripRepostory.save(gridTrip);
        return gridTrip;
    }

    @GetMapping("/waypoint")
    @ResponseBody
    public List<Waypoint> getAllWaypoint() {
        return waypointRepostory.findAll();
    }

    @GetMapping("/waypoint/{id}")
    @ResponseBody
    public Waypoint getWaypointById(@PathVariable("id") int id) {
        return waypointRepostory.findById(id).get();
    }

    @GetMapping("/waypoint/on_trip")
    @ResponseBody
    public List<Waypoint> getWaypointByTripId(@RequestParam("on_trip") int on_trip) {
        return waypointRepostory.findAllByOn_trip(on_trip);
    }

    @PostMapping("/waypoint")
    @ResponseBody
    public Waypoint addWaypoint(@RequestBody Waypoint waypoint) {
        waypointRepostory.save(waypoint);
        return waypoint;
    }

    @GetMapping("/gridpoint/{id}")
    @ResponseBody
    public GridPoint getGridpointById(@PathVariable("id") int id) {
        return gridPointRepostory.findById(id).get();
    }

    @GetMapping("/gridpoint/grid_trip_id")
    @ResponseBody
    public List<GridPoint> getGridPointByGridTripId(@RequestParam("grid_trip_id") int grid_trip_id) {
        return gridPointRepostory.findAllByGrid_trip_id(grid_trip_id);
    }

    @PostMapping("/gridpoint")
    @ResponseBody
    public GridPoint addGridpoint(@RequestBody GridPoint gridPoint) {
        gridPointRepostory.save(gridPoint);
        return gridPoint;
    }

    @GetMapping("/frequentroute")
    @ResponseBody
    public List<FrequentRoute> getAllFrequentRoute() {
        return frequentRouteRepostory.findAll();
    }

    @GetMapping("/frequentroute/{id}")
    @ResponseBody
    public FrequentRoute getFrequentRouteById(@PathVariable("id") int id) {
        return frequentRouteRepostory.findById(id).get();
    }

    @GetMapping("/frequentroute/account_id")
    @ResponseBody
    public List<FrequentRoute> getFrequentRouteByAccountId(@RequestParam int account_id) {
        return frequentRouteRepostory.findAllByAccount_id(account_id);
    }

    @PostMapping("/frequentroute")
    @ResponseBody
    public FrequentRoute addFrequentRoute(@RequestBody FrequentRoute frequentRoute) {
        frequentRouteRepostory.save(frequentRoute);
        return frequentRoute;
    }

    @GetMapping("/route")
    @ResponseBody
    public List<Route> getAllRoute() {
        return routeRepository.findAll();
    }

    @GetMapping("/route/{id}")
    @ResponseBody
    public Route getRouteById(@PathVariable("id") int id) {
        return routeRepository.findById(id).get();
    }

    @GetMapping("/route/account_id")
    @ResponseBody
    public List<Route> getRouteByAccountId(@RequestParam int account_id) {
        return routeRepository.findAllByAccount_id(account_id);
    }

    @GetMapping("/route/frequent_route_id")
    @ResponseBody
    public Route getRouteByFrequentRoute(@RequestParam int frequent_route_id) {
        return routeRepository.findAllByFrequent_route_id(frequent_route_id);
    }

    @PostMapping("/route")
    @ResponseBody
    public Route addRoute(@RequestBody Route route) {
        routeRepository.save(route);
        return route;
    }
}
