package com.ubakapp.ubakapp_backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProjectDTO {
    private Long id;
    private String projectCode;
    private String projectName;
    private String description;
    private String location;
    private String clientName;
    private String clientContact;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private String status;
    private Long createdBy;
    private String createdByName;
    private LocalDateTime createdAt;
}
