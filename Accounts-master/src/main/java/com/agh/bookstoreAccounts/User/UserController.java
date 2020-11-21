//package com.agh.bookstoreAccounts.User;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/user/{userID}")
//    public ResponseEntity<Map<String, String>> getUser (@PathVariable Long userID){
//        Map<String, String> map = new HashMap<>();
//        Optional<User> opt = userRepository.findById(userID);
//        if(opt.isPresent()){
//            User user = opt.get();
//            map.put("username", user.getUsername());
//            map.put("name", user.getName());
//            map.put("surname", user.getSurname());
//            map.put("email", user.getEmail());
//            return  ResponseEntity.ok(map);
//        } else {
//            map.put("username", "");
//            map.put("name", "");
//            map.put("surname", "");
//            map.put("email","");
//        }
//        return  ResponseEntity.ok(map);
//    }
//
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registration";
//    }
//
//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<String> register(@RequestBody User user) {
//        //userValidator.validate(userForm, bindingResult);
//
//        userService.save(user, true);
//
//        return ResponseEntity.ok("\"Status\":\"Added\"");
//    }
//
//    @PostMapping("/check/username")
//    @ResponseBody
//    public ResponseEntity<String> checkUsernameAvailable(@RequestBody User user){
//        if(userRepository.findByUsername(user.getUsername())==null){
//            return ResponseEntity.ok("\"true\"");
//        }
//        return ResponseEntity.ok("\"false\"");
//    }
//
//    @PostMapping("/check/email")
//    @ResponseBody
//    public ResponseEntity<String> checkEmailAvailable(@RequestBody User user){
//        if(userRepository.findByEmail(user.getEmail())==null){
//            return ResponseEntity.ok("\"true\"");
//        }
//        return ResponseEntity.ok("\"false\"");
//    }
//
//
//    @GetMapping("/rest/user/{userName}")
//    public long getUserLoggedID(@PathVariable String userName) {
//        return userRepository.findByUsername(userName).getId();
//    }
//
//    @GetMapping("/rest/user/counter/username/{userName}")
//    public int getReservationByUsername(@PathVariable String userName) {
//        return userRepository.findByUsername(userName).getNumberOfReservations();
//    }
//
//    @GetMapping("/rest/user/counter/userid/{userID}")
//    public int getReservationByUserId(@PathVariable Long userID) {
//        return userRepository.findById(userID).get().getNumberOfReservations();
//    }
//}
