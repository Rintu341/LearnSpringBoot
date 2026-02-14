package com.sujan.spring_boot;

import com.sujan.spring_boot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DSL = Domain Specific Language
 *
 * It means a language designed for a specific problem domain.
 *
 * In the Spring Data JPA ecosystem, examples of DSLs include:
 *
 * Method name query DSL → findByNameAndAgeGreaterThan()
 *
 * JPQL (JPA Query Language)
 *
 * Criteria API
 *
 * QueryDSL (a type-safe query builder library)
 *
 * All of these are ways to express database queries.
 */

@Repository
public interface StudentRepo  extends JpaRepository<Student, Integer> {


    @Query("select s from Student s where s.name = :name")
    List<Student> getByName(String name);




}
