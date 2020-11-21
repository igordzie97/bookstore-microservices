package com.agh.bookstoreAccounts.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Role: 1. admin 2. employee 3. normal_user

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    public void makeRoles(){
        Role admin = new Role();
        admin.setName("admin");

        Role employee = new Role();
        employee.setName("employee");

        Role normalUser = new Role();
        normalUser.setName("user");

        Role notRegisteredUser = new Role();
        notRegisteredUser.setName("notRegisteredUser");

        roleRepository.save(admin);
        roleRepository.save(employee);
        roleRepository.save(normalUser);
        roleRepository.save(notRegisteredUser);
    }
    public boolean checkRoles(){
        Role admin = roleRepository.findByName("admin");
        Role employee = roleRepository.findByName("employee");
        Role normalUser = roleRepository.findByName("user");
        Role notRegisteredUser = roleRepository.findByName("notRegisteredUser");

        if(admin != null & employee != null & normalUser != null & notRegisteredUser != null){
            return false;
        } else {
            return true;
        }

    }
}
