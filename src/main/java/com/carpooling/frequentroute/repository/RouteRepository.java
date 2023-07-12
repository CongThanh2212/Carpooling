package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Route;
import com.carpooling.frequentroute.model.booked.BookedRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query(value = "select * from route where account_id = ?1", nativeQuery = true)
    List<Route> findAllByAccount_id(int account_id);

    @Query(value = "select * from route where frequent_route_id = ?1", nativeQuery = true)
    Route findAllByFrequent_route_id(int frequent_route_id);

    @Modifying
    @Query(value = "update route set is_shared = ?1, type_shared = ?2 where id = ?3 ", nativeQuery = true)
    void updateShare(int is_shared, String type, int id);

    @Modifying
    @Query(value = "update route set is_shared = ?1 where id = ?2 ", nativeQuery = true)
    void updateIsShared(int is_shared, int id);

    @Query(value = "select * from route where account_id = ?1 and address_start = ?2 and address_end = ?3", nativeQuery = true)
    List<Route> findRouteByRequest(int account_id, String address_start, String address_end);

    @Modifying
    @Query(value = "update route set group_id = ?1, cost = ?2, is_shared = ?3 where id = ?4", nativeQuery = true)
    void updateGroupAndCostAndShared(int groupId, double cost, int isShared, int routeId);

    @Modifying
    @Query(value = "update route set is_shared = 0, type_shared = 'none', group_id = null, cost = 0 where group_id = ?1", nativeQuery = true)
    void updateOffShareDriver(int groupId);

    @Modifying
    @Query(value = "update route set is_shared = 0, type_shared = 'none', group_id = 0, cost = 0.0 where id = ?1", nativeQuery = true)
    void updateOffShareById(int groupId);

    @Query(value = "select * from route where group_id = ?1 and account_id != ?2", nativeQuery = true)
    List<Route> findAllByGroupId(int groupId, int accountId);

    @Query(nativeQuery = true, name = "getAllBookedRouteByAccountId")
    List<BookedRoute> getAllBookedRouteByAccountId(@Param("accountId") int accountId);

    @Query(nativeQuery = true, name = "getAllRideByAccountId")
    List<BookedRoute> getAllRideByAccountId(@Param("accountId") int accountId);

    @Query(value = "select r.* from group_frequent as gf inner join route as r on gf.driver_route_id = r.id", nativeQuery = true)
    List<Route> getAllRide();
}
