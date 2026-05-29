package com.student.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.student.entity.Student;
import com.student.service.CollegeService;
import com.student.service.StudentService;

@Controller
public class StudentController {

    private final StudentService service;
    private final CollegeService collegeService;

    public StudentController(
            StudentService service,
            CollegeService collegeService) {

        this.service = service;
        this.collegeService = collegeService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Student> students = service.searchStudents(keyword);

        model.addAttribute("student", new Student());
        model.addAttribute("students", students);

        addDashboardData(model);
        model.addAttribute("keyword", keyword);

        return "index";
    }

    @PostMapping("/save")
    public String saveStudent(
            @ModelAttribute Student student,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) throws IOException {

        if (!imageFile.isEmpty()) {

            String uploadDir = "uploads/";
            File folder = new File(uploadDir);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + imageFile.getOriginalFilename();

            Path filePath =
                    Paths.get(uploadDir + fileName);

            Files.write(filePath,
                    imageFile.getBytes());

            student.setPhoto(fileName);
        }

        Student savedStudent =
                service.saveStudent(student);

        model.addAttribute("student", new Student());
        model.addAttribute("students",
                service.getAllStudents());

        model.addAttribute("studentName",
                savedStudent.getName());

        model.addAttribute("suggestion",
                service.getCareerSuggestion(savedStudent));

        model.addAttribute("roadmap",
                service.getCareerRoadmap(savedStudent));

        model.addAttribute("colleges",
                collegeService.getColleges(
                        savedStudent.getQualification()));

        addDashboardData(model);

        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("student",
                service.getStudentById(id));

        model.addAttribute("students",
                service.getAllStudents());

        addDashboardData(model);

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(
            @PathVariable Long id) {

        service.deleteStudent(id);

        return "redirect:/";
    }

    private void addDashboardData(Model model) {

        model.addAttribute("totalStudents",
                service.getTotalStudents());

        model.addAttribute("tenthCount",
                service.get10thCount());

        model.addAttribute("twelfthCount",
                service.get12thCount());

        model.addAttribute("diplomaCount",
                service.getDiplomaCount());

        model.addAttribute("degreeCount",
                service.getDegreeCount());
    }
}