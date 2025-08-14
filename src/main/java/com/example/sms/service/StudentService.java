package com.example.sms.service;

import com.example.sms.dao.StudentDAO;
import com.example.sms.model.Student;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class StudentService {

    private StudentDAO studentDAO;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void addStudent(Student s) {
        validateStudent(s);
        studentDAO.save(s);
    }

    public CompletableFuture<Void> addStudentAsync(Student s) {
        validateStudent(s);
        return CompletableFuture.runAsync(() -> studentDAO.save(s), executor);
    }

    public java.util.List<Student> getAll() {
        return studentDAO.findAll();
    }

    public java.util.List<Student> getSortedByName() {
        return getAll().stream()
                .sorted(java.util.Comparator.comparing(Student::getName))
                .collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<Student> filterByMinAge(int minAge) {
        return getAll().stream()
                .filter(s -> s.getAge() >= minAge)
                .collect(java.util.stream.Collectors.toList());
    }

    public boolean delete(int id) {
        return studentDAO.deleteById(id);
    }

    private void validateStudent(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (s.getId() <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        if (s.getName() == null || s.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (s.getAge() < 5 || s.getAge() > 120) {
            throw new IllegalArgumentException("Age must be between 5 and 120");
        }
    }
}
