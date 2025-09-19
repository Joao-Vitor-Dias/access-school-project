package com.accesses.administrative_system.entity;

import com.accesses.administrative_system.util.CalculateAge;
import jakarta.persistence.*;
import lombok.*;

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
    private int age;

    @Column(nullable = false)
    private LocalDate birthDay;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private LocalDate enrollDay;

    @Column(nullable = false)
    private boolean isLegalAge;

    @Column(nullable = false)
    private boolean isDependent;

    @Column
    private String parentName;

    @Column
    private String parentPhone;

    public Student(String firstName,
                   String secondName,
                   LocalDate birthDay,
                   String telephone,
                   LocalDate enrollDay,
                   boolean isDependent,
                   String parentName,
                   String parentPhone){

        this.firstName = firstName;

        this.secondName = secondName;

        this.age = CalculateAge.calculateAge(birthDay);

        this.birthDay = birthDay;

        this.telephone = telephone;

        this.enrollDay = enrollDay;

        this.isLegalAge = CalculateAge.verifyIsLegal(birthDay);

        this.isDependent = isDependent;

        this.parentName = parentName;

        this.parentPhone = parentPhone;

    }

}
