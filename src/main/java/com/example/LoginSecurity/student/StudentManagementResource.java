package com.example.LoginSecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementResource {

    // API list for management accounts ( ADMIN / ADMINTRAINEE )
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1L,"Jack1"),
            new Student(2L,"Jack2"),
            new Student(3L,"Jack3")
    );

    // Possible list of PreAuthorize types
    // hasRole('ROLE_') , hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping()
    // Access Control
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    // POST API with request body
    @PostMapping()
    // Access Control
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("{studentId}")
    // Access Control
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        System.out.println(studentId);
    }

    @PutMapping("{studentId}")
    // Access Control
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Long studentId,@RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
    }
}