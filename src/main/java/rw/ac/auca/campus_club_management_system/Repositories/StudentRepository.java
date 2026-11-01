package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.campus_club_management_system.Domain.Student;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findByFacultyIgnoreCaseOrderByLastNameAsc(String faculty);

    
@Query("SELECT DISTINCT a.student FROM MembershipApplication a WHERE a.club.id = :clubId")
List<Student> findStudentsByClubId(@Param("clubId") UUID clubId);


}