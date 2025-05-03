package com.pawtastic.petservices.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class FormRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String frequency;
    private String date;
    private String time;

    @OneToOne(cascade = CascadeType.ALL)
    private Days days;

    private String notes;
}
