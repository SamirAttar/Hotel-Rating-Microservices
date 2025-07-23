/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UserService.controller;

import com.UserService.model.User;
import com.UserService.service.UserService;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 91976
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    //http://localhost:8089/user/save
    @PostMapping("/save")
    public ResponseEntity<User> savesUser(@RequestBody User user) {
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<User> getAllUser() {
        List<User> allUser = userService.getAllUser();

        return allUser;

    }

    //http://localhost:8089/user/get/1
    @GetMapping("/get/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        User userById = userService.getUserById(id);
        log.info("user get by id is executed" + userById);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }


    // fall back method for CB
    public ResponseEntity<User> ratingHotelFallBack(Integer userId, Exception ex) {
        log.info("Fallback is executed because service is down", ex);
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about("This user is dummy boz some services down").id(12345).build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //http://localhost:8089/user/update
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updatePerson(@PathVariable Integer id, @RequestBody User user) {
        User newUser = userService.updateUser(id, user);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    //http://localhost:8089/user/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletById(@PathVariable Integer id) {

        userService.deleteUser(id);
        return new ResponseEntity<>("Data is deleted", HttpStatus.GONE);
    }

}
