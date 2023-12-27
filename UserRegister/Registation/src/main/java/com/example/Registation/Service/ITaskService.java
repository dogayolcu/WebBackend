package com.example.Registation.Service;

import com.example.Registation.Dto.ProjectDTO;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Entity.Task;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ITaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO findTaskById(Integer taskId);

    List<TaskDTO> findTasksByProjectId(Integer projectId);

    void updateTaskStatus(Integer taskId, String newStatus);

    void assignTaskToUser(Integer taskId, Integer userId);


}
