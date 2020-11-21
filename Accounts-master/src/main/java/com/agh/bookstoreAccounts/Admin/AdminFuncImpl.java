package com.agh.bookstoreAccounts.Admin;

import com.agh.bookstoreAccounts.Role.Role;
import com.agh.bookstoreAccounts.Role.RoleRepository;
import com.agh.bookstoreAccounts.User.User;
import com.agh.bookstoreAccounts.User.UserRepository;
import com.agh.bookstoreAccounts.User.UserService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AdminFuncImpl implements AdminFunc {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

   // private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    //dodawanie konta admina jeśli nie ma
    @Override
    public void addAdminAccIfNotExist(){
        if(userService.findByUsername("admin") == null){
            User admin = new User();
            HashSet<Role> hs = new HashSet<>();
            hs.add(roleRepository.findByName("admin"));
            admin.setRoles(hs);
            admin.setPassword("admin");
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
            userRepository.save(admin);
            log.info("Admin added");
        } else {
            log.info("Admin exists");
        }
    }

    @Override
    public List<Map<String, String>> getListOfUsers(boolean normalUser) {
        Role role = new Role();
        if(normalUser){
            role.setName("normal_user");
            role.setId(roleRepository.findByName("normal_user").getId());
        } else {
            role.setName("employee");
            role.setId(roleRepository.findByName("employee").getId());
        }
        Map<Integer, Map<String, String>> map = new HashMap<>();
        List<User> list = userService.findUsersByRoles(role);
        List<Map<String, String>> all = new ArrayList<>();
        for(User user : list){
            Map<String, String> helperMap = new HashMap<>();
            helperMap.put("username", user.getUsername());
            helperMap.put("surname", user.getSurname());
            helperMap.put("name", user.getName());
            all.add(helperMap);
        }
        return all;
    }
}
