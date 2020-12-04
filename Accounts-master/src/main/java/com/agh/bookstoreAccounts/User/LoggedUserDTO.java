package com.agh.bookstoreAccounts.User;

import java.util.Set;

public class LoggedUserDTO {

    private Set<String> roles;
    private Long user_id;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
