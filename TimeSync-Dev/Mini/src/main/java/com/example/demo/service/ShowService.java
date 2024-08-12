package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Laboratory;
import com.example.demo.model.TeacherWrapper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LaboratoryRepository laboratoryRepository;
    public ResponseEntity<TeacherWrapper> showData()
    {


        List<String> courseNames = new ArrayList<>();
        List<String> labsNames = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        List<Laboratory> laboratories = laboratoryRepository.findAll();

        for(Course course : courses)
                courseNames.add(course.getName());
        for(Laboratory laboratory : laboratories)
                labsNames.add(laboratory.getName());
        TeacherWrapper wrapper = new TeacherWrapper(courseNames,labsNames);

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
