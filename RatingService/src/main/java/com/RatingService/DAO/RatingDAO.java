/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RatingService.DAO;

import com.RatingService.model.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 91976
 */
public interface RatingDAO extends JpaRepository<Rating, Integer>{

    public List<Rating> findByUserId(Integer userId);

    public List<Rating> findByHotelId(Integer hotelId);
    
}
