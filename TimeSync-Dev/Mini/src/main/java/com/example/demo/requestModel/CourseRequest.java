package com.example.demo.requestModel;

import com.example.demo.model.CourseInput;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CourseRequest
{
    private Set<CourseInput> name;
}
