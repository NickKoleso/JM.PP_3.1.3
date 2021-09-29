package com.kolesnichenko.restcontrollers_js.controller;

import com.kolesnichenko.restcontrollers_js.model.User;
import com.kolesnichenko.restcontrollers_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/rest")
@RestController
public class UserRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        final Iterable<User> users = userService.findAll();
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/get")
    public ResponseEntity<User> getUser(@RequestBody int id) {
        User user = this.userService.findById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @PutMapping(value = "/edit")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        if ("".equals(user.getStringRoles())) {
            user.setRoles(userService.findById(user.getId()).getRoles());
        }
        userService.save(user);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }
}


