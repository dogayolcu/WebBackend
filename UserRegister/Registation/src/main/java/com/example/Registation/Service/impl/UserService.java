package com.example.Registation.Service.impl;

import com.example.Registation.Entity.User;
import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Repo.IUserRepository;
import com.example.Registation.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public  UserService(IUserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    @Override
    public String addUser(UserDTO userDTO)
    {

        User user = new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getMail(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
        userRepository.save(user);
        return user.getUsername();
    }
    @Override
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public String generateVerificationCode(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        String verificationCode = generateRandomCode();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        return verificationCode;
    }

    public boolean resetPassword(String email, String verificationCode, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getVerificationCode().equals(verificationCode)) {
            return false;
        }
        user.setPassword(newPassword);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }



}