package com.student.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.student.entity.College;
import com.student.entity.Student;
import com.student.service.CollegeService;
import com.student.service.StudentService;

import jakarta.servlet.http.HttpServletResponse;

import com.student.service.EmailService;

@Controller
public class StudentController {

	private final StudentService service;
	private final CollegeService collegeService;
	private final EmailService emailService;

	public StudentController(StudentService service,
	                         CollegeService collegeService,
	                         EmailService emailService) {
	    this.service = service;
	    this.collegeService = collegeService;
	    this.emailService = emailService;
	}
	@GetMapping("/email-report/{id}")
	public String emailReport(@PathVariable Long id,
	                          Principal principal) {

	    try {
	        Student student = service.getStudentById(id);

	        if (student == null ||
	            student.getUsername() == null ||
	            !student.getUsername().equals(principal.getName())) {
	            return "redirect:/";
	        }

	        emailService.sendCareerReport(student);

	        return "redirect:/roadmap/" + id + "?emailSent=true";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/roadmap/" + id + "?emailFailed=true";
	    }
	}
	@GetMapping("/")
	public String home(@RequestParam(required = false) String keyword, Principal principal, Model model) {

		System.out.println("Logged in user: " + principal.getName());

		List<Student> students = service.searchStudentsByUsername(principal.getName(), keyword);

		model.addAttribute("student", new Student());
		model.addAttribute("students", students);
		model.addAttribute("keyword", keyword);

		addStudentDashboardData(model, principal.getName());

		return "index";
	}

	@PostMapping("/save")
	public String saveStudent(@ModelAttribute Student student, @RequestParam("imageFile") MultipartFile imageFile,
			Principal principal) throws IOException {

		student.setUsername(principal.getName());

		if (student.getId() != null) {
			Student oldStudent = service.getStudentById(student.getId());

			if (oldStudent != null) {
				student.setUsername(oldStudent.getUsername());

				if (imageFile.isEmpty()) {
					student.setPhoto(oldStudent.getPhoto());
				}
			}
		}

		if (!imageFile.isEmpty()) {

			String uploadDir = "uploads/";
			File folder = new File(uploadDir);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

			Path filePath = Paths.get(uploadDir + fileName);

			Files.write(filePath, imageFile.getBytes());

			student.setPhoto(fileName);
		}

		Student savedStudent = service.saveStudent(student);

		return "redirect:/roadmap/" + savedStudent.getId();
	}

	@GetMapping("/roadmap/{id}")
	public String viewRoadmap(@PathVariable Long id, Principal principal, Model model) {

		Student student = service.getStudentById(id);

		if (student == null) {
			return "redirect:/";
		}

		if (student.getUsername() == null || !student.getUsername().equals(principal.getName())) {
			return "redirect:/";
		}

		model.addAttribute("student", student);

		model.addAttribute("suggestion", service.getCareerSuggestion(student));

		model.addAttribute("careerMatches", service.getAiCareerMatches(student));

		model.addAttribute("roadmap", service.getCareerRoadmap(student));

		model.addAttribute("colleges", collegeService.getColleges(student.getQualification()));

		return "roadmap";
	}

	@GetMapping("/download-report/{id}")
	public void downloadReport(@PathVariable Long id, Principal principal, HttpServletResponse response)
			throws Exception {

		Student student = service.getStudentById(id);

		if (student == null || student.getUsername() == null || !student.getUsername().equals(principal.getName())) {
			response.sendRedirect("/");
			return;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=career-report-" + student.getId() + ".pdf");

		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
		Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

		document.add(new Paragraph("Student Career Guidance Report", titleFont));
		document.add(new Paragraph(" "));

		document.add(new Paragraph("Student Details", headingFont));
		document.add(new Paragraph("Name: " + student.getName(), normalFont));
		document.add(new Paragraph("Email: " + student.getEmail(), normalFont));
		document.add(new Paragraph("Phone: " + student.getPhone(), normalFont));
		document.add(new Paragraph("Qualification: " + student.getQualification(), normalFont));
		document.add(new Paragraph("Percentage: " + student.getPercentage() + "%", normalFont));
		document.add(new Paragraph("Interest: " + student.getInterest(), normalFont));
		document.add(new Paragraph(" "));

		document.add(new Paragraph("Career Suggestion", headingFont));
		document.add(new Paragraph(service.getCareerSuggestion(student), normalFont));
		document.add(new Paragraph(" "));

		document.add(new Paragraph("AI Career Recommendations", headingFont));
		service.getAiCareerMatches(student).forEach(match -> {
			try {
				document.add(
						new Paragraph(match.getCareerName() + " - " + match.getMatchPercentage() + "%", normalFont));
				document.add(new Paragraph(match.getDescription(), normalFont));
				document.add(new Paragraph(" "));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		document.add(new Paragraph("Career Roadmap", headingFont));
		document.add(new Paragraph(service.getCareerRoadmap(student), normalFont));
		document.add(new Paragraph(" "));

		document.add(new Paragraph("Recommended Colleges", headingFont));

		List<College> colleges = collegeService.getColleges(student.getQualification());

		for (College c : colleges) {
			document.add(new Paragraph("College: " + c.getCollegeName(), normalFont));
			document.add(new Paragraph("Course: " + c.getCourse(), normalFont));
			document.add(new Paragraph("Location: " + c.getLocation(), normalFont));
			document.add(new Paragraph("Fees: " + c.getFees(), normalFont));
			document.add(new Paragraph("Website: " + c.getWebsite(), normalFont));
			document.add(new Paragraph(" "));
		}

		document.close();
	}

	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable Long id, Principal principal, Model model) {

		Student student = service.getStudentById(id);

		if (student == null || student.getUsername() == null || !student.getUsername().equals(principal.getName())) {
			return "redirect:/";
		}

		model.addAttribute("student", student);
		model.addAttribute("students", service.getStudentsByUsername(principal.getName()));

		addStudentDashboardData(model, principal.getName());

		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable Long id, Principal principal) {

		Student student = service.getStudentById(id);

		if (student != null && student.getUsername() != null && student.getUsername().equals(principal.getName())) {

			service.deleteStudent(id);
		}

		return "redirect:/";
	}

	private void addStudentDashboardData(Model model, String username) {

		List<Student> students = service.getStudentsByUsername(username);

		long tenthCount = students.stream().filter(s -> "10th".equalsIgnoreCase(s.getQualification())).count();

		long twelfthCount = students.stream().filter(s -> "12th".equalsIgnoreCase(s.getQualification())).count();

		long diplomaCount = students.stream().filter(s -> "Diploma".equalsIgnoreCase(s.getQualification())).count();

		long degreeCount = students.stream().filter(s -> "Degree".equalsIgnoreCase(s.getQualification())).count();

		model.addAttribute("totalStudents", students.size());
		model.addAttribute("tenthCount", tenthCount);
		model.addAttribute("twelfthCount", twelfthCount);
		model.addAttribute("diplomaCount", diplomaCount);
		model.addAttribute("degreeCount", degreeCount);
	}
}