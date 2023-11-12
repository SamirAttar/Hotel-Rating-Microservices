/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.HotelService.HotelService.service;

import com.HotelService.HotelService.Model.Hotel;
import java.util.List;

/**
 *
 * @author 91976
 */
public interface HotelService {

    public Hotel saveHotel(Hotel hotel);

    public List<Hotel> getAllHotel();

    public Hotel getHotelById(Integer id);

    public void deleteHotel(Integer id);

    public Hotel updateUser(Integer id, Hotel hotel);

}
