package com.example.demo.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class LoanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanName;
    private double loanAmount;
    private double interestRate;
    private int loanTenure;
    private String eligibilityCriteria;
    private String feesAndCharges;
    private String documentationRequirements;
    private String disbursementDetails;
    private String supportAndServices;
    private String termsAndConditions;
    private String riskAssessment;
}
