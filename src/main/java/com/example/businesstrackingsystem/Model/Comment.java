package com.example.businesstrackingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(columnDefinition = "varchar(20) not null")
    private Integer userId;
    @NotNull
    @Column(columnDefinition = "varchar(20) not null")
    private Integer taskId;

    @NotEmpty(message = "Content is required")
    @Column(columnDefinition = "varchar(100) not null")
    private String content;
}
