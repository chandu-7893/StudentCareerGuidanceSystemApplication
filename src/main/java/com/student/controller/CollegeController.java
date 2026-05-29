package com.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.student.entity.College;
import com.student.service.CollegeService;

@Controller
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping("/college/{id}")
    public String collegeDetails(@PathVariable Long id,
                                 Model model) {

        College college = collegeService.getCollegeById(id);

        if (college == null) {
            return "redirect:/";
        }

        model.addAttribute("college", college);

        return "college-details";
    }
}