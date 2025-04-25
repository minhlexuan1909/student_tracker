package com.example.learnspring.dao;

import com.example.learnspring.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    EntityManager entityManager;

    StudentDAOImpl() {
        // Constructor logic if needed
    }

    @Autowired
    StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // First find the student
        Student student = entityManager.find(Student.class, id);

        // Then delete the student if found
        if (student != null) {
            entityManager.remove(student);
        }
    }

    @Override
    public List<Student> findByLastNameContaining(String lastName) {
        // Create a query using JPQL with LIKE operator
        TypedQuery<Student> query = entityManager.createQuery(
                "FROM Student WHERE lastName LIKE :searchName", Student.class);

        // Set the parameter and use % for wildcard matching
        query.setParameter("searchName", "%" + lastName + "%");

        // Execute and return the results
        return query.getResultList();
    }

    @Override
    public List<Student> findByFirstNameContaining(String firstName) {
        // Create a query using JPQL with LIKE operator for firstName
        TypedQuery<Student> query = entityManager.createQuery(
                "FROM Student WHERE firstName LIKE :searchName", Student.class);

        // Set the parameter and use % for wildcard matching
        query.setParameter("searchName", "%" + firstName + "%");

        // Execute and return the results
        return query.getResultList();
    }

    @Override
    public List<Student> searchStudents(String firstName, String lastName, String email) {
        StringBuilder queryBuilder = new StringBuilder("FROM Student WHERE 1=1");
        ArrayList<String> paramNames = new ArrayList<>();
        ArrayList<String> paramValues = new ArrayList<>();

        if (firstName != null && !firstName.trim().isEmpty()) {
            queryBuilder.append(" AND firstName LIKE :firstName");
            paramNames.add("firstName");
            paramValues.add("%" + firstName + "%");
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            queryBuilder.append(" AND lastName LIKE :lastName");
            paramNames.add("lastName");
            paramValues.add("%" + lastName + "%");
        }

        if (email != null && !email.trim().isEmpty()) {
            queryBuilder.append(" AND email LIKE :email");
            paramNames.add("email");
            paramValues.add("%" + email + "%");
        }

        TypedQuery<Student> query = entityManager.createQuery(queryBuilder.toString(), Student.class);

        for (int i = 0; i < paramNames.size(); i++) {
            query.setParameter(paramNames.get(i), paramValues.get(i));
        }

        return query.getResultList();
    }
}
