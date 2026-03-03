package com.sujan.spring_secu_demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<Student>(List.of(
            new Student(1, "Sujan", "Math"),
            new Student(2, "Rahul", "Physics"),
            new Student(3, "Anita", "Chemistry")
    ));

    @GetMapping("csrf-token")
    public CsrfToken getToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping(value = "students", produces = {"application/json"})
    public List<Student> students(){
        return students;
    }

    @PostMapping(value = "students", consumes = {"application/json"})
    public void addStudent(@RequestBody Student s){
        students.add(s);
    }


}
