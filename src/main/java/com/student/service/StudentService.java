package com.student.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository repository;

	public StudentService(StudentRepository repository) {
		this.repository = repository;
	}

	public Student saveStudent(Student student) {
		return repository.save(student);
	}

	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	public Student getStudentById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public void deleteStudent(Long id) {
		repository.deleteById(id);
	}

	public String getCareerSuggestion(Student student) {

		String q = student.getQualification();
		String interest = student.getInterest();

		if (q.equalsIgnoreCase("10th")) {
			if (interest.equalsIgnoreCase("Engineering")) {
				return "Choose Polytechnic Diploma, MPC Intermediate, ITI Technical Courses.";
			} else if (interest.equalsIgnoreCase("Medical")) {
				return "Choose BiPC Intermediate, Paramedical Diploma, Nursing Assistant Course.";
			} else if (interest.equalsIgnoreCase("Commerce")) {
				return "Choose CEC Intermediate, MEC Intermediate, Accounting Basics.";
			} else if (interest.equalsIgnoreCase("IT")) {
				return "Choose Computer Diploma, Web Designing, Basic Programming, Polytechnic CSE.";
			} else {
				return "Choose Intermediate, ITI, Polytechnic, or Government job preparation.";
			}
		}

		if (q.equalsIgnoreCase("12th")) {
			if (interest.equalsIgnoreCase("Engineering")) {
				return "Choose B.Tech, Diploma lateral entry, B.Sc Computer Science.";
			} else if (interest.equalsIgnoreCase("Medical")) {
				return "Choose MBBS, B.Pharmacy, Nursing, Physiotherapy, Lab Technician.";
			} else if (interest.equalsIgnoreCase("Commerce")) {
				return "Choose B.Com, BBA, CA Foundation, Banking courses.";
			} else if (interest.equalsIgnoreCase("IT")) {
				return "Choose BCA, B.Sc Computers, Java Full Stack, Python, Data Analytics.";
			} else {
				return "Choose Degree, Government exams, Skill development courses.";
			}
		}

		if (q.equalsIgnoreCase("Diploma")) {
			return "Choose B.Tech lateral entry, Software courses, Apprenticeship, Technical jobs.";
		}

		if (q.equalsIgnoreCase("Degree")) {
			return "Choose MCA, MBA, M.Tech, Software Developer jobs, Government exams.";
		}

		return "Please select valid qualification.";
	}
}