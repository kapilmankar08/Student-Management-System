package com.student.management.service.impl;

import com.student.management.dto.StudentDto;
import com.student.management.entity.Student;
import com.student.management.mapper.StudentMapper;
import com.student.management.repository.StudentRepository;
import com.student.management.service.StudentService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;

  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<StudentDto> getAllStudents() {
    List<Student> students = studentRepository.findAll();
    List<StudentDto> studentDtos =
        students.stream()
            .map((student) -> StudentMapper.mapToStudentDto(student))
            .collect(Collectors.toList());
    return studentDtos;
  }

  @Override
  public void createStudent(StudentDto studentDto) {
    Student student = StudentMapper.mapToStudent(studentDto);
    studentRepository.save(student);
  }

  @Override
  public StudentDto getStudentById(Long studentId) {
    Student student = studentRepository.findById(studentId).get();
    return StudentMapper.mapToStudentDto(student);
  }

  @Override
  public void updateStudent(StudentDto studentDto) {
    studentRepository.save(StudentMapper.mapToStudent(studentDto));
  }

  @Override
  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }
}
