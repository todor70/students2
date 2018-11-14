package com.zeljko.students2.service;

import com.zeljko.students2.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();

    Optional<Course> getCourseById(long id);
}
