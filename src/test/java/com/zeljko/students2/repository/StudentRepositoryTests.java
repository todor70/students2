package com.zeljko.students2.repository;

import com.zeljko.students2.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void findStudentById() {
        Optional<Student> student = studentRepository.findById(1L);
        assertEquals("marko", student.get().getFirstName());
        assertEquals("markovic", student.get().getLastName());
        assertEquals("marko@gmail.com", student.get().getEmail());
    }

}
