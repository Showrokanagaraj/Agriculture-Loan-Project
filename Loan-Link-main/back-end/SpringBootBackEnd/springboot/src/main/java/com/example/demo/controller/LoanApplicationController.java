package com.example.demo.controller;



import com.example.demo.entity.LoanApplication;
import com.example.demo.service.LoanApplicationService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/users_list")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService service;

    @PostMapping("/save_loan_data")
    public ResponseEntity<String> saveLoanApplication(@RequestBody LoanApplication loanApplication) {
        try {
            service.saveLoanApplication(loanApplication);
            return new ResponseEntity<>("Loan application saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save loan application", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // http://localhost:5454/auth/api/users_list?/get_loans_by_email?email=USER@gmail.com
    // SELECT * FROM loan_application WHERE email = 'SUBASH@gmail.com';
   
    @GetMapping("/getemail")
    public ResponseEntity<List<LoanApplication>> getLoansByEmail(@RequestParam String email) {
        try {
            List<LoanApplication> loans = service.findLoansByEmail(email);
            if (loans.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/get_loan_by_id/{id}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable Long id) {
        Optional<LoanApplication> loanApplication = service.findLoanApplicationById(id);
        if (loanApplication.isPresent()) {
            return new ResponseEntity<>(loanApplication.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_all_loans")
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        try {
            List<LoanApplication> loans = service.findAllLoanApplications();
            if (loans.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update_loan_status")
    public ResponseEntity<String> updateLoanStatus(@RequestBody LoanApplication loanApplication) {
        try {
            Optional<LoanApplication> existingLoan = service.findLoanApplicationById(loanApplication.getId());
            if (existingLoan.isPresent()) {
                LoanApplication updatedLoan = existingLoan.get();
                updatedLoan.setStatus(loanApplication.getStatus());
                service.saveLoanApplication(updatedLoan);
                return new ResponseEntity<>("Loan status updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Loan application not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update loan status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
        
  


}
