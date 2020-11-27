package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity @Getter @Setter
public class Address extends AbstractEntity {
    private String Street;
    private String Zip;
    private String State;
    private String Country;
}
