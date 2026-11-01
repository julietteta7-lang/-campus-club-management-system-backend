
package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.Club;
import rw.ac.auca.campus_club_management_system.Service.ClubService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveClub(@RequestBody Club club) {
        clubService.saveClub(club);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Club saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Club>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClubById(@PathVariable UUID id) {
        Optional<Club> club = clubService.getClubById(id);

        if (club.isPresent()) {
            return ResponseEntity.ok(club.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The club with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClub(
            @PathVariable UUID id,
            @RequestBody Club updatedClub) {

        String result = clubService.updateClub(id, updatedClub);

        if (result.equals("Club updated successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable UUID id) {
        String result = clubService.deleteClub(id);

        if (result.equals("Club deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/by-club-category")
    public ResponseEntity<List<Club>> findClubsByCategoryName(
        @RequestParam String name) {

            return ResponseEntity.ok(
                clubService.findClubsByCategoryName(name));
        }

       @PutMapping("/{clubId}/club-categories/{clubCategoryId}")
public ResponseEntity<String> addCategoryToClub(
        @PathVariable UUID clubId,
        @PathVariable UUID clubCategoryId) {

    String result = clubService.addCategoryToClub(clubId, clubCategoryId);

    if (result.equals("The club with that id does not exist")
            || result.equals("That club category does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    if (result.equals("The club already has that club category")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    return ResponseEntity.ok(result);
}

@PatchMapping("/{clubId}/meeting-room")
public ResponseEntity<String> assignMeetingRoom(
        @PathVariable UUID clubId,
        @RequestParam int roomNumber) {

    String result = clubService.assignMeetingRoom(clubId, roomNumber);

    if (result.equals("The club with that id does not exist")
            || result.equals("The meeting room with that roomNumber does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    if (result.equals("That meeting room is taken by another club")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    return ResponseEntity.ok(result);
}

}
