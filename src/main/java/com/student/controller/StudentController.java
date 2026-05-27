package com.student.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.student.entity.Student;
import com.student.service.StudentService;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Student> students = service.searchStudents(keyword);

        model.addAttribute("student", new Student());
        model.addAttribute("students", students);

        // Dashboard Statistics
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

        model.addAttribute("keyword", keyword);

        return "index";
    }

    @PostMapping("/save")
    public String saveStudent(
            @ModelAttribute Student student,
            Model model) {

        Student savedStudent =
                service.saveStudent(student);

        model.addAttribute("student",
                new Student());

        model.addAttribute("students",
                service.getAllStudents());

        model.addAttribute("studentName",
                savedStudent.getName());

        model.addAttribute("suggestion",
                service.getCareerSuggestion(savedStudent));

        // Dashboard Statistics
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

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(
            @PathVariable Long id) {

        service.deleteStudent(id);

        return "redirect:/";
    }
}