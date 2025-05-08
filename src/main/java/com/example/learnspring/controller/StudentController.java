package com.example.learnspring.controller;

import com.example.learnspring.entity.Student;
import com.example.learnspring.service.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public StudentController(StudentService studentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.save(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student == null) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }
        return student;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {

        List<Student> students = studentService.searchStudents(firstName, lastName, email);

        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        // Check if student exists
        Student existingStudent = studentService.findById(id);
        if (existingStudent == null) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }

        // Set the id to ensure we update the existing record
        updatedStudent.setId(id);

        // Update the student
        return studentService.update(updatedStudent);
    }

    @PatchMapping("/{id}")
    public Student partialUpdateStudent(@PathVariable Long id, @RequestBody JsonNode patch) {
        // Check if student exists
        Student existingStudent = studentService.findById(id);
        if (existingStudent == null) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }

        try {
            // Convert existing student to JsonNode
            JsonNode studentNode = objectMapper.convertValue(existingStudent, JsonNode.class);

            // Apply the patch (merge updates into the existing JsonNode)
            JsonNode patchedNode = objectMapper.readerForUpdating(studentNode).readValue(patch);

            // Convert patched JsonNode back to Student object
            Student patchedStudent = objectMapper.treeToValue(patchedNode, Student.class);

            // Ensure the ID remains unchanged
            patchedStudent.setId(id);

            // Update the student in the database
            return studentService.update(patchedStudent);
        } catch (Exception e) {
            throw new RuntimeException("Error applying patch to student: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        // Check if student exists
        Student existingStudent = studentService.findById(id);
        if (existingStudent == null) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }

        // Delete the student directly using the found instance
        studentService.delete(existingStudent);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllStudents() {
        studentService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
