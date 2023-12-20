package com.example.Registation.Service;

import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Entity.User;

import java.util.List;

public interface UserService {
    String addUser(UserDTO userDTO);
    User loginUser(String username, String password);
    List<UserDTO> findAllUsers();
}
