package com.example.Registation.Entity;

import jakarta.persistence.*;



@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;



    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;


    public enum TaskStatus {
        TO_DO("TO_DO"),
        IN_PROGRESS("IN PROGRESS"),
        COMPLETED("COMPLETED");

        private final String status;

        TaskStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }

        public static TaskStatus fromString(String text) {
            for (TaskStatus ts : TaskStatus.values()) {
                if (ts.status.equalsIgnoreCase(text)) {
                    return ts;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }

    public void setStatus(TaskStatus status) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public TaskStatus getStatus() {
        return status;
    }







}
