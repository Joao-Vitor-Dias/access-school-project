package com.accesses.administrative_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_tb")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private LocalDate enrollDay;

    @Column(nullable = false)
    private boolean isLegalAge;

}
