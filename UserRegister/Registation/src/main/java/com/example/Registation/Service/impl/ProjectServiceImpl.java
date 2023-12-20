package com.example.Registation.Service.impl;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Entity.Project;
import com.example.Registation.Repo.ProjectRepository;
import com.example.Registation.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public String createProject(ProjectDTO projectDTO)
    {

        Project project = new Project(
                projectDTO.getId(),
                projectDTO.getName(),
                projectDTO.getDescription(),
                projectDTO.getStartDate(),
                projectDTO.getEndDate(),
                projectDTO.getStatus()
        );
        projectRepository.save(project);
        return project.getName();
    }


}