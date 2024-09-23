package com.example.StudentDB;


import com.example.StudentDB.student.ObjectMapperConfig;
import com.example.StudentDB.student.Student;
import com.example.StudentDB.student.StudentRepository;
import com.example.StudentDB.student.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class StudentDbApplicationTests {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName(value = "verifyTheGetStudentTest")
    void verifyIfTheGetRequestFunctionWell() throws Exception {
        List<Student> studentList = studentRepository.findAll();
        when(studentService.getStudents()).thenReturn(studentList);

        this.mockMvc.perform(get("/api/v1/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    @DisplayName(value = "verifyTheCreateStudentTestMethod")
    void createAStudent() throws Exception {
        ObjectMapper objectMapper = ObjectMapperConfig.getObjectMapper();
        Student student = new Student("Loic", "NOO@gmail.com", LocalDate.of(2000, Month.APRIL, 12), 24);
        when(studentService.createStudent(student)).thenReturn(student);
        String studentJson = objectMapper.writeValueAsString(student);
        this.mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Loic"))
                .andExpect(jsonPath("$.email").value("NOO@gmail.com"))
                .andExpect(jsonPath("$.age").value(24));
    }

    @Test
    @DisplayName(value = "verifyTheDeleteStudentTest")
    void testDeleteStudent() throws Exception {
        Long studentId = 205L;

        StudentService studentService = mock(StudentService.class);

        doNothing().when(studentService).deleteStudent(studentId);

        this.mockMvc.perform(delete("/api/v1/student/" + studentId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "verifyTheUpdateStudentTest")
    public void testUpdateStudent() throws Exception {
        // Create mock student
        Long studentId = 12L;
        String updatedName = "Mariam";
        String updatedEmail = "mariam@gmail.com";

        // Ensure the student exists in the repository before updating
        Student existingStudent = new Student(22L, "Jessica", "jessica@gmail.com", LocalDate.of(2000, 4, 5), 24);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findStudentByEmail(updatedEmail)).thenReturn(Optional.empty());

        // Perform update operation via MockMvc
        mockMvc.perform(put("/api/v1/student/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"" + updatedName + "\", \"email\": \"" + updatedEmail + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.email").value(updatedEmail));

    }

}




