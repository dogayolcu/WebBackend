package com.example.Registation.Controller;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Dto.UserDTO;

import com.example.Registation.Service.impl.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<String> saveProject(@RequestBody ProjectDTO projectDTO) {
        String projectName = projectService.createProject(projectDTO);
        return ResponseEntity.ok(projectName);
    }

    @GetMapping("/user/{userId}/projects")
    public ResponseEntity<List<ProjectDTO>> getUserProjects(@PathVariable Integer userId) {
        List<ProjectDTO> projects = projectService.findProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<Set<UserDTO>> getProjectMembers(@PathVariable Integer projectId) {
        Set<UserDTO> members = projectService.findProjectMembersByProjectId(projectId);
        return ResponseEntity.ok(members);
    }



}