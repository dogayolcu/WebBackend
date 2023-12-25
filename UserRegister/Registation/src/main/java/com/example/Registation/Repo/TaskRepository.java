package com.example.Registation.Repo;

import com.example.Registation.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
