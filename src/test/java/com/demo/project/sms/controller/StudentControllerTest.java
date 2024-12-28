package com.demo.project.sms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.demo.project.sms.dto.StudentDto;
import com.demo.project.sms.service.StudentService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

  @Autowired private MockMvc mockMvc; // Automatically injected MockMvc instance

  @MockBean private StudentService studentService;

  @Test
  public void testListStudents() throws Exception {
    // Given
    List<StudentDto> students =
        Arrays.asList(
            new StudentDto(1L, "John", "Doe", "john.doe@example.com"),
            new StudentDto(2L, "Jane", "Doe", "jane.doe@example.com"));
    when(studentService.getAllStudents()).thenReturn(students);

    // When & Then
    mockMvc
        .perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(view().name("students"))
        .andExpect(model().attribute("students", students));

    verify(studentService, times(1)).getAllStudents();
  }

  @Test
  public void testNewStudent() throws Exception {
    // When & Then
    mockMvc
        .perform(get("/students/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("create_student"))
        .andExpect(model().attributeExists("student"));
  }

  @Test
  public void testSaveStudent() throws Exception {
    // Given
    StudentDto studentDto = new StudentDto(null, "John", "Doe", "john.doe@example.com");
    doNothing().when(studentService).createStudent(any(StudentDto.class));

    // When & Then
    mockMvc
        .perform(
            post("/students")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/students"));

    verify(studentService, times(1)).createStudent(any(StudentDto.class));
  }

  @Test
  public void testEditStudent() throws Exception {
    // Given
    Long studentId = 1L;
    StudentDto studentDto = new StudentDto(studentId, "John", "Doe", "john.doe@example.com");
    when(studentService.getStudentById(studentId)).thenReturn(studentDto);

    // When & Then
    mockMvc
        .perform(get("/students/{studentId}/edit", studentId))
        .andExpect(status().isOk())
        .andExpect(view().name("edit_student"))
        .andExpect(model().attribute("student", studentDto));

    verify(studentService, times(1)).getStudentById(studentId);
  }

  @Test
  public void testUpdateStudent() throws Exception {
    // Given
    Long studentId = 1L;
    StudentDto studentDto = new StudentDto(studentId, "John", "Doe", "john.doe@example.com");
    doNothing().when(studentService).updateStudent(any(StudentDto.class));

    // When & Then
    mockMvc
        .perform(
            post("/students/{studentId}", studentId)
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/students"));

    verify(studentService, times(1)).updateStudent(any(StudentDto.class));
  }

  @Test
  public void testDeleteStudent() throws Exception {
    // Given
    Long studentId = 1L;
    doNothing().when(studentService).deleteStudent(studentId);

    // When & Then
    mockMvc
        .perform(get("/students/{studentId}/delete", studentId))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/students"));

    verify(studentService, times(1)).deleteStudent(studentId);
  }

  @Test
  public void testViewStudent() throws Exception {
    // Given
    Long studentId = 1L;
    StudentDto studentDto = new StudentDto(studentId, "John", "Doe", "john.doe@example.com");
    when(studentService.getStudentById(studentId)).thenReturn(studentDto);

    // When & Then
    mockMvc
        .perform(get("/students/{studentId}/view", studentId))
        .andExpect(status().isOk())
        .andExpect(view().name("view_students"))
        .andExpect(model().attribute("student", studentDto));

    verify(studentService, times(1)).getStudentById(studentId);
  }
}
