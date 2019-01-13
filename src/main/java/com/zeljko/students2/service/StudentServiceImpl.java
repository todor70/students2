package com.zeljko.students2.service;

import com.zeljko.students2.entity.Course;
import com.zeljko.students2.entity.Student;
import com.zeljko.students2.repository.CourseRepository;
import com.zeljko.students2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void saveStudent(Student student) {

        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Student student) {

        for (Course c : courseRepository.findCoursesByStudents(student)) {
            student.addCourse(c);
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(long id) {

        studentRepository.deleteById(id);
    }
}