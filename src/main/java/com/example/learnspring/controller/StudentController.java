package com.example.learnspring.controller;

import com.example.learnspring.entity.Student;
import com.example.learnspring.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentDAO.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentDAO.findById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {

        List<Student> students = studentDAO.searchStudents(firstName, lastName, email);

        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        // Check if student exists
        Student existingStudent = studentDAO.findById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }

        // Set the id to ensure we update the existing record
        updatedStudent.setId(id);

        // Update the student
        Student result = studentDAO.update(updatedStudent);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        // Check if student exists
        Student existingStudent = studentDAO.findById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete the student
        studentDAO.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
