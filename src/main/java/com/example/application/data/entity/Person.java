package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import javax.annotation.Nullable;

@Entity @Getter @Setter
public class Person extends AbstractEntity {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    @Nullable
    private LocalDate dateOfBirth;
    private String occupation;

}
