package net.javaguides.sms.controller;

import jakarta.validation.Valid;
import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
}
