package com.example.businesstrackingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String status;
    @NotEmpty(message = "description must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime submitDate;
    private Integer user_id;

}
