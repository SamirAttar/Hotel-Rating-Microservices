/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.HotelService.HotelService.DAO;

import com.HotelService.HotelService.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 91976
 */
public interface HotelDAO extends JpaRepository<Hotel, Integer>{
    
}
