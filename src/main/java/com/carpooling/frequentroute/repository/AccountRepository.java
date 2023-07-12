package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "select * from account where user_name = ?1", nativeQuery = true)
    List<Account> getAccountByName (String user_name);

    @Query(value = "select a.* from group_frequent as g " +
            "inner join route as r on g.driver_route_id = r.id " +
            "inner join account as a on a.account_id = r.account_id " +
            "where g.id = ?1", nativeQuery = true)
    Account getByGroupId(int group_id);
}