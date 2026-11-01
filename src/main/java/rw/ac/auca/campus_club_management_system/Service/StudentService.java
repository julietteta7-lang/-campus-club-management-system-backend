package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;
import rw.ac.auca.campus_club_management_system.Domain.Student;
import rw.ac.auca.campus_club_management_system.Repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public String updateStudent(UUID id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isEmpty()) {
            return "The student with that id does not exist";
        }

        Student student = existingStudent.get();
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setRegNumber(updatedStudent.getRegNumber());
        student.setEmail(updatedStudent.getEmail());
        student.setFaculty(updatedStudent.getFaculty());

        studentRepository.save(student);
        return "Student updated successfully";
    }

    public String deleteStudent(UUID id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return "Student deleted successfully";
        }

        return "The student with that id does not exist";
    }

    public List<Student> findStudentsByFaculty(String faculty) {
        return studentRepository
               .findByFacultyIgnoreCaseOrderByLastNameAsc(faculty);
    }

    
public List<Student> findStudentsByClubId(UUID clubId) {
    return studentRepository.findStudentsByClubId(clubId);
}

}