package com.agh.bookstoreAccounts.Admin;

import com.agh.bookstoreAccounts.User.User;
import com.agh.bookstoreAccounts.User.UserRepository;
import com.agh.bookstoreAccounts.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private AdminFunc adminFunc;

  @GetMapping(value = "/admin/users/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getUsers(){
        return ResponseEntity.ok(adminFunc.getListOfUsers(true));
    }


    @GetMapping(value = "/admin/employees/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getEmployee() {
        return ResponseEntity.ok(adminFunc.getListOfUsers(false));
    }

    @GetMapping(value = "/admin/employees/{username}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getEmployeeDetails(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("name", user.getName());
        map.put("surname", user.getSurname());
        map.put("email", user.getEmail());
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/admin/employees/")
    @ResponseBody
    public ResponseEntity<String> registerUserAccount(@RequestBody User userData) {

        userService.save(userData, false);
        return ResponseEntity.ok("\"employeeStatus\": \"added\"");
    }

    @PutMapping(value = "/admin/employees")
    @ResponseBody
    public ResponseEntity<String> editEmployee(@RequestBody User userData) {
        User userToEdit = userRepository.findByUsername(userData.getUsername());

        if (userToEdit!= null) {
            userToEdit.setEmail(userData.getEmail());
            userToEdit.setName(userData.getName());
            userToEdit.setSurname(userData.getSurname());
            userService.save(userToEdit, false);

            return ResponseEntity.ok("\"editStatus\": \"done\"");
        }
        return ResponseEntity.ok("\"editStatus\": \"error\"");
    }

    @DeleteMapping(value = "/admin/employees/{username}")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> deleteEmployee(@PathVariable String username) {

        userRepository.deleteUserByUsername(username);

        if (userRepository.findByUsername(username) == null) {
            log.info("Employee deleted: " + username);
            return ResponseEntity.ok("{\"Status\": \"Success\"");
        }
        return ResponseEntity.ok("\"Status\": \"Error\"");
    }

}