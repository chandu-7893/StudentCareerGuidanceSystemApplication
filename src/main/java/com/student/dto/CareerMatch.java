package com.student.dto;

public class CareerMatch {

    private String careerName;
    private int matchPercentage;
    private String description;

    public CareerMatch(String careerName,
                       int matchPercentage,
                       String description) {
        this.careerName = careerName;
        this.matchPercentage = matchPercentage;
        this.description = description;
    }

    public String getCareerName() {
        return careerName;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public String getDescription() {
        return description;
    }
}