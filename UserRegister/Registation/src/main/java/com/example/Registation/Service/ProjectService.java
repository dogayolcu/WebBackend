package com.example.Registation.Service;

import com.example.Registation.Dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    String createProject(ProjectDTO projectDTO);

    List<ProjectDTO> findProjectsByUserId(Integer userId);



}