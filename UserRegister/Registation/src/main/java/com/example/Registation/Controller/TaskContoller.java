package com.example.Registation.Controller;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskContoller {
    private final TaskService taskService;

    public TaskContoller(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
=======

public class TaskContoller {
    private TaskService taskService;
        @PostMapping
>>>>>>> 7ea5a997b7b555453cca59e0c98e49ad8950afe7
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
        String response = taskService.createTask(taskDTO);
        return ResponseEntity.ok(response);
    }
<<<<<<< HEAD

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateTaskStatus(@RequestBody TaskDTO taskDTO) {
        String response = taskService.updateTaskStatus(taskDTO.getId(), taskDTO.getStatus());
        return ResponseEntity.ok(response);
    }
=======
>>>>>>> 7ea5a997b7b555453cca59e0c98e49ad8950afe7
}
