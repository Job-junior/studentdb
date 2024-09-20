package com.example.StudentDB.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    // select*from student where email =??
    Optional<Student> findStudentByEmail(String email);
    Optional<Student>findStudentByAge(int age);
    Optional<Student>findStudentByDob(LocalDate dob);
    Optional<Student>findStudentByName(String name);


}