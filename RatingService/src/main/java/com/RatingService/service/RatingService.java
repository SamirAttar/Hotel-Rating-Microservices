/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RatingService.service;

import com.RatingService.model.Rating;
import java.util.List;

/**
 *
 * @author 91976
 */
public interface RatingService {
    
    //Create 
    
    public Rating createRating(Rating rating);
    
    public List<Rating>getAllRating();
    
    //getAll By Rtaing By Using UserId
    public List<Rating>getRatingByUserId(Integer userId);
    
    
    //get all rating by user id
    
    public List<Rating>getRatingByhotelId(Integer hotelId);
    
}
