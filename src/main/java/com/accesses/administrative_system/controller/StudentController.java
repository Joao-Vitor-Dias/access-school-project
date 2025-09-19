package com.accesses.administrative_system.controller;


import com.accesses.administrative_system.dto.student.StudentRequestDTO;
import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){

        this.studentService = studentService;

    }

    @GetMapping()
    public List<Student> findAllStudents(){

        return studentService.findAllStudents();

    }

    @GetMapping("/{id}")
    public Optional<Student> findStudentById(@PathVariable Long id){

        return studentService.findStudentById(id);

    }

    @PostMapping("/create")
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentRequestDTO dto){

        return studentService.createStudent(dto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeStudent(@PathVariable Long id){

        return studentService.removeStudent(id);

    }

}
