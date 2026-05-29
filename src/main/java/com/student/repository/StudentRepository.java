package com.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	
	 long countByQualification(String qualification);

	    List<Student> findByNameContainingIgnoreCase(String name);

	    List<Student> findByQualificationContainingIgnoreCase(String qualification);

	    List<Student> findByInterestContainingIgnoreCase(String interest);
	    
	    List<Student> findByUsername(String username);
}