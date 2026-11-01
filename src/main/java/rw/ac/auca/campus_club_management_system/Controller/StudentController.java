package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.Student;
import rw.ac.auca.campus_club_management_system.Service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import rw.ac.auca.campus_club_management_system.Service.ClubService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final ClubService clubService;

    public StudentController(
        StudentService studentService,
        ClubService clubService) {
            
        this.studentService = studentService;
        this.clubService = clubService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
    studentService.saveStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body("Student saved successfully");
}

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The student with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(
            @PathVariable UUID id,
            @RequestBody Student updatedStudent) {

        String result = studentService.updateStudent(id, updatedStudent);

        if (result.equals("Student updated successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        String result = studentService.deleteStudent(id);

        if (result.equals("Student deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/by-faculty")
    public ResponseEntity<List<Student>> findStudentsByFaculty(
        @RequestParam String faculty) {

            return ResponseEntity
                .ok(studentService.findStudentsByFaculty(faculty));
            
        }

        
@GetMapping("/of-club/{clubId}")
public ResponseEntity<?> findStudentsByClubId(
        @PathVariable UUID clubId) {

    if (!clubService.clubExists(clubId)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The club with that id does not exist");
    }

    return ResponseEntity.ok(
            studentService.findStudentsByClubId(clubId));
}

}