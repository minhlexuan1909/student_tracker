package com.example.learnspring.service;

import com.example.learnspring.dao.StudentDAO;
import com.example.learnspring.entity.Student;
import com.example.learnspring.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, StudentRepository studentRepository) {
        this.studentDAO = studentDAO;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public void deleteAll() {
        studentRepository.deleteAll();
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
