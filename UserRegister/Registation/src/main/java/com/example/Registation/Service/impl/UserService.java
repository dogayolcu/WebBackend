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
    public boolean isValidPassword(String password) {
        int minLength = 8;
        int maxLength = 20;

        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else hasSpecial = true;

            if (hasUpper && hasLower && hasDigit && hasSpecial) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String addUser(UserDTO userDTO)
    {
        if (!isValidPassword(userDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
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
        String verificationCode = String.valueOf(new Random().nextInt(899999) + 100000);
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        return verificationCode;
    }







}