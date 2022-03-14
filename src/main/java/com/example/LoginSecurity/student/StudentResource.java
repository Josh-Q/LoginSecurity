package com.example.LoginSecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentResource {


    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1L,"Jack1"),
            new Student(2L,"Jack2"),
            new Student(3L,"Jack3")
    );


    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId){
        return STUDENTS.stream().filter(student ->
            studentId.equals(student.getStudentId())
        ).findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist!"));

    }

    }

