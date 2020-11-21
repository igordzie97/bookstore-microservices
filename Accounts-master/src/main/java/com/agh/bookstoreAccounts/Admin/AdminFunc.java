package com.agh.bookstoreAccounts.Admin;


import java.util.List;
import java.util.Map;

public interface AdminFunc {
    void addAdminAccIfNotExist();
    List<Map<String, String>> getListOfUsers(boolean normalUser);
}
