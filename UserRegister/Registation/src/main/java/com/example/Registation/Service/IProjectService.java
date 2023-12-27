package com.example.Registation.Service;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Dto.UserDTO;

import java.util.List;
import java.util.Set;

public interface IProjectService {
    String createProject(ProjectDTO projectDTO);
    List<ProjectDTO> findProjectsByUserId(Integer userId);
    Set<UserDTO> findProjectMembersByProjectId(Integer projectId);
    void addMemberToProject(Integer projectId, String username) throws Exception;

}