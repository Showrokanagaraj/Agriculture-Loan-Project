// package com.example.demo.service;



// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.entity.Inquiry;
// import com.example.demo.entity.InquiryReply;
// import com.example.demo.repository.InquiryRepository;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class InquiryService {

//     @Autowired
//     private InquiryRepository inquiryRepository;

//     // Get all inquiries
//     public List<Inquiry> getAllInquiries() {
//         return inquiryRepository.findAll();
//     }

//     public InquiryReply getReplyByEmail(String email) {
//         Optional<Inquiry> inquiryOptional = inquiryRepository.findByEmail(email);
//         return inquiryOptional.map(Inquiry::getReply).orElse(null);
//     }

//     // Submit a new inquiry
//     public void submitInquiry(Inquiry inquiry) {
//         inquiryRepository.save(inquiry);
//     }

//     // Send a reply to a specific inquiry
//     public void sendReply(Long id, InquiryReply reply) {
//         Optional<Inquiry> inquiryOptional = inquiryRepository.findById(id);
//         if (inquiryOptional.isPresent()) {
//             Inquiry inquiry = inquiryOptional.get();
    
//             // Set the email of the reply based on the inquiry's email
//             reply.setEmail(inquiry.getEmail());
    
//             inquiry.setReply(reply);
//             inquiryRepository.save(inquiry);
//         }
//     }
    
// }