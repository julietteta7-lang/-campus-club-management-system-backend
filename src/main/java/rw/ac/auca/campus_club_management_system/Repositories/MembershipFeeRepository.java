package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.campus_club_management_system.Domain.MembershipFee;

import java.util.List;
import java.util.UUID;

public interface MembershipFeeRepository
        extends JpaRepository<MembershipFee, UUID> {

    @Query("SELECT f FROM MembershipFee f " +
           "WHERE f.membershipApplication.student.regNumber = :regNumber " +
           "ORDER BY f.paidDate DESC")
    List<MembershipFee> findFeesByStudentRegNumber(
            @Param("regNumber") String regNumber);

            boolean existsByMembershipApplication_Id(UUID membershipApplicationId);

    }