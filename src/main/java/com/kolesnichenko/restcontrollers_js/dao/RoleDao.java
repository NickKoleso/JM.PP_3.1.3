package com.kolesnichenko.restcontrollers_js.dao;


import com.kolesnichenko.restcontrollers_js.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findRoleByName(String name);




}
