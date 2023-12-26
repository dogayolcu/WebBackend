package com.example.Registation.Service.impl;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Entity.Task;
import com.example.Registation.Repo.TaskRepository;
import com.example.Registation.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceIMPL implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public String createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setProject(taskDTO.getProject());

        taskRepository.save(task);
        return task.getName();
    }
}