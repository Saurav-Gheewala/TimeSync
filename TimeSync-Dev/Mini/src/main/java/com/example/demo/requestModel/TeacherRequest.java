package com.example.demo.requestModel;

import com.example.demo.model.CourseInputForTeacher;
import com.example.demo.model.LabInputForTeacher;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequest {
    private String name;
    private List<CourseInputForTeacher> coursesByTeacher;
    private List<LabInputForTeacher> labsByTeacher;
}
