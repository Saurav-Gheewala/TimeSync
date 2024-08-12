package com.example.demo.service;



import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ResponseEntity<Boolean> addUsers(User user)
    {
        if(userRepository.existsByEmail(user.getEmail()))
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            try {
                userRepository.save(user);
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();

                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
    }
    public ResponseEntity<List<User>> showUsers() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }
}
