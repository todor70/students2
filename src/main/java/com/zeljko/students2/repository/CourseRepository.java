package com.zeljko.students2.repository;

import com.zeljko.students2.entity.Course;
import com.zeljko.students2.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findCoursesByStudents(Student student);

}
