package com.example.Registation.Dto;

import com.example.Registation.Entity.Project;
import com.example.Registation.Entity.Task;
import com.example.Registation.Entity.User;
import jakarta.persistence.*;

public class TaskDTO {
    private Integer id;
    private String name;
    private String status;
    private Integer assignedUserId;
    private Integer projectId;
    private Integer userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}


