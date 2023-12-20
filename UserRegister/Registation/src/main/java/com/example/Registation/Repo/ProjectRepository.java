package com.example.Registation.Repo;

import com.example.Registation.Entity.Project;
import com.example.Registation.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}