package com.ubakapp.ubakapp_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateProjectDTO {

    @NotBlank(message = "Project code is required")
    @Size(max = 20, message = "Project code cannot exceed 20 characters")
    private String projectCode;

    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String projectName;

    private String description;
    private String location;
    private String clientName;
    private String clientContact;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull(message = "Estimated cost is required")
    private BigDecimal estimatedCost;

    private String status;

    @NotNull(message = "Created by user ID is required")
    private Long createdBy;
}
