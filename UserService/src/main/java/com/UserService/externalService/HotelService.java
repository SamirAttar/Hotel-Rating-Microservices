/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UserService.externalService;

import com.UserService.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author 91976
 */
@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {
    
    @GetMapping("/hotel/get/{hotelId}")
    Hotel getHotel(@PathVariable Integer hotelId);
    
}
