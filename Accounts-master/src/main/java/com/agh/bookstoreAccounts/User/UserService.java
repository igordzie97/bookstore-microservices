package com.agh.bookstoreAccounts.User;


import com.agh.bookstoreAccounts.Role.Role;

import java.util.List;


public interface UserService {
    void save(User user);
    void save(User user, boolean normalUser);
    User findByUsername(String username);
    List<User> findUsersByRoles(Role role);
}


