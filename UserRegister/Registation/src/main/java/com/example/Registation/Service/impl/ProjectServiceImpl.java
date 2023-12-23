package com.example.Registation.Service.impl;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Dto.UserDTO;
import com.example.Registation.Entity.Project;
import com.example.Registation.Entity.User;
import com.example.Registation.Repo.ProjectRepository;
import com.example.Registation.Repo.UserRepository;
import com.example.Registation.Service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public String createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(projectDTO.getStatus());

        if (projectDTO.getUserIds() != null && !projectDTO.getUserIds().isEmpty()) {
            Set<User> users = projectDTO.getUserIds().stream()
                    .map(userId -> userRepository.findById(userId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            project.setUsers(users);
        }

        projectRepository.save(project);
        return project.getName();
    }


    @Override
    public List<ProjectDTO> findProjectsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }

        return user.getProjects().stream()
                .map(this::convertToProjectDTO)
                .collect(Collectors.toList());
    }

    private ProjectDTO convertToProjectDTO(Project project) {

        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());
        return dto;
    }

    @Override
    public Set<UserDTO> findProjectMembersByProjectId(Integer projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (!projectOptional.isPresent()) {
            return Collections.emptySet();
        }

        Project project = projectOptional.get();
        Set<User> members = project.getUsers();
        return members.stream().map(this::convertToUserDTO).collect(Collectors.toSet());
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword()
        );
    }
    @Override
    @Transactional
    public void addMemberToProject(Integer projectId, String username) throws Exception {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new Exception("User not found");
        }

        project.getUsers().add(user);
        projectRepository.save(project);
    }

}
