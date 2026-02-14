package com.sujan.spring_boot;

import com.sujan.spring_boot.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);

		StudentRepo repo = context.getBean(StudentRepo.class);
		/*Student s1 = context.getBean(Student.class);
		s1.setName("Sujan");
		s1.setRollNo(101);
		s1.setMarks(78);

		Student s2 = context.getBean(Student.class);
		s2.setName("Saheb");
		s2.setRollNo(102);
		s2.setMarks(81);

		Student s3 = context.getBean(Student.class);
		s3.setName("Laxman");
		s3.setRollNo(103);
		s3.setMarks(85);


		repo.save(s1);
		repo.save(s2);
		repo.save(s3);*/

//		System.out.println(repo.findAll());

/*		Optional<Student> s = repo.findById(104); *//* findById return Optional not object to handle if object not exist *//*

		System.out.println(s.orElse(new Student()));//repo.findById(102) // if not exist return empty object*/


		/**
		 * Fetches multiple entities using their primary keys. (findAllById)
		 * It returns only the existing records by Ids
		 */

//		List<Integer> Ids = List.of(102,103);
		/*List<Integer> Ids = List.of(101,104); // 104 not exist in DB

		List<Student> students = repo.findAllById(Ids);

		System.out.println(students);*/


		/**
		 *
		 */
		System.out.println(repo.getByName("Sujan"));

		Student s2 = context.getBean(Student.class);
		s2.setName("Saheb");
		s2.setRollNo(102);
		s2.setMarks(80);

		repo.save(s2);
//		repo.delete(s2);
	}

}
