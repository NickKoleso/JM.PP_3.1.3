package com.kolesnichenko.restcontrollers_js.dao;


import com.kolesnichenko.restcontrollers_js.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    @Query("select u from User u JOIN FETCH u.roles where u.email =:email")
    User findByEmail(@Param("email") String email);
}
