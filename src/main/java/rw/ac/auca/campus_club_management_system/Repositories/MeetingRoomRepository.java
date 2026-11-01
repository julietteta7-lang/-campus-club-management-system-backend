package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.ac.auca.campus_club_management_system.Domain.MeetingRoom;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface MeetingRoomRepository
        extends JpaRepository<MeetingRoom, UUID> {

                
@Query("SELECT m FROM MeetingRoom m WHERE m.id NOT IN (SELECT c.meetingRoom.id FROM Club c WHERE c.meetingRoom IS NOT NULL) ORDER BY m.roomNumber ASC")
List<MeetingRoom> findFreeMeetingRooms();

Optional<MeetingRoom> findByRoomNumber(int roomNumber);

}