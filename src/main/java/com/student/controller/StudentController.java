package com.student.controller;

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
    public String home(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("students", service.getAllStudents());
        return "index";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, Model model) {

        Student savedStudent = service.saveStudent(student);

        model.addAttribute("student", new Student());
        model.addAttribute("students", service.getAllStudents());
        model.addAttribute("studentName", savedStudent.getName());
        model.addAttribute("suggestion", service.getCareerSuggestion(savedStudent));

        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        model.addAttribute("student", service.getStudentById(id));
        model.addAttribute("students", service.getAllStudents());

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return "redirect:/";
    }
}