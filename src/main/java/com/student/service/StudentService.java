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

    // Dashboard counts
    public long getTotalStudents() {
        return repository.count();
    }

    public long get10thCount() {
        return repository.countByQualification("10th");
    }

    public long get12thCount() {
        return repository.countByQualification("12th");
    }

    public long getDiplomaCount() {
        return repository.countByQualification("Diploma");
    }

    public long getDegreeCount() {
        return repository.countByQualification("Degree");
    }

    // Search
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findAll();
        }

        List<Student> byName =
                repository.findByNameContainingIgnoreCase(keyword);

        if (!byName.isEmpty()) {
            return byName;
        }

        List<Student> byQualification =
                repository.findByQualificationContainingIgnoreCase(keyword);

        if (!byQualification.isEmpty()) {
            return byQualification;
        }

        return repository.findByInterestContainingIgnoreCase(keyword);
    }

    // Advanced career suggestion
    public String getCareerSuggestion(Student student) {

        String q = student.getQualification();
        String interest = student.getInterest();
        double percentage = student.getPercentage();

        if (q.equalsIgnoreCase("10th")) {

            if (percentage >= 85) {
                if (interest.equalsIgnoreCase("Engineering")) {
                    return "Excellent score! Choose MPC Intermediate with IIT/JEE foundation or Polytechnic CSE/ECE.";
                } else if (interest.equalsIgnoreCase("Medical")) {
                    return "Excellent score! Choose BiPC Intermediate with NEET foundation.";
                } else if (interest.equalsIgnoreCase("IT")) {
                    return "Choose Polytechnic CSE, Computer Diploma, Web Development, and Java/Python basics.";
                }
            }

            if (percentage >= 60) {
                return "Good score! Choose Intermediate, Polytechnic, ITI, or skill-based courses based on your interest.";
            }

            return "Focus on skill-based courses like ITI, Polytechnic, Computer Basics, and improve academic foundation.";
        }

        if (q.equalsIgnoreCase("12th")) {

            if (percentage >= 85) {
                if (interest.equalsIgnoreCase("Engineering")) {
                    return "Excellent score! Choose B.Tech CSE/ECE/AI or prepare for JEE/EAMCET.";
                } else if (interest.equalsIgnoreCase("Medical")) {
                    return "Excellent score! Choose MBBS, B.Pharmacy, Nursing, Physiotherapy, or NEET preparation.";
                } else if (interest.equalsIgnoreCase("IT")) {
                    return "Choose BCA, B.Sc Computers, Java Full Stack, Python, Data Analytics, or Cloud Computing.";
                } else if (interest.equalsIgnoreCase("Commerce")) {
                    return "Choose B.Com, BBA, CA Foundation, Banking, Finance, or Business Analytics.";
                }
            }

            if (percentage >= 60) {
                return "Good score! Choose Degree, BCA, B.Com, BBA, skill courses, or government exam preparation.";
            }

            return "Choose job-oriented degree courses, diploma courses, computer courses, or skill development programs.";
        }

        if (q.equalsIgnoreCase("Diploma")) {

            if (percentage >= 75) {
                return "Great! Choose B.Tech lateral entry, software development, apprenticeships, or technical government jobs.";
            }

            return "Choose technical jobs, apprenticeship, certification courses, AutoCAD, Java, Python, or hardware/networking.";
        }

        if (q.equalsIgnoreCase("Degree")) {

            if (percentage >= 70) {
                if (interest.equalsIgnoreCase("IT")) {
                    return "Choose Java Full Stack, Spring Boot, React, SQL, Git, and apply for Software Developer jobs.";
                }

                return "Choose MCA, MBA, M.Tech, government exams, internships, or placement preparation.";
            }

            return "Improve skills with Java, SQL, Web Development, Communication Skills, and prepare for entry-level jobs.";
        }

        return "Please select valid qualification.";
    }
}