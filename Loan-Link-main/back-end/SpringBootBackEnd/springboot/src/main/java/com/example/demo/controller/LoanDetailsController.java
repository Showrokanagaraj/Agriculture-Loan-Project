package com.example.demo.controller;


// import com.example.demo.entity.LoanDetails;
// import com.example.demo.service.LoanDetailsService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth/api/users_list")
// public class LoanDetailsController {

//     @Autowired
//     private LoanDetailsService loanDetailsService;

//     @PostMapping
//     public ResponseEntity<LoanDetails> createLoanDetails(@RequestBody LoanDetails loanDetails) {
//         LoanDetails savedLoanDetails = loanDetailsService.saveLoanDetails(loanDetails);
//         return ResponseEntity.ok(savedLoanDetails);
//     }
// }


import com.example.demo.entity.LoanDetails;
import com.example.demo.service.LoanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/api/users_list")
public class LoanDetailsController {

    @Autowired
    private LoanDetailsService loanDetailsService;

    // Create a new loan detail
    @PostMapping
    public ResponseEntity<LoanDetails> createLoanDetails(@RequestBody LoanDetails loanDetails) {
        LoanDetails savedLoanDetails = loanDetailsService.saveLoanDetails(loanDetails);
        return ResponseEntity.ok(savedLoanDetails);
    }

    // Get all loan details
    @GetMapping
    public ResponseEntity<List<LoanDetails>> getAllLoanDetails() {
        List<LoanDetails> loanDetails = loanDetailsService.getAllLoanDetails();
        return ResponseEntity.ok(loanDetails);
    }

    // Get loan details by ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanDetails> getLoanDetailsById(@PathVariable Long id) {
        Optional<LoanDetails> loanDetails = loanDetailsService.getLoanDetailsById(id);
        return loanDetails.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update loan details by ID
    @PutMapping("/{id}")
    public ResponseEntity<LoanDetails> updateLoanDetails(@PathVariable Long id, @RequestBody LoanDetails loanDetails) {
        if (!loanDetailsService.getLoanDetailsById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        loanDetails.setId(id);
        LoanDetails updatedLoanDetails = loanDetailsService.saveLoanDetails(loanDetails);
        return ResponseEntity.ok(updatedLoanDetails);
    }

    // Delete loan details by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanDetails(@PathVariable Long id) {
        if (!loanDetailsService.getLoanDetailsById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        loanDetailsService.deleteLoanDetails(id);
        return ResponseEntity.noContent().build();
    }
}


