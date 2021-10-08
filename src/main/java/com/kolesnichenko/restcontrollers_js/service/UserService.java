package com.kolesnichenko.restcontrollers_js.service;


import com.kolesnichenko.restcontrollers_js.model.Role;
import com.kolesnichenko.restcontrollers_js.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    public User findByEmail(String email);

    User save(User user);

    User findById(int id);

    Iterable<User> findAll();

    void deleteById(int id);



    void processOAuthPostLogin(String username, Map<String, Object> attributes);


}
