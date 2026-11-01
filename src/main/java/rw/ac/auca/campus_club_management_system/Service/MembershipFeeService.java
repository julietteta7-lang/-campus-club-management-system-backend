package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;
import rw.ac.auca.campus_club_management_system.Domain.MembershipFee;
import rw.ac.auca.campus_club_management_system.Repositories.MembershipFeeRepository;
import java.util.List;
import java.util.UUID;
import rw.ac.auca.campus_club_management_system.Domain.MembershipApplication;
import rw.ac.auca.campus_club_management_system.Domain.ApplicationStatus;
import java.time.LocalDate;
import java.util.Optional;
import rw.ac.auca.campus_club_management_system.Repositories.MembershipApplicationRepository;

@Service
public class MembershipFeeService {

    private final MembershipFeeRepository membershipFeeRepository;
    private final MembershipApplicationRepository applicationRepository;

   public MembershipFeeService(
        MembershipFeeRepository membershipFeeRepository,
        MembershipApplicationRepository applicationRepository) {
    this.membershipFeeRepository = membershipFeeRepository;
    this.applicationRepository = applicationRepository;
}

   public String saveMembershipFee(MembershipFee fee, UUID membershipApplicationId) {

    Optional<MembershipApplication> applicationOptional =
            applicationRepository.findById(membershipApplicationId);

    if (applicationOptional.isEmpty()) {
        return "The membership application with that id does not exist";
    }

    MembershipApplication application = applicationOptional.get();

    if (application.getStatus() != ApplicationStatus.ADMITTED) {
        return "Membership fees are only for ADMITTED membership applications";
    }

    fee.setPaidDate(LocalDate.now());
    fee.setMembershipApplication(application);

    membershipFeeRepository.save(fee);

    return "Membership fee saved successfully";
}

    public List<MembershipFee> findFeesByStudentRegNumber(String regNumber) {
        return membershipFeeRepository.findFeesByStudentRegNumber(regNumber);
    }

    public List<MembershipFee> findAllMembershipFees() {
        return membershipFeeRepository.findAll();
    }

    public java.util.Optional<MembershipFee> findMembershipFeeById(UUID id) {
        return membershipFeeRepository.findById(id);
    }

   public String deleteMembershipFee(UUID id) {

    Optional<MembershipFee> feeOptional =
            membershipFeeRepository.findById(id);

    if (feeOptional.isEmpty()) {
        return "The membership fee with that id does not exist";
    }

    membershipFeeRepository.deleteById(id);

    return "Membership fee deleted successfully";
}
}