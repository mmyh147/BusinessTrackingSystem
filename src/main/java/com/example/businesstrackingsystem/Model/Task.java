package com.example.businesstrackingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty(message = "title must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String title;
    @Column(columnDefinition = "varchar(15) not null check(status = 'in-progress' or status = 'in-review'  or status = 'to-do' or status = 'complete')")
    @Pattern(regexp = "(in-progress|in-review|submit|to-do|complete)", message = "Status must be 'in-progress', 'in-review', 'submit', or 'to-do'")
    private String status;
    @Column(columnDefinition = "varchar(10) not null check(review = 'reject' or review = 'approve')")
    @Pattern(regexp = "(approve|reject)", message = "Status must be 'in-progress', 'in-review', 'submit', or 'to-do'")
    private String review;
    @NotEmpty(message = "description must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime submitDate;
    private LocalDateTime createAt;
    private Integer userId;

}
