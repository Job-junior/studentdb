package com.example.StudentDB.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {

        return studentService.getStudents();
    }

    @PostMapping
    public Student registerNewStudent(@RequestBody Student student1) {
        studentService.createStudent(student1);
        return student1;
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {

        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public Student updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name
    ) {
        return studentService.updateStudent(studentId, name, email);
    }


}
