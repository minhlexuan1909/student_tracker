package com.example.learnspring.service;

import com.example.learnspring.dao.StudentDAO;
import com.example.learnspring.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    @Transactional
    public void save(Student student) {
        studentDAO.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentDAO.findById(id);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return studentDAO.update(student);
    }

    @Override
    @Transactional
    public void delete(Student student) {
        studentDAO.delete(student);
    }

    @Override
    @Transactional
    public void deleteAll() {
        studentDAO.deleteAll();
    }

    @Override
    public List<Student> findByLastNameContaining(String lastName) {
        return studentDAO.findByLastNameContaining(lastName);
    }

    @Override
    public List<Student> findByFirstNameContaining(String firstName) {
        return studentDAO.findByFirstNameContaining(firstName);
    }

    @Override
    public List<Student> searchStudents(String firstName, String lastName, String email) {
        return studentDAO.searchStudents(firstName, lastName, email);
    }
}
