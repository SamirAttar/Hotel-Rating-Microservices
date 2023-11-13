/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UserService.service.Impl;

import com.UserService.DAO.UserDAO;
import com.UserService.model.Rating;
import com.UserService.model.User;
import com.UserService.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.UserService.model.Hotel;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 91976
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {

        return userDAO.save(user);

    }

    @Override
    public List<User> getAllUser() {
        List<User> user = userDAO.findAll();
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        User user = userDAO.findById(id).orElse(null);
        //fetch rating by above userId
        //http://localhost:8094/rating/get/user/2
        Rating[] forObject = restTemplate.getForObject("http://localhost:8094/rating/get/user/" + user.getId(), Rating[].class);
        logger.info("{}", forObject);

        
        List<Rating> ratings = Arrays.stream(forObject).toList();
        
        
        List<Rating> ratingList = ratings.stream().map(rating -> {

            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8091/hotel/get/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code:{}", forEntity.getStatusCode());

            rating.setHotel(hotel);
            return rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        userDAO.deleteById(id);
    }

    @Override
    public User updateUser(Integer id, User user) {
        return userDAO.findById(id)
                .map(existingPerson -> {
                    existingPerson.setName(user.getName());
                    existingPerson.setEmail(user.getEmail());
                    existingPerson.setAbout(user.getAbout());

                    return userDAO.save(existingPerson);
                })
                .orElseThrow(null);
    }
}
