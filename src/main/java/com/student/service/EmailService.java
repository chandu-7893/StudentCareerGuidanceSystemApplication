package com.student.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.student.entity.Student;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.from.email}")
    private String fromEmail;

    @Value("${resend.test.to}")
    private String testEmail;

    private final StudentService studentService;

    public EmailService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void sendCareerReport(Student student) {

        try {
            System.out.println("===== EMAIL STARTED =====");

            if (student == null) {
                throw new RuntimeException("Student is null");
            }

            if (apiKey == null || apiKey.isBlank()) {
                throw new RuntimeException("Resend API key is missing");
            }

            System.out.println("Sending email to: " + testEmail);

            String body =
                    "<h2>Hello " + student.getName() + "</h2>" +
                    "<p>Here is your career guidance report.</p>" +
                    "<p><b>Qualification:</b> " + student.getQualification() + "</p>" +
                    "<p><b>Percentage:</b> " + student.getPercentage() + "%</p>" +
                    "<p><b>Interest:</b> " + student.getInterest() + "</p>" +
                    "<h3>Career Suggestion</h3>" +
                    "<p>" + studentService.getCareerSuggestion(student) + "</p>" +
                    "<h3>Career Roadmap</h3>" +
                    "<p>" + studentService.getCareerRoadmap(student) + "</p>" +
                    "<br><p>Thank you,<br>Student Career Guidance System</p>";

            Resend resend = new Resend(apiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(testEmail)
                    .subject("Your Career Guidance Report")
                    .html(body)
                    .build();

            CreateEmailResponse response = resend.emails().send(params);

            System.out.println("Email sent successfully. ID: " + response.getId());

        } catch (Exception e) {
            System.out.println("===== EMAIL ERROR =====");
            e.printStackTrace();
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}