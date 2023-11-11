/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.HotelService.HotelService.service.Impl;

import com.HotelService.HotelService.DAO.HotelDAO;
import com.HotelService.HotelService.Model.Hotel;
import com.HotelService.HotelService.service.HotelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 91976
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDAO hotelDAO;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelDAO.save(hotel);

    }

    @Override
    public List<Hotel> getAllHotel() {
        List<Hotel> findAll = hotelDAO.findAll();
        return findAll;
    }

    @Override
    public Hotel getHotelById(Integer id) {
        Hotel hotel = hotelDAO.findById(id).orElseThrow(null);
        return hotel;

    }

    @Override
    public void deleteHotel(Integer id) {

        hotelDAO.deleteById(id);
    }

    @Override
    public Hotel updateUser(Integer id, Hotel hotel) {
        Hotel orElseThrow = hotelDAO.findById(id).map(exixtingHotel
                -> {
            exixtingHotel.setName(hotel.getName());
            exixtingHotel.setLocation(hotel.getLocation());
            exixtingHotel.setAbout(hotel.getAbout());

            return hotelDAO.save(exixtingHotel);
        })
                .orElseThrow(null);

        return orElseThrow;

    }
}
