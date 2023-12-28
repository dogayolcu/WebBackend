package com.example.Registation.Service.impl;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Entity.Project;
import com.example.Registation.Entity.Task;
import com.example.Registation.Entity.User;
import com.example.Registation.Repo.IProjectRepository;
import com.example.Registation.Repo.ITaskRepository;
import com.example.Registation.Repo.IUserRepository;
import com.example.Registation.Service.ITaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {
    private final IProjectRepository projectRepository;
    private final IUserRepository userRepository;
    private final ITaskRepository taskRepository;

    public TaskService(IProjectRepository projectRepository, IUserRepository userRepository,ITaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository=taskRepository;
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.getStatus() == null) {
            throw new IllegalArgumentException("Task status is required");
        }

        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setStatus(Task.TaskStatus.fromString(taskDTO.getStatus()));

        if (taskDTO.getProjectId() != null) {
            Project project = projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        if (taskDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(taskDTO.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignedUser(user);
        }

        Task savedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(savedTask);
    }


    @Override
    public TaskDTO findTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return TaskDTO.fromEntity(task);
    }


    @Override
    public List<TaskDTO> findTasksByProjectId(Integer projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }




    @Override
    @Transactional
    public void updateTaskStatus(Integer taskId, String newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Task.TaskStatus statusEnum = Task.TaskStatus.fromString(newStatus);
        task.setStatus(statusEnum);
        taskRepository.save(task);
    }



    @Override
    @Transactional
    public void assignTaskToUser(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setAssignedUser(user);
        taskRepository.save(task);
    }
    @Override
    public List<TaskDTO> findTasksByAssignedUserId(Integer userId) {
        return taskRepository.findByAssignedUserId(userId).stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
