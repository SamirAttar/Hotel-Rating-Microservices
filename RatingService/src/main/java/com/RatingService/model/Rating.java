/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RatingService.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author 91976
 */

@Entity
@Table(name = "m_rating")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingId;
    private Integer userId;
    private Integer hotelId;
    private Integer rating;
    private String feedBack;
  
    
    

    public Rating(Integer ratingId, Integer userId, Integer hotelId, Integer rating, String feedBack) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.feedBack = feedBack;
    }

    public Rating() {
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

}
