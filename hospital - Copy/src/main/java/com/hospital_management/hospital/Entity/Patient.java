package com.hospital_management.hospital.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "patient_Name",nullable = false)
    private String patientName;

    @Column(name = "is_active", columnDefinition = "integer default 0")
    private int isActive;

    @Column(name = "is_delete", columnDefinition = "integer default 0")
    private int isDelete;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime updateDateTime;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "role_user_id")
    private User role;

}
