package com.example.demo.service;

import com.example.demo.entity.LoanDetails;
import com.example.demo.repository.LoanDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanDetailsService {

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    public List<LoanDetails> getAllLoanDetails() {
        return loanDetailsRepository.findAll();
    }

    public Optional<LoanDetails> getLoanDetailsById(Long id) {
        return loanDetailsRepository.findById(id);
    }

    public LoanDetails saveLoanDetails(LoanDetails loanDetails) {
        return loanDetailsRepository.save(loanDetails);
    }

    public void deleteLoanDetails(Long id) {
        loanDetailsRepository.deleteById(id);
    }
}
