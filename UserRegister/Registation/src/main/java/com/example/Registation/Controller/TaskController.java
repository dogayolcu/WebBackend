package com.example.Registation.Controller;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Entity.Task;
import com.example.Registation.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO createdTask = taskService.createTask(taskDTO);
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer taskId) {
        TaskDTO task = taskService.findTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksByProjectId(@PathVariable Integer projectId) {
        List<TaskDTO> tasks = taskService.findTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Integer taskId, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        taskService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok().build();
    }



    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<?> assignTaskToUser(@PathVariable Integer taskId, @RequestBody Map<String, Integer> assignData) {
        taskService.assignTaskToUser(taskId, assignData.get("userId"));
        return ResponseEntity.ok().build();
    }


}

