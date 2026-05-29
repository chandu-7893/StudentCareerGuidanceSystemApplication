package com.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.entity.College;

public interface CollegeRepository extends JpaRepository<College, Long>{
	 List<College> findByQualification(String qualification);

}
