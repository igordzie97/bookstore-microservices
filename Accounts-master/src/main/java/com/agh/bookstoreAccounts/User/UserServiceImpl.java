package com.agh.bookstoreAccounts.User;

import com.agh.bookstoreAccounts.Role.Role;
import com.agh.bookstoreAccounts.Role.RoleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public void save(User user) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(roleRepository.findAll()));
            userRepository.save(user);
        }

        @Override
        public void save(User user, boolean normalUser) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            HashSet<Role> hs = new HashSet<>();
            if(normalUser) {
                hs.add(roleRepository.findByName("user"));
            } else {
                hs.add(roleRepository.findByName("employee"));
            }
            user.setRoles(hs);
            userRepository.save(user);
            log.info("User Added");
        }

        @Override
        public User findByUsername(String username) {
            return userRepository.findByUsername(username);
        }

        @Override
        public List<User> findUsersByRoles(Role role){
            return userRepository.findUsersByRoles(role);
        }

}

