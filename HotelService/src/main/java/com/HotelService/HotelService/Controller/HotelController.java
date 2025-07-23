/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.HotelService.HotelService.Controller;

import com.HotelService.HotelService.Model.Hotel;
import com.HotelService.HotelService.service.HotelService;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 91976
 */
@RestController
@RequestMapping("/hotel")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //http://localhost:8091/hotel/save
    @PostMapping("/save")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        log.info("HotelController :: saveHotel method is executed");
        Hotel saveHotel = hotelService.saveHotel(hotel);

        return new ResponseEntity<>(saveHotel, HttpStatus.OK);

    }

    @GetMapping("/get")
    public List<Hotel> getAllHotel() {
        log.info("HotelController :: getAllHotel method is executed");
        List<Hotel> allHotel = hotelService.getAllHotel();

        return allHotel;
    }

    @GetMapping("/get/{hotelId}")
    public ResponseEntity<Hotel> getById(@PathVariable Integer hotelId) {
        log.info("HotelController :: getById method is executed");

        Hotel hotel = hotelService.getHotelById(hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        log.info("HotelController :: updateHotel method is executed");
        Hotel updateUser = hotelService.updateUser(id, hotel);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletHotel(@PathVariable Integer id) {
        log.info("HotelController :: deletHotel method is executed");
        hotelService.deleteHotel(id);

        return new ResponseEntity<>("Data is deleted", HttpStatus.OK);
    }
}
