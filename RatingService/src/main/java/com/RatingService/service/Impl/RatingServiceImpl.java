/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RatingService.service.Impl;

import com.RatingService.DAO.RatingDAO;
import com.RatingService.model.Rating;
import com.RatingService.service.RatingService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 91976
 */
@Service
@Slf4j
public class RatingServiceImpl implements RatingService{

    
    @Autowired
    private RatingDAO ratingDAO;
    
    
     @Override
    public Rating createRating(Rating rating) {
        log.info("RatingServiceImpl ::createRating method is executed");
        return ratingDAO.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        log.info("RatingServiceImpl :: getAllRating method is executed");
        List<Rating> rating = ratingDAO.findAll();
        return rating;
    }

    @Override
    public List<Rating> getRatingByUserId(Integer userId) {
        log.info("RatingServiceImpl :: getRatingByUserId method id executed ");
        List<Rating> findByUserId = ratingDAO.findByUserId(userId);
        return findByUserId;

    }

    @Override
    public List<Rating> getRatingByhotelId(Integer hotelId) {
        log.info("RatingServiceImpl :: getRatingByhotelId method id executed");
        List<Rating> findByHotelId = ratingDAO.findByHotelId(hotelId);
        return findByHotelId;
    }

    
}
