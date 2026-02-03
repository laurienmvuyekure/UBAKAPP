package com.ubakapp.ubakapp_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_code", unique = true, nullable = false, length = 20)
    private String projectCode;

    @Column(name = "project_name", nullable = false, length = 100)
    private String projectName;

    @Lob
    private String description;

    @Lob
    private String location;

    @Column(name = "client_name", length = 100)
    private String clientName;

    @Column(name = "client_contact", length = 50)
    private String clientContact;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "estimated_cost", precision = 15, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "actual_cost", precision = 15, scale = 2)
    @Builder.Default // ADD THIS
    private BigDecimal actualCost = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Builder.Default // ADD THIS
    private ProjectStatus status = ProjectStatus.PLANNING;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum ProjectStatus {
        PLANNING, ONGOING, ON_HOLD, COMPLETED, CANCELLED
    }
}
