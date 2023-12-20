package com.example.Registation.Service.impl;

import com.example.Registation.Entity.User;
import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Repo.UserRepository;
import  com.example.Registation.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getUsername(), user.getPassword());
    }



}