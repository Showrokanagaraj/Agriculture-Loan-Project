// package com.example.demo.repository;




// import org.apache.el.stream.Optional;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.example.demo.entity.Inquiry;
// import com.example.demo.entity.InquiryReply;

// @Repository
// public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
//     InquiryReply findReplyByEmail(String email);
//     Optional<Inquiry> findByEmail(String email);
// }