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

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


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

    @PostMapping("/{projectId}/addMember")
    public ResponseEntity<?> addMemberToProject(@PathVariable Integer projectId, @RequestBody Map<String, String> requestBody) {
        try {
            projectService.addMemberToProject(projectId, requestBody.get("username"));
            return ResponseEntity.ok("Member added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}