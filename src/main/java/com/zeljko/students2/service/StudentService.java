package com.zeljko.students2.service;

import com.zeljko.students2.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(long id);

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(long id);
}
