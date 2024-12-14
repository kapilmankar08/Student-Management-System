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

  //handler method to handle new student request
  @GetMapping("/students/new")
  public String newStudent(Model model){
    StudentDto studentDto=new StudentDto();
    model.addAttribute("student",studentDto);
    return "create_student";
  }

  //handler method to handle save student form submit request
  @PostMapping("/students")
  public String saveStudent(@Valid @ModelAttribute("student") StudentDto studentDto, BindingResult result, Model model){

    if(result.hasErrors()){
      model.addAttribute("student",studentDto);
      return "create_student";
    }
    studentService.createStudent(studentDto);
    return "redirect:/students";
  }
}
