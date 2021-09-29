package com.kolesnichenko.restcontrollers_js.dao;


import com.kolesnichenko.restcontrollers_js.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends CrudRepository<User, Integer> {
    @Query("select u from User u JOIN FETCH u.roles where u.name =:name")
    User findByName(@Param("name") String name);
}
