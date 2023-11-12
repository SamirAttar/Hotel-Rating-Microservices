/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RatingService.service.Impl;

import com.RatingService.DAO.RatingDAO;
import com.RatingService.model.Rating;
import com.RatingService.service.RatingService;
import java.io.Serial;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 91976
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDAO ratingDAO;

    @Override
    public Rating createRating(Rating rating) {
        return ratingDAO.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        List<Rating> rating = ratingDAO.findAll();
        return rating;
    }

//    @Override
//    public List<Rating> getRatingByUserId(Integer userid) {
//        List<Rating> findByUserId = ratingDAO.findByUserId(userid);
//        return findByUserId;
//
//    }

    @Override
    public List<Rating> getRatingByhotelId(Integer hotelId) {
        List<Rating> findByHotelId = ratingDAO.findByHotelId(hotelId);
        return findByHotelId;
    }

}
