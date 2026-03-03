package com.sujan.spring_secu_demo;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @GetMapping("hello")
    public String greet(HttpServletRequest request){
        return "hello world " + request.getSession().getId();
    }

    @GetMapping("about")
    public String  about(HttpServletRequest request){
        return "Sujan " + request.getSession().getId();
    }
}
