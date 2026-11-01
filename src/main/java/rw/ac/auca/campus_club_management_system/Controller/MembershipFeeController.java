package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.MembershipFee;
import rw.ac.auca.campus_club_management_system.Service.MembershipFeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/membership-fees")
public class MembershipFeeController {

    private final MembershipFeeService membershipFeeService;

    public MembershipFeeController(MembershipFeeService membershipFeeService) {
        this.membershipFeeService = membershipFeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMembershipFee(
            @RequestBody MembershipFee fee,
            @RequestParam UUID membershipApplicationId) {

        String result = membershipFeeService.saveMembershipFee(
                fee, membershipApplicationId);

        if (result.equals("The membership application with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        if (result.equals("Membership fees are only for ADMITTED membership applications")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/student/{regNumber}")
public ResponseEntity<List<MembershipFee>> findFeesByStudent(
        @PathVariable String regNumber) {

    return ResponseEntity.ok(
            membershipFeeService.findFeesByStudentRegNumber(regNumber));
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteMembershipFee(@PathVariable UUID id) {

    String result = membershipFeeService.deleteMembershipFee(id);

    if (result.equals("The membership fee with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    return ResponseEntity.ok(result);
}
}