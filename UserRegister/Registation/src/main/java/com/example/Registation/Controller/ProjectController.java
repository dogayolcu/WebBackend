package com.example.Registation.Controller;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Entity.User;
import com.example.Registation.Service.ProjectService;
import com.example.Registation.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping(path = "/save")
    public String saveProject(@RequestBody ProjectDTO projectDTO)
    {
        String id = String.valueOf(projectService.createProject(projectDTO));
        return id;
    }
}