package com.example.demo.service;

import com.example.demo.dto.ReqRes;
import com.example.demo.entity.User_Info;
import com.example.demo.repository.User_InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class User_InfoService {
    public List<User_Info> getAllUsers() {
        return user_InfoRepository.findAll();
    }
    // public List<User_Info> getAllUsers() {
    //     return user_InfoRepository.findAll();
    // }
    

    public ReqRes mapUserToReqRes(User_Info user) {
        ReqRes response = new ReqRes();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        // Include more fields if necessary
        return response;
    }

    public List<ReqRes> getAllUserDetails() {
        List<User_Info> users = getAllUsers();
        return users.stream().map(this::mapUserToReqRes).toList();
    }

    @Autowired
    private User_InfoRepository user_InfoRepository;

    public Optional<User_Info> getUserByEmail(String email) {
        return user_InfoRepository.findByEmail(email);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ReqRes getUserDetailsResponse(String email) {
        Optional<User_Info> user = getUserByEmail(email);
        ReqRes response = new ReqRes();

        if (user.isPresent()) {
            User_Info userInfo = user.get();
            response.setStatusCode(200);
            response.setMessage("User found");
            response.setName(userInfo.getName());
            response.setEmail(userInfo.getEmail());
            response.setRole(userInfo.getRole());
            response.setPassword(passwordEncoder.encode(userInfo.getPassword())); // Include password if necessary
        } else {
            response.setStatusCode(404);
            response.setError("User not found");
        }
        return response;
    }

    public User_Info createUserFromReqRes(ReqRes reg) {
        User_Info newUser = new User_Info();
        newUser.setName(reg.getName());
        newUser.setEmail(reg.getEmail());
        newUser.setPassword(passwordEncoder.encode(reg.getPassword()));
        newUser.setRole(reg.getRole());
        return newUser;
    }

    public User_Info saveUser(User_Info user) {
        return user_InfoRepository.save(user);
    }

    public ReqRes registerUser(ReqRes reg) {
        User_Info newUser = createUserFromReqRes(reg);
        User_Info savedUser = saveUser(newUser);

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("User registered successfully");
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setPassword(savedUser.getPassword()); // Include password if necessary
        return response;
    }
    @Autowired
    private JWTUtils jwtUtilsObj;
    @Autowired
    private AuthenticationManager AuthenticationManagerObj;
    public ReqRes  loginTHEDATA(ReqRes reg)
    {
        ReqRes obji = new ReqRes();
        try{

            AuthenticationManagerObj.authenticate(new UsernamePasswordAuthenticationToken( reg.getEmail(),reg.getPassword()));
            var user = user_InfoRepository.findByEmail(reg.getEmail()).orElseThrow();
            var jwt=jwtUtilsObj.generateToken(user);
            var refreshToken=jwtUtilsObj.generateRefreshToken(new HashMap<>(),user );
            obji.setStatusCode(200);
            obji.setToken(jwt);
            obji.setName(user.getName());
            obji.setEmail(user.getEmail());
            obji.setRole(user.getRole());
            obji.setRefreshToken(refreshToken);
            obji.setExpirationTime("24Hrs");
            obji.setMessage("Successfully Logged In");
        }
        catch(Exception e)
        {
            obji.setStatusCode(500);
            obji.setMessage(e.getMessage());
        }
        return obji;
    }
   
    

    public ReqRes updateUserDetails(String email, ReqRes updatedUserDetails) {
        Optional<User_Info> userOptional = user_InfoRepository.findByEmail(email);
        ReqRes response = new ReqRes();

        if (userOptional.isPresent()) {
            User_Info user = userOptional.get();
            // Update user fields with new data
            user.setName(updatedUserDetails.getName());
            if (!updatedUserDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
            }
            user.setRole(updatedUserDetails.getRole());
            // Save the updated user back to the repository
            User_Info updatedUser = user_InfoRepository.save(user);

            // Prepare the response
            response.setStatusCode(200);
            response.setMessage("User details updated successfully");
            response.setName(updatedUser.getName());
            response.setEmail(updatedUser.getEmail());
            response.setRole(updatedUser.getRole());
            response.setPassword(updatedUser.getPassword()); // Optional, depending on your needs
        } else {
            response.setStatusCode(404);
            response.setError("User not found");
        }

        return response;
    }


    public ReqRes updateUserDetailsByEmail(String email, ReqRes updatedUserDetails) {
        Optional<User_Info> userOptional = user_InfoRepository.findByEmail(email);
        ReqRes response = new ReqRes();

        if (userOptional.isPresent()) {
            User_Info user = userOptional.get();
            // Update user fields with new data
            user.setName(updatedUserDetails.getName());
            if (updatedUserDetails.getPassword() != null && !updatedUserDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
            }
            user.setRole(updatedUserDetails.getRole());
            // Save the updated user back to the repository
            User_Info updatedUser = user_InfoRepository.save(user);

            // Prepare the response
            response.setStatusCode(200);
            response.setMessage("User details updated successfully");
            response.setName(updatedUser.getName());
            response.setEmail(updatedUser.getEmail());
            response.setRole(updatedUser.getRole());
        } else {
            response.setStatusCode(404);
            response.setError("User not found");
        }

        return response;
    }

     public ReqRes deleteUserByEmail(String email) {
        Optional<User_Info> userOpt = user_InfoRepository.findByEmail(email);
        ReqRes response = new ReqRes();

        if (userOpt.isPresent()) {
            user_InfoRepository.delete(userOpt.get());
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            response.setMessage("User deleted successfully");
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setError("User not found");
        }
        return response;
    }
    public ReqRes deleteUserById(Long id) {
        Optional<User_Info> userOpt = user_InfoRepository.findById(id);
        ReqRes response = new ReqRes();

        if (userOpt.isPresent()) {
            user_InfoRepository.delete(userOpt.get());
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            response.setMessage("User deleted successfully");
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setError("User not found");
        }
        return response;
    }
    // public ReqRes updateUserDetailsById(Long id, ReqRes updatedDetails) {
    //     Optional<User_Info> userOptional = user_InfoRepository.findById(id);
    //     ReqRes response = new ReqRes();

    //     if (userOptional.isPresent()) {
    //         User_Info user = userOptional.get();
    //         user.setName(updatedDetails.getName());
    //         if (updatedDetails.getPassword() != null && !updatedDetails.getPassword().isEmpty()) {
    //             user.setPassword(passwordEncoder.encode(updatedDetails.getPassword()));
    //         }
    //         user.setRole(updatedDetails.getRole());
    //         User_Info updatedUser = user_InfoRepository.save(user);

    //         response.setStatusCode(200);
    //         response.setMessage("User details updated successfully");
    //         response.setName(updatedUser.getName());
    //         response.setEmail(updatedUser.getEmail());
    //         response.setRole(updatedUser.getRole());
    //     } else {
    //         response.setStatusCode(404);
    //         response.setError("User not found");
    //     }
    //     return response;
    // }

    // public ReqRes updateUserDetailsById(Long id, ReqRes updatedUserDetails) {
    //     Optional<User_Info> userOptional = user_InfoRepository.findById(id);
    //     ReqRes response = new ReqRes();
    
    //     if (userOptional.isPresent()) {
    //         User_Info user = userOptional.get();
    //         // Update user fields with new data
    //         user.setName(updatedUserDetails.getName());
    //         if (updatedUserDetails.getPassword() != null && !updatedUserDetails.getPassword().isEmpty()) {
    //             user.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
    //         }
    //         user.setRole(updatedUserDetails.getRole());
    //         // Save the updated user back to the repository
    //         User_Info updatedUser = user_InfoRepository.save(user);
    
    //         // Prepare the response
    //         response.setStatusCode(200);
    //         response.setMessage("User details updated successfully");
    //         response.setName(updatedUser.getName());
    //         response.setEmail(updatedUser.getEmail());
    //         response.setRole(updatedUser.getRole());
    //     } else {
    //         response.setStatusCode(404);
    //         response.setError("User not found");
    //     }
    
    //     return response;
    // }
    public boolean updateUserDetailsById(Long id, User_Info updatedUser) {
        // Find the existing user
        Optional<User_Info> existingUserOpt = user_InfoRepository.findById(id);
    
        if (existingUserOpt.isPresent()) {
            User_Info existingUser = existingUserOpt.get();
            
            // Update user details
            existingUser.setName(updatedUser.getName());
            // Only update fields that should be changed, and avoid setting the email if it's not intended
            // existingUser.setEmail(updatedUser.getEmail()); // Avoid updating email if not intended
    
            // Save updated user
            user_InfoRepository.save(existingUser);
            
            return true;
        } else {
            return false;
        }
    }
    

    @Autowired
    private User_InfoRepository userRepository;

    public Optional<User_Info> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User_Info updateUserName(Long id, String newName) {
        Optional<User_Info> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            User_Info user = userOptional.get();
            user.setName(newName);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    public ReqRes checkLoginCredentials(String email, String password) {
        ReqRes response = new ReqRes();
        
        try {
            // Authenticate the user's credentials
            AuthenticationManagerObj.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            
            // Fetch user details from the database
            Optional<User_Info> userOptional = user_InfoRepository.findByEmail(email);
            
            if (userOptional.isPresent()) {
                User_Info user = userOptional.get();
                String token = jwtUtilsObj.generateToken(user);
                String refreshToken = jwtUtilsObj.generateRefreshToken(new HashMap<>(), user);
                
                // Prepare successful response
                response.setStatusCode(200);
                response.setToken(token);
                response.setRefreshToken(refreshToken);
                response.setName(user.getName());
                response.setEmail(user.getEmail());
                response.setRole(user.getRole());
                response.setExpirationTime("24Hrs");
                response.setMessage("Login successful");
            } else {
                // User not found
                response.setStatusCode(404);
                response.setError("User not found");
            }
        } catch (Exception e) {
            // Authentication failed
            response.setStatusCode(401);
            response.setError("Invalid email or password");
        }
        
        return response;
    }
    
    

}
