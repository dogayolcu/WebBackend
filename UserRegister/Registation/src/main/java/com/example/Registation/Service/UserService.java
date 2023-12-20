package com.example.Registation.Service;

import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Entity.User;

public interface UserService {
    String addUser(UserDTO userDTO);
    User loginUser(String username, String password);
}
