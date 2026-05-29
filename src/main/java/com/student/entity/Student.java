package com.student.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String qualification;
    private double percentage;
    private String interest;
    
    private String photo;
    
    private String username;
    
    
    private String tenthSchool;
    private double tenthPercentage;
    private String tenthYear;

    private String intermediateCollege;
    private String intermediateStream;
    private double intermediatePercentage;
    private String intermediateYear;

    private String degreeCollege;
    private String degreeCourse;
    private double degreePercentage;
    private String degreeYear;
    
    public String getTenthSchool() {
		return tenthSchool;
	}

	public double getTenthPercentage() {
		return tenthPercentage;
	}

	public String getTenthYear() {
		return tenthYear;
	}

	public String getIntermediateCollege() {
		return intermediateCollege;
	}

	public String getIntermediateStream() {
		return intermediateStream;
	}

	public double getIntermediatePercentage() {
		return intermediatePercentage;
	}

	public String getIntermediateYear() {
		return intermediateYear;
	}

	public String getDegreeCollege() {
		return degreeCollege;
	}

	public String getDegreeCourse() {
		return degreeCourse;
	}

	public double getDegreePercentage() {
		return degreePercentage;
	}

	public String getDegreeYear() {
		return degreeYear;
	}

	public void setTenthSchool(String tenthSchool) {
		this.tenthSchool = tenthSchool;
	}

	public void setTenthPercentage(double tenthPercentage) {
		this.tenthPercentage = tenthPercentage;
	}

	public void setTenthYear(String tenthYear) {
		this.tenthYear = tenthYear;
	}

	public void setIntermediateCollege(String intermediateCollege) {
		this.intermediateCollege = intermediateCollege;
	}

	public void setIntermediateStream(String intermediateStream) {
		this.intermediateStream = intermediateStream;
	}

	public void setIntermediatePercentage(double intermediatePercentage) {
		this.intermediatePercentage = intermediatePercentage;
	}

	public void setIntermediateYear(String intermediateYear) {
		this.intermediateYear = intermediateYear;
	}

	public void setDegreeCollege(String degreeCollege) {
		this.degreeCollege = degreeCollege;
	}

	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public void setDegreePercentage(double degreePercentage) {
		this.degreePercentage = degreePercentage;
	}

	public void setDegreeYear(String degreeYear) {
		this.degreeYear = degreeYear;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}