package com.kolesnichenko.restcontrollers_js.service;

import com.kolesnichenko.restcontrollers_js.dao.RoleDao;
import com.kolesnichenko.restcontrollers_js.dao.UserDao;
import com.kolesnichenko.restcontrollers_js.model.AuthProvider;
import com.kolesnichenko.restcontrollers_js.model.Role;
import com.kolesnichenko.restcontrollers_js.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;
    private String DEFAULT_PASSWORD = "100";

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User save(User user) {

        if (user.getPassword().startsWith("$2a$")) {
            user.setPassword(userDao.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Set<Role> roleSet = new HashSet<>();
        if(user.getRoles() != null) {
            for(Role rl: user.getRoles()) {
                roleSet.add(roleDao.findRoleByName(rl.getName()));
            }
        }
        user.setRoles(roleSet);
        return userDao.save(user);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id).get();
    }

    @Override
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public void processOAuthPostLogin(String username, Map<String, Object> attributes) {
        System.out.println(attributes);
        User newUser = new User();
        newUser.setEmail(username);
        if (username.contains("@gmail.com")) {
            newUser.setAuthProvider(AuthProvider.GOOGLE);
            newUser.setName((String) attributes.get("given_name"));
            newUser.setSurname((String) attributes.get("family_name"));
        }
        newUser.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        newUser.setRoles(Collections.singleton(roleDao.findRoleByName("USER")));
        userDao.save(newUser);
    }






}
