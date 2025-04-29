package com.example.learnspring.service;

import com.example.learnspring.entity.Student;

import java.util.List;

public interface StudentService {
    void save(Student student);

    Student findById(Long id);

    Student update(Student student);

    void delete(Student student);

    List<Student> findByLastNameContaining(String lastName);

    List<Student> findByFirstNameContaining(String firstName);

    List<Student> searchStudents(String firstName, String lastName, String email);

    void deleteAll();
}
