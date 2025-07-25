/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UserService.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 91976
 */
@Entity
@Table(name = "m_user")
@Getter
@Setter
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String about;

    public User() {
    }

    public User(Integer id, String name, String email, String about, List<Rating> ratings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.about = about;
        this.ratings = ratings;
    }

    @Transient //we are using @Transient bcoz we dont want to save this field in DB
    private List<Rating> ratings = new ArrayList<>();

}
