
package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.ac.auca.campus_club_management_system.Domain.MembershipApplication;
import rw.ac.auca.campus_club_management_system.Domain.ApplicationStatus;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface MembershipApplicationRepository
        extends JpaRepository<MembershipApplication, UUID> {

    List<MembershipApplication>
    findByStatusOrderByApplicationDateAsc(ApplicationStatus status);

    List<MembershipApplication>
    findByApplicationDateBetweenOrderByApplicationDateAsc(
        LocalDate start, LocalDate end);

        
boolean existsByClubIdAndApplicationDateAndStatusNot(
        UUID clubId,
        LocalDate applicationDate,
        ApplicationStatus status);

@Query("SELECT a.status, COUNT(a) FROM MembershipApplication a GROUP BY a.status")
List<Object[]> countApplicationsByStatus();

@Modifying
@Transactional
@Query("UPDATE MembershipApplication a " +
       "SET a.status = :declined " +
       "WHERE a.club.id = :clubId " +
       "AND a.applicationDate = :date " +
       "AND a.status <> :admitted")
int cancelApplicationsForClubOnDate(
        @Param("clubId") UUID clubId,
        @Param("date") LocalDate date,
        @Param("declined") ApplicationStatus declined,
        @Param("admitted") ApplicationStatus admitted);
        
}
