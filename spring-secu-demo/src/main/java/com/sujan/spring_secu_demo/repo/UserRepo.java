package com.sujan.spring_secu_demo.repo;

import com.sujan.spring_secu_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
