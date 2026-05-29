package com.student.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;


import java.util.ArrayList;
import com.student.dto.CareerMatch;

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
    
    public List<Student> getStudentsByUsername(String username) {
        return repository.findByUsername(username);
    }
    
    
    public List<Student> searchStudentsByUsername(String username, String keyword) {

        List<Student> studentList = repository.findByUsername(username);

        if (keyword == null || keyword.trim().isEmpty()) {
            return studentList;
        }

        String key = keyword.toLowerCase();

        return studentList.stream()
                .filter(s ->
                        s.getName().toLowerCase().contains(key)
                        || s.getQualification().toLowerCase().contains(key)
                        || s.getInterest().toLowerCase().contains(key))
                .toList();
    }

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

    public String getCareerRoadmap(Student student) {

        String q = student.getQualification();
        String interest = student.getInterest();

        if (q.equalsIgnoreCase("10th")
                && interest.equalsIgnoreCase("Engineering")) {

            return """
                    10th Completed
                    ↓
                    MPC Intermediate
                    ↓
                    JEE / EAMCET Preparation
                    ↓
                    B.Tech CSE / ECE / Mechanical
                    ↓
                    Internship
                    ↓
                    Software / Core Engineering Job
                    """;
        }

        if (q.equalsIgnoreCase("10th")
                && interest.equalsIgnoreCase("Medical")) {

            return """
                    10th Completed
                    ↓
                    BiPC Intermediate
                    ↓
                    NEET Preparation
                    ↓
                    MBBS / BDS / Nursing / Pharmacy
                    ↓
                    Internship
                    ↓
                    Doctor / Medical Professional
                    """;
        }

        if (q.equalsIgnoreCase("10th")
                && interest.equalsIgnoreCase("IT")) {

            return """
                    10th Completed
                    ↓
                    Polytechnic CSE / Computer Diploma
                    ↓
                    Java / Python Basics
                    ↓
                    Web Development
                    ↓
                    Projects
                    ↓
                    Junior Developer
                    """;
        }

        if (q.equalsIgnoreCase("12th")
                && interest.equalsIgnoreCase("Engineering")) {

            return """
                    12th MPC Completed
                    ↓
                    Entrance Exam / EAMCET / JEE
                    ↓
                    B.Tech CSE / IT / ECE
                    ↓
                    Java Full Stack / Cloud / AI
                    ↓
                    Internship
                    ↓
                    Software Engineer
                    """;
        }

        if (q.equalsIgnoreCase("12th")
                && interest.equalsIgnoreCase("Medical")) {

            return """
                    12th BiPC Completed
                    ↓
                    NEET / State Medical Entrance
                    ↓
                    MBBS / B.Pharmacy / Nursing
                    ↓
                    Clinical Training
                    ↓
                    Medical Career
                    """;
        }

        if (q.equalsIgnoreCase("12th")
                && interest.equalsIgnoreCase("Commerce")) {

            return """
                    12th Commerce Completed
                    ↓
                    B.Com / BBA
                    ↓
                    CA Foundation / Banking / Finance
                    ↓
                    Internship
                    ↓
                    Accountant / Banking / Business Analyst
                    """;
        }

        if (q.equalsIgnoreCase("12th")
                && interest.equalsIgnoreCase("IT")) {

            return """
                    12th Completed
                    ↓
                    BCA / B.Sc Computers
                    ↓
                    Java Full Stack + SQL
                    ↓
                    React / Spring Boot Projects
                    ↓
                    Internship
                    ↓
                    Software Developer
                    """;
        }

        if (q.equalsIgnoreCase("Diploma")) {

            return """
                    Diploma Completed
                    ↓
                    B.Tech Lateral Entry / Technical Certification
                    ↓
                    Industry Tools Training
                    ↓
                    Internship / Apprenticeship
                    ↓
                    Technical Job / Engineer
                    """;
        }

        if (q.equalsIgnoreCase("Degree")
                && interest.equalsIgnoreCase("IT")) {

            return """
                    Degree Completed
                    ↓
                    Java + Spring Boot
                    ↓
                    React + SQL
                    ↓
                    GitHub Projects
                    ↓
                    Internship
                    ↓
                    Software Developer
                    """;
        }

        if (q.equalsIgnoreCase("Degree")) {

            return """
                    Degree Completed
                    ↓
                    MBA / MCA / M.Tech / Government Exams
                    ↓
                    Skill Development
                    ↓
                    Internship / Training
                    ↓
                    Career Growth
                    """;
        }

        return """
                Student
                ↓
                Skill Development
                ↓
                Professional Course
                ↓
                Internship
                ↓
                Career Growth
                """;
    }
    
    
    public List<CareerMatch> getAiCareerMatches(Student student) {

        List<CareerMatch> matches = new ArrayList<>();

        String qualification = student.getQualification();
        String interest = student.getInterest();
        double percentage = student.getPercentage();

        if (interest.equalsIgnoreCase("IT")) {

            if (qualification.equalsIgnoreCase("Degree")
                    || qualification.equalsIgnoreCase("12th")
                    || qualification.equalsIgnoreCase("Diploma")) {

                matches.add(new CareerMatch(
                        "Java Developer",
                        percentage >= 75 ? 95 : 85,
                        "Best for students interested in backend development using Java, Spring Boot, SQL, and APIs."));

                matches.add(new CareerMatch(
                        "Full Stack Developer",
                        percentage >= 70 ? 92 : 82,
                        "Good path if you want to build complete web applications using Java, React, HTML, CSS, and MySQL."));

                matches.add(new CareerMatch(
                        "Cloud Engineer",
                        percentage >= 80 ? 88 : 75,
                        "Suitable for students interested in cloud platforms, deployment, servers, and DevOps basics."));

                matches.add(new CareerMatch(
                        "Data Analyst",
                        percentage >= 70 ? 84 : 72,
                        "Useful career path for students interested in data, SQL, Excel, dashboards, and reports."));

                matches.add(new CareerMatch(
                        "Software Tester",
                        percentage >= 60 ? 82 : 74,
                        "Good entry-level IT role focused on manual testing, automation testing, Selenium, and QA process."));
            }
        }

        else if (interest.equalsIgnoreCase("Engineering")) {

            matches.add(new CareerMatch(
                    "Software Engineer",
                    percentage >= 80 ? 92 : 82,
                    "Best choice for engineering students interested in coding, problem solving, and application development."));

            matches.add(new CareerMatch(
                    "Core Engineer",
                    percentage >= 75 ? 88 : 78,
                    "Suitable for students interested in mechanical, electrical, civil, or electronics engineering fields."));

            matches.add(new CareerMatch(
                    "AI / ML Engineer",
                    percentage >= 85 ? 90 : 76,
                    "Recommended for high-scoring students interested in artificial intelligence, Python, and machine learning."));

            matches.add(new CareerMatch(
                    "DevOps Engineer",
                    percentage >= 75 ? 86 : 74,
                    "Good for students interested in servers, deployment, CI/CD, Linux, Docker, and cloud platforms."));
        }

        else if (interest.equalsIgnoreCase("Medical")) {

            matches.add(new CareerMatch(
                    "Doctor",
                    percentage >= 85 ? 95 : 80,
                    "Best path for students interested in MBBS, NEET preparation, and healthcare services."));

            matches.add(new CareerMatch(
                    "Pharmacist",
                    percentage >= 70 ? 88 : 78,
                    "Good career option through B.Pharmacy and medicine-related fields."));

            matches.add(new CareerMatch(
                    "Nursing Officer",
                    percentage >= 65 ? 85 : 76,
                    "Suitable for students interested in patient care and hospital services."));

            matches.add(new CareerMatch(
                    "Lab Technician",
                    percentage >= 60 ? 82 : 72,
                    "Good practical medical career path with diploma or degree-based lab training."));
        }

        else if (interest.equalsIgnoreCase("Commerce")) {

            matches.add(new CareerMatch(
                    "Accountant",
                    percentage >= 70 ? 90 : 78,
                    "Good career for students interested in accounts, taxation, finance, and bookkeeping."));

            matches.add(new CareerMatch(
                    "Banking Professional",
                    percentage >= 75 ? 88 : 76,
                    "Suitable for students interested in banking exams, finance, and customer services."));

            matches.add(new CareerMatch(
                    "Business Analyst",
                    percentage >= 80 ? 86 : 72,
                    "Best for students interested in business, data, communication, and decision making."));

            matches.add(new CareerMatch(
                    "CA / CMA Foundation",
                    percentage >= 85 ? 92 : 75,
                    "Recommended for high-scoring commerce students interested in professional finance careers."));
        }

        else {

            matches.add(new CareerMatch(
                    "Government Job Preparation",
                    percentage >= 70 ? 85 : 75,
                    "Good option for students interested in public sector jobs, SSC, Railways, Banking, and state exams."));

            matches.add(new CareerMatch(
                    "Skill-Based Career",
                    percentage >= 60 ? 82 : 74,
                    "Recommended path through ITI, diploma, computer courses, communication skills, and practical training."));

            matches.add(new CareerMatch(
                    "Entrepreneurship",
                    percentage >= 65 ? 80 : 70,
                    "Good for students interested in business, self-employment, freelancing, and startups."));
        }

        return matches;
    }
}