package com.ubakapp.ubakapp_backend.repository;

import com.ubakapp.ubakapp_backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(Project.ProjectStatus status);

    Optional<Project> findByProjectCode(String projectCode);

    List<Project> findByCreatedById(Long userId);
}