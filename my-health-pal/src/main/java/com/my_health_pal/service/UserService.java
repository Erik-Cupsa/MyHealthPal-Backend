package com.my_health_pal.service;

import com.my_health_pal.model.User;
import com.my_health_pal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

//    public void deleteUser(Long id) {
//        User user = getUserById(id);
//        userRepository.delete(user);
//    }
}
