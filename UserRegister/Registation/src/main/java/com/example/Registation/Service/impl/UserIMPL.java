package com.example.Registation.Service.impl;

import com.example.Registation.Entity.User;
import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Repo.UserRepository;
import  com.example.Registation.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService
{
    @Autowired
    private UserRepository userRepository;
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



}