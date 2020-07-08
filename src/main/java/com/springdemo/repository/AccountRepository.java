package com.springdemo.repository;

import com.springdemo.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    // Method names should use special keywords such as “find”, “order” with the name of the variables

    //List<Account> findByUsername(String username);
    Account findByUsername(String username);

}
