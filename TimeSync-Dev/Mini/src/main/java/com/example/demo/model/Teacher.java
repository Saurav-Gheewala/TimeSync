package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> coursesTaughtByTeacher;

    @ManyToMany
    @JoinTable(
            name = "teacher_laboratory",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "laboratory_id")
    )
    private List<Laboratory> laboratoriesTaughtByTeacher;

    public Teacher(String name, List<Course> coursesTaughtByTeacher, List<Laboratory> laboratoriesTaughtByTeacher) {
        this.name = name;
        this.coursesTaughtByTeacher = coursesTaughtByTeacher;
        this.laboratoriesTaughtByTeacher = laboratoriesTaughtByTeacher;
    }
}
