package com.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.entity.College;
import com.student.repository.CollegeRepository;

@Service
public class CollegeService {
	 @Autowired
	    private CollegeRepository repository;

	    public List<College> getColleges(String qualification) {

	        return repository.findByQualification(
	                qualification);
	    }
	    public College getCollegeById(Long id) {
	        return repository.findById(id).orElse(null);
	    }
}
