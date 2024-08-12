package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class TeacherWrapper
{
    private List<String> availableCourses;
    private List<String> availableLabs;

    public TeacherWrapper(List<String> availableCourses, List<String> availableLabs) {
        this.availableCourses = availableCourses;
        this.availableLabs = availableLabs;
    }
}
