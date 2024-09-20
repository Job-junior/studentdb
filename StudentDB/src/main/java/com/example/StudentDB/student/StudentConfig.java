package com.example.StudentDB.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository
    ){
        return args -> {

            Student mariam = new Student(12L,

                    "Mariam",
                    "mariam@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 5),
                    20
            );
            Student job = new Student(13L,

                    "Job",
                    "job@gmail.com",
                    LocalDate.of(2002, Month.MARCH, 21),
                    22
            );
           Student mike = new Student(14L,

                    "mike",
                    "mike@gmail.com",
                    LocalDate.of(2000, Month.AUGUST, 7),
                    21
            );
           repository.saveAll(
                   List.of(mariam, job, mike)
           );
        };
    }
}
