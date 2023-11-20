/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UserService.service;

import com.UserService.model.User;
import java.util.List;

/**
 *
 * @author 91976
 */
public interface UserService {

    public User createUser(User user);

    List<User> getAllUser();

    User getUserById(Integer id);

    //   public User updateUser(User user) ;
    public void deleteUser(Integer id);

    public User updateUser(Integer id, User user);
}
