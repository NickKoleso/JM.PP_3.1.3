package com.kolesnichenko.restcontrollers_js.service;

import com.kolesnichenko.restcontrollers_js.dao.UserDao;
import com.kolesnichenko.restcontrollers_js.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserServiceImpl implements UserService{
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public User updateUser(User user) {
       if(user.getPassword().startsWith("$2a$")){
           user.setPassword(userDao.findByName(user.getName()).getPassword());
       }
       else {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
       }
       return userDao.save(user);
    }


}
