package com.zeljko.students2.controller;

import com.zeljko.students2.entity.Course;
import com.zeljko.students2.entity.Student;
import com.zeljko.students2.service.CourseService;
import com.zeljko.students2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	private StudentService studentService;
	private CourseService courseService;

	@Autowired
	public StudentController(StudentService studentService, CourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
	}

	@RequestMapping("/students")
	public String list(Model model) {
		List<Student> students = studentService.getAllStudents();
		model.addAttribute("students", students);
		return "student_list";
	}

	@RequestMapping(value = "/add")
	public String addStudent(Model model){
		model.addAttribute("student", new Student());
		return "add_student";
	}

	@RequestMapping(value = "/update/{id}")
	public String updateStudent(@PathVariable("id") Long studentId, Model model){
		model.addAttribute("student", studentService.getStudentById(studentId));
		return "update_student";
	}

	@PostMapping("/save")
	public String save(@Valid Student student, BindingResult theBindingResult){

		if (theBindingResult.hasErrors()) {
			return "add_student";
		}

		studentService.saveStudent(student);
		return "redirect:/students";
	}

	@PostMapping("/update")
	public String update(@Valid Student student, BindingResult theBindingResult){

		if (theBindingResult.hasErrors()) {
			return "update_student";
		}

		studentService.updateStudent(student);
		return "redirect:/students";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") Long studentId) {
		studentService.deleteStudentById(studentId);
		return "redirect:/students";
	}

	@GetMapping("/addStudentCourse/{id}")
	public String addCourse(@PathVariable("id") Long studentId, Model model){

		model.addAttribute("courses", courseService.getAllCourses());
		model.addAttribute("student", studentService.getStudentById(studentId).get());
		return "add_student_course";
	}


	@GetMapping("/student/{id}/courses")
	public String studentsAddCourse(@RequestParam(value="action", required=true) String action, @PathVariable Long id, @RequestParam Long courseId, Model model) {
		Optional<Course> course = courseService.getCourseById(courseId);
		Optional<Student> student = studentService.getStudentById(id);

		if (student.isPresent() && action.equalsIgnoreCase("save")) {
			if (!student.get().hasCourse(course.get())) {
				student.get().getCourses().add(course.get());
			}
			studentService.saveStudent(student.get());
			model.addAttribute("student", courseService.getCourseById(id));
			model.addAttribute("courses", courseService.getAllCourses());
			return "redirect:/students";
		}

		return "redirect:/students";
	}

}