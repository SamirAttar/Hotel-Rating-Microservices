/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RatingService.controller;

import com.RatingService.model.Rating;
import com.RatingService.service.RatingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 91976
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //http://localhost:8092/rating/save
    @PostMapping("/save")
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
        Rating newRating = ratingService.createRating(rating);
        return new ResponseEntity<>(newRating, HttpStatus.OK);
    }

    @GetMapping("/get")
    public List<Rating> getAllRating() {
        List<Rating> allRating = ratingService.getAllRating();

        return allRating;
    }

//    @GetMapping("/get/{userid}")
//    public List<Rating> getByUserId(@PathVariable Integer userid) {
//        List<Rating> ratingByUserId = ratingService.getRatingByUserId(userid);
//
//        return ratingByUserId;
//    }

    @GetMapping("/get/hotel/{hotelId}")
    public List<Rating> getByHotelId(@PathVariable Integer hotelId) {

        List<Rating> ratingByhotelId = ratingService.getRatingByhotelId(hotelId);
        return ratingByhotelId;
    }

}
