package com.student.management.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.student.management.dto.StudentDto;
import com.student.management.entity.Student;
import com.student.management.repository.StudentRepository;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class StudentServiceImplTest {

  @Mock private StudentRepository studentRepository;

  @InjectMocks private StudentServiceImpl studentService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllStudents() {
    // Arrange
    Student student1 = new Student(1L, "John", "Doe", "john@example.com");
    Student student2 = new Student(2L, "Jane", "Doe", "jane@example.com");
    List<Student> students = Arrays.asList(student1, student2);

    when(studentRepository.findAll()).thenReturn(students);

    // Act
    List<StudentDto> studentDto = studentService.getAllStudents();

    // Assert
    assertEquals(2, studentDto.size());
    assertEquals("John", studentDto.get(0).getFirstName());
    assertEquals("Jane", studentDto.get(1).getFirstName());
  }

  @Test
  public void testCreateStudent() {
    // Arrange
    StudentDto studentDto = new StudentDto(null, "John", "Doe", "john@example.com");
    Student student = new Student(null, "John", "Doe", "john@example.com");

    when(studentRepository.save(any(Student.class))).thenReturn(student);

    // Act
    studentService.createStudent(studentDto);

    // Assert
    verify(studentRepository, times(1)).save(any(Student.class));
  }

  @Test
  public void testUpdateStudent() {
    // Arrange
    StudentDto studentDto = new StudentDto(1L, "John", "Doe", "john@example.com");
    Student student = new Student(1L, "John", "Doe", "john@example.com");

    when(studentRepository.save(any(Student.class))).thenReturn(student);

    // Act
    studentService.updateStudent(studentDto);

    // Assert
    verify(studentRepository, times(1)).save(any(Student.class));
  }

  @Test
  public void testDeleteStudent() {
    // Arrange
    Long studentId = 1L;

    // Act
    studentService.deleteStudent(studentId);

    // Assert
    verify(studentRepository, times(1)).deleteById(studentId);
  }
}
