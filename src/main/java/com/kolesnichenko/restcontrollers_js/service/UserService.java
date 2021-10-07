package com.kolesnichenko.restcontrollers_js.service;


import com.kolesnichenko.restcontrollers_js.model.User;

public interface UserService {
    public User findByEmail(String email);

    User save(User user);

    User findById(int id);

    Iterable<User> findAll();

    void deleteById(int id);

    User updateUser(User user);
}
