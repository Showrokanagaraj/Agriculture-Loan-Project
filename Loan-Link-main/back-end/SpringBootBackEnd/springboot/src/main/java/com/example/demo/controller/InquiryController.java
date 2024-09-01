// package com.example.demo.controller;



// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.demo.entity.Inquiry;
// import com.example.demo.entity.InquiryReply;
// import com.example.demo.service.InquiryService;

// import java.util.List;

// @RestController
// @RequestMapping("/auth/api/users_list")
// public class InquiryController {

//     @Autowired
//     private InquiryService inquiryService;

//     // Get all inquiries
//     @GetMapping("/inquirylist")
//     public List<Inquiry> getAllInquiries() {
//         return inquiryService.getAllInquiries();
//     }

//     // Get reply for a specific inquiry by email
//     @GetMapping("/reply/{email}")
//     public ResponseEntity<InquiryReply> getReplyByEmail(@PathVariable String email) {
//         InquiryReply reply = inquiryService.getReplyByEmail(email);
//         if (reply != null) {
//             return ResponseEntity.ok(reply);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // Post a new inquiry
//     @PostMapping("/inquiry")
//     public ResponseEntity<Void> submitInquiry(@RequestBody Inquiry inquiry) {
//         inquiryService.submitInquiry(inquiry);
//         return ResponseEntity.ok().build();
//     }

//     // Post a reply to a specific inquiry
//     @PostMapping("/inquiries/{id}/reply")
//     public ResponseEntity<Void> sendReply(@PathVariable Long id, @RequestBody InquiryReply reply) {
//         inquiryService.sendReply(id, reply);
//         return ResponseEntity.ok().build();
//     }
//     @GetMapping("/inquiries/reply/{email}")
//     public ResponseEntity<InquiryReply> fetchReplyByEmail(@PathVariable String email) {
//         InquiryReply reply = inquiryService.getReplyByEmail(email);
//         if (reply != null) {
//             return ResponseEntity.ok(reply);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }