package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Laboratory;
import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LaboratoryRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SampleDataService {


    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LaboratoryRepository laboratoryRepository;

    @Autowired
    TeacherRepository teacherRepository;

    public List<String> getCourses() {
        List<String> courseNames = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        for(Course course : courses)
            courseNames.add(course.getName());
        return courseNames;
    }

    public List<String> getLaboratories() {
        List<String> labsNames = new ArrayList<>();
        List<Laboratory> laboratories = laboratoryRepository.findAll();
        for(Laboratory laboratory : laboratories)
            labsNames.add(laboratory.getName());

        return labsNames;
    }

    public List<String> getTeachers() {

        List<String> teachersName = new ArrayList<>();
        List<Teacher> teachers = teacherRepository.findAll();
        for(Teacher teacher : teachers)
                teachersName.add(teacher.getName());

        return teachersName;
    }
}
