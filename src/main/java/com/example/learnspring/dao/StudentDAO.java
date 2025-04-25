package com.example.learnspring.dao;

import com.example.learnspring.entity.Student;

import java.util.List;

public interface StudentDAO {
    void save(Student student);

    Student findById(Long id);

    Student update(Student student);

    void deleteById(Long id);

    List<Student> findByLastNameContaining(String lastName);

    List<Student> findByFirstNameContaining(String firstName);

    List<Student> searchStudents(String firstName, String lastName, String email);
}
