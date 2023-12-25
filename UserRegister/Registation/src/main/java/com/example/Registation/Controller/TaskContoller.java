package com.example.Registation.Controller;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TaskContoller {
    private TaskService taskService;
        @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
        String response = taskService.createTask(taskDTO);
        return ResponseEntity.ok(response);
    }
}
