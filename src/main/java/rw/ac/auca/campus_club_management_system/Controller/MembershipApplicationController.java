
package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.MembershipApplication;
import rw.ac.auca.campus_club_management_system.Service.MembershipApplicationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import rw.ac.auca.campus_club_management_system.Domain.ApplicationStatus;

@RestController
@RequestMapping("/api/membership-applications")
public class MembershipApplicationController {

    private final MembershipApplicationService applicationService;

    public MembershipApplicationController(
            MembershipApplicationService applicationService) {
        this.applicationService = applicationService;
    }

   
@PostMapping("/save")
public ResponseEntity<String> saveApplication(
        @RequestBody MembershipApplication application) {

    String result = applicationService.saveApplication(application);

    if (result.equals("Membership application saved successfully")) {
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    if (result.equals("Membership application date cannot be in the past")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
}


    @GetMapping("/all")
    public ResponseEntity<List<MembershipApplication>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable UUID id) {
        Optional<MembershipApplication> application =
                applicationService.getApplicationById(id);

        if (application.isPresent()) {
            return ResponseEntity.ok(application.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The membership application with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateApplication(
            @PathVariable UUID id,
            @RequestBody MembershipApplication updatedApplication) {

        String result = applicationService.updateApplication(
                id, updatedApplication);

        if (result.equals("Membership application updated successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteApplication(@PathVariable UUID id) {

    String result = applicationService.deleteApplication(id);

    if (result.equals("Membership application deleted successfully")) {
        return ResponseEntity.ok(result);
    }

    if (result.equals(
            "That membership application has membership fees, delete them first")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
}

    @GetMapping("/by-status")
    public ResponseEntity<List<MembershipApplication>> findApplicationsByStatus(
        @RequestParam ApplicationStatus status) {

            return ResponseEntity.ok(
                applicationService.findApplicationsByStatus(status));
        }

        
@GetMapping("/between")
public ResponseEntity<List<MembershipApplication>> findApplicationsBetweenDates(
        @RequestParam String start,
        @RequestParam String end) {

    LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);

    return ResponseEntity.ok(
            applicationService.findApplicationsBetweenDates(
                    startDate, endDate));
}


@PatchMapping("/{id}/status")
public ResponseEntity<String> updateApplicationStatus(
        @PathVariable UUID id,
        @RequestParam ApplicationStatus status) {

    String result =
            applicationService.updateApplicationStatus(id, status);

    if (result.equals(
            "The membership application with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    if (result.equals(
            "Membership application status updated successfully")) {
        return ResponseEntity.ok(result);
    }

    return ResponseEntity.badRequest().body(result);
}

@GetMapping("/stats/by-status")
public ResponseEntity<List<Object[]>> countApplicationsByStatus() {
    return ResponseEntity.ok(
            applicationService.countApplicationsByStatus());
}

@PatchMapping("/cancel-day")
public ResponseEntity<String> cancelApplicationsForClubOnDate(
        @RequestParam UUID clubId,
        @RequestParam String date) {

    LocalDate applicationDate = LocalDate.parse(date);

    int updatedCount =
            applicationService.cancelApplicationsForClubOnDate(
                    clubId, applicationDate);

    return ResponseEntity.ok(
            updatedCount + " membership applications cancelled");
}
}
