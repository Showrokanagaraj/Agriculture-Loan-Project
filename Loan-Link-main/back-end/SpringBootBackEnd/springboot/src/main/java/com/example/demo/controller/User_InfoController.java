package com.example.demo.controller;

import com.example.demo.dto.ReqRes;
import com.example.demo.entity.User_Info;
import com.example.demo.service.User_InfoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/users_list")
public class User_InfoController {

    

    @Autowired
    private User_InfoService user_InfoService;


    @GetMapping("/getUserByEmail")
    public ResponseEntity<ReqRes> getUserByEmail(@RequestParam String email) {
        ReqRes response = user_InfoService.getUserDetailsResponse(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    // @GetMapping("/getAllUsers")
    // public ResponseEntity<List<ReqRes>> getAllUsers() {
    //     List<ReqRes> response = user_InfoService.getAllUserDetails();
    //     return ResponseEntity.ok(response);
    // }
    @GetMapping("/getAllUsers")
public ResponseEntity<List<User_Info>> getAllUsers() {
    List<User_Info> users = user_InfoService.getAllUsers();
    return ResponseEntity.ok(users);
}

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> createUser(@RequestBody ReqRes reg) {
        ReqRes response = user_InfoService.registerUser(reg);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<ReqRes> logIn(@RequestBody ReqRes reg) {
        ReqRes response = user_InfoService.loginTHEDATA(reg);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/Login")
    public ResponseEntity<ReqRes> LogIN(@RequestBody ReqRes reg) {
        ReqRes response = user_InfoService.loginTHEDATA(reg);
        return ResponseEntity.ok(response);
    }
  
   
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Long id) {
        ReqRes response = user_InfoService.deleteUserById(id);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT.value()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    // @PutMapping("/updateUser/{id}")
    // public ResponseEntity<ReqRes> updateUser(@PathVariable Long id, @RequestBody ReqRes updatedDetails) {
    //     ReqRes response = user_InfoService.updateUserDetailsById(id, updatedDetails);
    //     return ResponseEntity.status(response.getStatusCode()).body(response);
    // }
//     @PutMapping("/updateUser/{id}")
// public ResponseEntity<ReqRes> updateUser(@PathVariable Long id, @RequestBody ReqRes updatedUserDetails) {
//     ReqRes response = user_InfoService.updateUserDetailsById(id, updatedUserDetails);
//     return ResponseEntity.status(response.getStatusCode()).body(response);
// }



@PutMapping("/updateUser/{id}")
public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User_Info updatedUser) {
    boolean isUpdated = user_InfoService.updateUserDetailsById(id, updatedUser);

    if (isUpdated) {
        return ResponseEntity.ok("User updated successfully");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}


@PutMapping("/updateName/{id}")
public ResponseEntity<String> updateUserName(@PathVariable Long id, @RequestBody String newName) {
    try {
        User_Info updatedUser = user_InfoService.updateUserName(id, newName);
        return ResponseEntity.ok("User name updated successfully. New Name: " + updatedUser.getName());
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}

    

}
