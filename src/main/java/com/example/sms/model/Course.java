package com.example.sms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    private int duration;

    public Course() {}

    public Course(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    // getters and setters...
}
