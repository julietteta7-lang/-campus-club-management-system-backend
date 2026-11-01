package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.MeetingRoom;
import rw.ac.auca.campus_club_management_system.Service.MeetingRoomService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/meeting-rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMeetingRoom(
            @RequestBody MeetingRoom meetingRoom) {

        meetingRoomService.saveMeetingRoom(meetingRoom);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Meeting room saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MeetingRoom>> getAllMeetingRooms() {
        return ResponseEntity.ok(
                meetingRoomService.getAllMeetingRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeetingRoomById(@PathVariable UUID id) {
        Optional<MeetingRoom> room =
                meetingRoomService.getMeetingRoomById(id);

        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The meeting room with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMeetingRoom(
            @PathVariable UUID id,
            @RequestBody MeetingRoom updatedRoom) {

        String result =
                meetingRoomService.updateMeetingRoom(id, updatedRoom);

        if (result.equals("Meeting room updated successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMeetingRoom(@PathVariable UUID id) {
        String result = meetingRoomService.deleteMeetingRoom(id);

        if (result.equals("Meeting room deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    
@GetMapping("/free")
public ResponseEntity<List<MeetingRoom>> findFreeMeetingRooms() {
    return ResponseEntity.ok(
            meetingRoomService.findFreeMeetingRooms());
}

}