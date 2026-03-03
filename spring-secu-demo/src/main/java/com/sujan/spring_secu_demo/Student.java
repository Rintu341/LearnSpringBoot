package com.sujan.spring_secu_demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    public int id;
    public String name;
    public String subject;
}
