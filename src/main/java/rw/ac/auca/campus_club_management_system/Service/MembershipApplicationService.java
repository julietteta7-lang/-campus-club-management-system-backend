
package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;

import rw.ac.auca.campus_club_management_system.Domain.MembershipApplication;
import rw.ac.auca.campus_club_management_system.Domain.ApplicationStatus;

import rw.ac.auca.campus_club_management_system.Repositories.MembershipApplicationRepository;
import rw.ac.auca.campus_club_management_system.Repositories.MembershipFeeRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MembershipApplicationService {

    private final MembershipApplicationRepository applicationRepository;
    private final MembershipFeeRepository membershipFeeRepository;

    public MembershipApplicationService(
            MembershipApplicationRepository applicationRepository,
            MembershipFeeRepository membershipFeeRepository) {

        this.applicationRepository = applicationRepository;
        this.membershipFeeRepository = membershipFeeRepository;
    }

    public String saveApplication(MembershipApplication application) {

        if (application.getApplicationDate().isBefore(LocalDate.now())) {
            return "Membership application date cannot be in the past";
        }

        boolean duplicate =
                applicationRepository.existsByClubIdAndApplicationDateAndStatusNot(
                        application.getClub().getId(),
                        application.getApplicationDate(),
                        ApplicationStatus.DECLINED);

        if (duplicate) {
            return "Club already has an application to review on that date";
        }

        application.setStatus(ApplicationStatus.APPLIED);
        applicationRepository.save(application);

        return "Membership application saved successfully";
    }

    public List<MembershipApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<MembershipApplication> getApplicationById(UUID id) {
        return applicationRepository.findById(id);
    }

    public String updateApplication(
            UUID id, MembershipApplication updatedApplication) {

        Optional<MembershipApplication> existingApplication =
                applicationRepository.findById(id);

        if (existingApplication.isEmpty()) {
            return "The membership application with that id does not exist";
        }

        MembershipApplication application = existingApplication.get();

        application.setApplicationDate(updatedApplication.getApplicationDate());
        application.setMotivation(updatedApplication.getMotivation());
        application.setStudent(updatedApplication.getStudent());
        application.setClub(updatedApplication.getClub());

        // Do not change the application's status during a normal update.
        applicationRepository.save(application);

        return "Membership application updated successfully";
    }

    public String deleteApplication(UUID id) {

        Optional<MembershipApplication> applicationOptional =
                applicationRepository.findById(id);

        if (applicationOptional.isEmpty()) {
            return "The membership application with that id does not exist";
        }

        boolean hasFees =
                membershipFeeRepository.existsByMembershipApplication_Id(id);

        if (hasFees) {
            return "That membership application has membership fees, delete them first";
        }

        applicationRepository.deleteById(id);

        return "Membership application deleted successfully";
    }

    public List<MembershipApplication> findApplicationsByStatus(
            ApplicationStatus status) {

        return applicationRepository
                .findByStatusOrderByApplicationDateAsc(status);
    }

    public List<MembershipApplication> findApplicationsBetweenDates(
            LocalDate start, LocalDate end) {

        return applicationRepository
                .findByApplicationDateBetweenOrderByApplicationDateAsc(
                        start, end);
    }

    public String updateApplicationStatus(
            UUID id, ApplicationStatus newStatus) {

        Optional<MembershipApplication> existingApplication =
                applicationRepository.findById(id);

        if (existingApplication.isEmpty()) {
            return "The membership application with that id does not exist";
        }

        MembershipApplication application = existingApplication.get();

        ApplicationStatus currentStatus = application.getStatus();

        boolean validTransition =
                (currentStatus == ApplicationStatus.APPLIED
                        && (newStatus == ApplicationStatus.SHORTLISTED
                        || newStatus == ApplicationStatus.DECLINED))
                ||
                (currentStatus == ApplicationStatus.SHORTLISTED
                        && (newStatus == ApplicationStatus.ADMITTED
                        || newStatus == ApplicationStatus.DECLINED));

        if (!validTransition) {
            return "Cannot change status from " + currentStatus
                    + " to " + newStatus;
        }

        application.setStatus(newStatus);
        applicationRepository.save(application);

        return "Membership application status updated successfully";
    }

    public List<Object[]> countApplicationsByStatus() {
    return applicationRepository.countApplicationsByStatus();
}

@Transactional
public int cancelApplicationsForClubOnDate(
        UUID clubId, LocalDate date) {

    return applicationRepository.cancelApplicationsForClubOnDate(
            clubId,
            date,
            ApplicationStatus.DECLINED,
            ApplicationStatus.ADMITTED);
}
}
