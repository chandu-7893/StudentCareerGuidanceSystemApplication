package com.student.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.student.entity.Student;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final StudentService studentService;

    public EmailService(JavaMailSender mailSender,
                        StudentService studentService) {
        this.mailSender = mailSender;
        this.studentService = studentService;
    }

    public void sendCareerReport(Student student) {

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Student email is empty");
        }

        String subject = "Your Career Guidance Report";

        String body =
                "Hello " + student.getName() + ",\n\n" +
                "Here is your career guidance report.\n\n" +

                "Qualification: " + student.getQualification() + "\n" +
                "Percentage: " + student.getPercentage() + "%\n" +
                "Interest: " + student.getInterest() + "\n\n" +

                "Career Suggestion:\n" +
                studentService.getCareerSuggestion(student) + "\n\n" +

                "Career Roadmap:\n" +
                studentService.getCareerRoadmap(student) + "\n\n" +

                "Thank you,\n" +
                "Student Career Guidance System";

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("sangireddyreddy789@gmail.com");
            message.setTo(student.getEmail().trim());
            message.setSubject(subject);
            message.setText(body);

            System.out.println("Sending email to: " + student.getEmail());

            mailSender.send(message);

            System.out.println("Email sent successfully to: " + student.getEmail());

        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Email sending failed", e);
        }
    }
}