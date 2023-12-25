package com.example.Registation.Service;

import com.example.Registation.Dto.ProjectDTO;
import com.example.Registation.Dto.TaskDTO;
import jakarta.transaction.Transactional;

public interface TaskService {
    String createTask(TaskDTO taskDTO);
}
