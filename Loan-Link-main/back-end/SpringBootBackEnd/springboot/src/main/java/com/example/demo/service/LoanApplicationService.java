package com.example.demo.service;


import com.example.demo.entity.LoanApplication;
import com.example.demo.repository.LoanApplicationRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository repository;

    public LoanApplication saveLoanApplication(LoanApplication loanApplication) {
        return repository.save(loanApplication);
    }

    public List<LoanApplication> findLoansByEmail(String email) {
        return repository.findByEmail(email);
    }
    public Optional<LoanApplication> findLoanApplicationById(Long id) {
        return repository.findById(id);
    }

    public List<LoanApplication> findAllLoanApplications() {
        return repository.findAll();
    }
    // LoanApplicationService.java




}