package com.accesses.administrative_system.service;


import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){

        this.studentRepository = studentRepository;

    }

    public Optional<Student> findStudentById(Long id){
        return studentRepository.findById(id);
    }

    public List<Student> findAllStudents(){

        return studentRepository.findAll();

    }

    public ResponseEntity<String> createStudent(Student student){

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Estudante criado com succeso.");

    }

    public ResponseEntity<String> removeStudent(Long id){

        studentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("O estudante foi deletado com sucesso");

    }

}
