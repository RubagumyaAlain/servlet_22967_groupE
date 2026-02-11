package auca.ac.rw.Assignment1.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import auca.ac.rw.Assignment1.Student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")


public class StudentController {
    private final List<Student> students = new ArrayList<>(List.of(
            new Student(22967L, "RUBAGUMYA", "Alain", "rubagumyaalain@gmail.com", "Software Engineering", 17.2),
            new Student(23232L, "Hirwa", "Arsene", "hirwaarsene@gmail.com", "Information&Technology", 13.2),
            new Student(23244L, "Muhire", "Cedrick", "muhirecedrick@gmail.com", "Networking", 14.0)
    ));
    private long nextId = 30000L;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> listStudents() {
        return ResponseEntity.ok(students);
   }
   @GetMapping("/students/{studentId}")
   public ResponseEntity<Student> getById(@PathVariable("studentId") Long studentId) {
        Student student = findById(studentId);
        if(student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
   }

   private Student findById (Long studentId){
        for(Student student : students){
                if(studentId != null && studentId.equals(student.getStudentId())){
                    return student;
                }
        }
         return null;
   }

   @PostMapping("/students")
   public ResponseEntity<Student> createStudent(@RequestBody Student newStudent){
        if (newStudent == null){
            return ResponseEntity.badRequest().build();
        }
        if (newStudent.getStudentId() == null ){
           newStudent.setStudentId(++nextId); // auto-assign an id if client omitted it
        }
        students.add(newStudent);
        return ResponseEntity.created(URI.create("/api/students/" + newStudent.getStudentId())).body(newStudent);
   }
}
