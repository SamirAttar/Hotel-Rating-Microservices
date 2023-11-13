/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UserService.service.Impl;

import com.UserService.DAO.UserDAO;
import com.UserService.model.User;
import com.UserService.service.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 91976
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    
    @Autowired
    private RestTemplate RestTemplate;
    
    
    @Override
    public User createUser(User user) {

        return userDAO.save(user);

    }

    @Override
    public List<User> getAllUser() {
        List<User> user = userDAO.findAll();
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Integer id) {
        userDAO.deleteById(id);
    }

    @Override
    public User updateUser(Integer id, User user) {
        return userDAO.findById(id)
                .map(existingPerson -> {
                    existingPerson.setName(user.getName());
                    existingPerson.setEmail(user.getEmail());
                    existingPerson.setAbout(user.getAbout());

                    return userDAO.save(existingPerson);
                })
                .orElseThrow(null);
    }
}
