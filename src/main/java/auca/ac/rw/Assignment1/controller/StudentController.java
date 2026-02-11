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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")


public class StudentController {
    private final List<Student> students = new ArrayList<>(List.of(
            new Student(1001L, "Alice", "Jones", "alice@gmail.com", "Computer Science", 3.9),
            new Student(1002L, "Bob", "Smith", "bob@gmail.com", "Information Technology", 3.4),
            new Student(1003L, "Carol", "Lee", "carol@gmail.com", "Computer Science", 3.6),
            new Student(1004L, "David", "Kim", "david@gmail.com", "Networking", 3.2),
            new Student(1005L, "Eva", "Garcia", "eva@gmail.com", "Software Engineering", 3.8)
    ));
    private long nextId = 2000L;

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

   @GetMapping("/students/major/{major}")
   public List<Student> getByMajor(@PathVariable String major){
        List<Student> matches = new ArrayList<>();
        if (major == null || major.isBlank()){
            return matches;
        }
        String desired = major.toLowerCase();
        for(Student student : students){
            String m = student.getMajor();
            if (m != null && m.toLowerCase().equals(desired)){
                matches.add(student);
            }
        }
        return matches;
   }

   @GetMapping("/students/filter")
   public List<Student> filterByGpa(@RequestParam(name = "gpa", required = false) Double minGpa){
        double threshold = minGpa == null ? 0.0 : minGpa;
        List<Student> matches = new ArrayList<>();
        for(Student student : students){
            Double gpa = student.getGpa();
            if (gpa != null && gpa >= threshold){
                matches.add(student);
            }
        }
        return matches;
   }

   @PutMapping("/students/{studentId}")
   public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updated){
        Student existing = findById(studentId);
        if (existing == null){
            return ResponseEntity.notFound().build();
        }
        if (updated == null){
            return ResponseEntity.badRequest().build();
        }
        existing.setStudentId(studentId);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setMajor(updated.getMajor());
        existing.setGpa(updated.getGpa());
        return ResponseEntity.ok(existing);
   }
}
