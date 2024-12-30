package com.student.management.controller;

import com.student.management.dto.StudentDto;
import com.student.management.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

  private StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  // handler method to handle list students request
  @GetMapping({"/students"})
  public String listStudents(Model model) {
    List<StudentDto> students = studentService.getAllStudents();
    model.addAttribute("students", students);
    return "students";
  }

  // handler method to handle new student request
  @GetMapping("/students/new")
  public String newStudent(Model model) {
    StudentDto studentDto = new StudentDto();
    model.addAttribute("student", studentDto);
    return "create_student";
  }

  // handler method to handle save student form submit request
  @PostMapping("/students")
  public String saveStudent(
      @Valid @ModelAttribute("student") StudentDto studentDto, BindingResult result, Model model) {

    if (result.hasErrors()) {
      model.addAttribute("student", studentDto);
      return "create_student";
    }
    studentService.createStudent(studentDto);
    return "redirect:/students";
  }

  @GetMapping("/students/{studentId}/edit")
  public String editStudent(@PathVariable("studentId") Long studentId, Model model) {
    StudentDto studentDto = studentService.getStudentById(studentId);
    model.addAttribute("student", studentDto);
    return "edit_student";
  }

  // handler method to handle the edit response of the existing student

  @PostMapping("/students/{studentId}")
  public String updateStudent(
      @PathVariable("studentId") Long id,
      @Valid @ModelAttribute("student") StudentDto studentDto,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("student", studentDto);
      return "edit_student";
    }
    studentDto.setId(id);
    studentService.updateStudent(studentDto);
    return "redirect:/students";
  }

  // handler method to handle delete request
  @GetMapping("/students/{studentId}/delete")
  public String deleteStudent(@PathVariable("studentId") Long id) {
    studentService.deleteStudent(id);
    return "redirect:/students";
  }

  // handler method to handle delete request
  @GetMapping("students/{studentId}/view")
  public String viewStudent(@PathVariable("studentId") Long id, Model model) {
    StudentDto studentDto = studentService.getStudentById(id);
    model.addAttribute("student", studentDto);
    return "view_students";
  }
}
