package com.sujan.spring_secu_demo.controller;

import com.sujan.spring_secu_demo.model.User;
import com.sujan.spring_secu_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("register")
    public User saveUser(@RequestBody User user){
        return service.saveUser(user);
    }

}
