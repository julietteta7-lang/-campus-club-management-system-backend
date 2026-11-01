package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;
import rw.ac.auca.campus_club_management_system.Domain.MeetingRoom;
import rw.ac.auca.campus_club_management_system.Repositories.MeetingRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public MeetingRoom saveMeetingRoom(MeetingRoom meetingRoom) {
        return meetingRoomRepository.save(meetingRoom);
    }

    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomRepository.findAll();
    }

    public Optional<MeetingRoom> getMeetingRoomById(UUID id) {
        return meetingRoomRepository.findById(id);
    }

    public String updateMeetingRoom(UUID id, MeetingRoom updatedRoom) {
        Optional<MeetingRoom> existingRoom = meetingRoomRepository.findById(id);

        if (existingRoom.isEmpty()) {
            return "The meeting room with that id does not exist";
        }

        MeetingRoom room = existingRoom.get();
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setName(updatedRoom.getName());

        meetingRoomRepository.save(room);
        return "Meeting room updated successfully";
    }

    public String deleteMeetingRoom(UUID id) {
        if (meetingRoomRepository.existsById(id)) {
            meetingRoomRepository.deleteById(id);
            return "Meeting room deleted successfully";
        }

        return "The meeting room with that id does not exist";
    }

    
public List<MeetingRoom> findFreeMeetingRooms() {
    return meetingRoomRepository.findFreeMeetingRooms();
}

}