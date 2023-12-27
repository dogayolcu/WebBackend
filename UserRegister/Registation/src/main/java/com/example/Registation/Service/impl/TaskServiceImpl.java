package com.example.Registation.Service.impl;

import com.example.Registation.Dto.TaskDTO;
import com.example.Registation.Entity.Project;
import com.example.Registation.Entity.Task;
import com.example.Registation.Entity.User;
import com.example.Registation.Repo.ProjectRepository;
import com.example.Registation.Repo.TaskRepository;
import com.example.Registation.Repo.UserRepository;
import com.example.Registation.Service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.getStatus() == null) {
            throw new IllegalArgumentException("Task status is required");
        }

        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setStatus(Task.TaskStatus.fromString(taskDTO.getStatus())); // String'den enum'a dönüşüm


        // projectId kontrolü
        if (taskDTO.getProjectId() != null) {
            Project project = projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        // assignedUserId kontrolü
        if (taskDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(taskDTO.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignedUser(user);
        }

        Task savedTask = taskRepository.save(task);
        return convertToTaskDTO(savedTask);
    }

    @Override
    public TaskDTO findTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToTaskDTO(task);
    }

    @Override
    public List<TaskDTO> findTasksByProjectId(Integer projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }

    private TaskDTO convertToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setStatus(task.getStatus().toString()); // Enum'dan String'e dönüşüm
        if (task.getProject() != null) {
            taskDTO.setProjectId(task.getProject().getId());
        }
        if (task.getAssignedUser() != null) {
            taskDTO.setAssignedUserId(task.getAssignedUser().getId());
        }
        return taskDTO;
    }


    @Override
    @Transactional
    public void updateTaskStatus(Integer taskId, String newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Task.TaskStatus statusEnum = Task.TaskStatus.fromString(newStatus); // String'den enum'a dönüşüm
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


}
