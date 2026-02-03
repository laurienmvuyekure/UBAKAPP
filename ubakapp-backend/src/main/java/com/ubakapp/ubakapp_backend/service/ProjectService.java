package com.ubakapp.ubakapp_backend.service;

import com.ubakapp.ubakapp_backend.dto.CreateProjectDTO;
import com.ubakapp.ubakapp_backend.dto.ProjectDTO;
import com.ubakapp.ubakapp_backend.entity.Project;
import com.ubakapp.ubakapp_backend.entity.User;
import com.ubakapp.ubakapp_backend.exception.ResourceNotFoundException;
import com.ubakapp.ubakapp_backend.repository.ProjectRepository;
import com.ubakapp.ubakapp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public ProjectDTO createProject(CreateProjectDTO projectDTO) {
        // Check if project code already exists
        Optional<Project> existingProject = projectRepository.findByProjectCode(projectDTO.getProjectCode());
        if (existingProject.isPresent()) {
            throw new IllegalArgumentException("Project code already exists");
        }
        
        // Find user who created the project
        User createdBy = userRepository.findById(projectDTO.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + projectDTO.getCreatedBy()));
        
        // Create project entity
        Project project = new Project();
        project.setProjectCode(projectDTO.getProjectCode());
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        project.setLocation(projectDTO.getLocation());
        project.setClientName(projectDTO.getClientName());
        project.setClientContact(projectDTO.getClientContact());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setEstimatedCost(projectDTO.getEstimatedCost());
        project.setCreatedBy(createdBy);
        
        // Set default values
        project.setActualCost(projectDTO.getEstimatedCost().multiply(new BigDecimal("0"))); // Initialize to 0
        
        // Set status
        if (projectDTO.getStatus() != null && !projectDTO.getStatus().isEmpty()) {
            project.setStatus(Project.ProjectStatus.valueOf(projectDTO.getStatus()));
        } else {
            project.setStatus(Project.ProjectStatus.PLANNING);
        }
        
        // Save project
        Project savedProject = projectRepository.save(project);
        
        // Convert to DTO and return
        return convertToDTO(savedProject);
    }
    
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return convertToDTO(project);
    }
    
    @Transactional
    public ProjectDTO updateProject(Long id, CreateProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        
        // Update fields
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        project.setLocation(projectDTO.getLocation());
        project.setClientName(projectDTO.getClientName());
        project.setClientContact(projectDTO.getClientContact());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setEstimatedCost(projectDTO.getEstimatedCost());
        
        if (projectDTO.getStatus() != null && !projectDTO.getStatus().isEmpty()) {
            project.setStatus(Project.ProjectStatus.valueOf(projectDTO.getStatus()));
        }
        
        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }
    
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
    
    public List<ProjectDTO> getProjectsByStatus(String status) {
        Project.ProjectStatus projectStatus = Project.ProjectStatus.valueOf(status.toUpperCase());
        List<Project> projects = projectRepository.findByStatus(projectStatus);
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setProjectCode(project.getProjectCode());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setLocation(project.getLocation());
        dto.setClientName(project.getClientName());
        dto.setClientContact(project.getClientContact());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setEstimatedCost(project.getEstimatedCost());
        dto.setActualCost(project.getActualCost());
        dto.setStatus(project.getStatus().name());
        dto.setCreatedBy(project.getCreatedBy().getId());
        
        // Safely get user's name
        User createdBy = project.getCreatedBy();
        if (createdBy != null) {
            String firstName = createdBy.getFirstName() != null ? createdBy.getFirstName() : "";
            String lastName = createdBy.getLastName() != null ? createdBy.getLastName() : "";
            dto.setCreatedByName(firstName + " " + lastName);
        } else {
            dto.setCreatedByName("Unknown");
        }
        
        dto.setCreatedAt(project.getCreatedAt());
        return dto;
    }
}