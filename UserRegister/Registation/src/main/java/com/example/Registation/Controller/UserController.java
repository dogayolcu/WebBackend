package com.example.Registation.Controller;

import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Service.impl.EmailService;
import com.example.Registation.Entity.User;
import com.example.Registation.Repo.IUserRepository;
import com.example.Registation.Service.impl.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final IUserRepository userRepository;

    private final EmailService emailService;

    public UserController(UserService userService, IUserRepository userRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        User user = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveEmployee(@RequestBody UserDTO userDTO) {
        try {
            String id = userService.addUser(userDTO);
            boolean emailSent = emailService.sendSimpleEmail(userDTO.getMail(), "Welcome", "Your registration has been completed successfully!");
            if (emailSent) {
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email could not be sent.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kullanıcı kaydedilirken bir hata oluştu.");
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String userEmail = payload.get("email");
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            String verificationCode = userService.generateVerificationCode(userEmail);
            emailService.sendSimpleEmail(userEmail, "Password Reset", "Your verification code: " + verificationCode);
            return ResponseEntity.ok("Password reset instructions have been sent to your email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String verificationCode = payload.get("verificationCode");
        String newPassword = payload.get("newPassword");
        if (email == null || verificationCode == null || newPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing fields in request.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if (!user.getVerificationCode().equals(verificationCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
        }

        user.setPassword(newPassword);
        user.setVerificationCode(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password has been successfully reset.");
    }




    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getUsername(), user.getPassword());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
