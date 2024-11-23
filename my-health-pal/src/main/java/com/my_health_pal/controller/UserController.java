package com.my_health_pal.controller;

import com.my_health_pal.model.User;
import com.my_health_pal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }
}
