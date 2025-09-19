package com.accesses.administrative_system.message_api;

import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class Message {

    @Autowired
    private StudentService studentService;

    private String api = "https://localhost:5500";

    public void sendMenssage(Long studentId, String message){

        Optional<Student> student = studentService.findStudentById(studentId);

        String telephone;

        if (student.isPresent()){
            telephone = student.get().getTelephone();
        }

        // call api and pass the telephone and message

    }

    /*
    *
    * url -> body Json with message and studentPhone
    *
    *
    *
    * */

}
