
package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;
import rw.ac.auca.campus_club_management_system.Domain.Club;
import rw.ac.auca.campus_club_management_system.Domain.ClubCategory;
import rw.ac.auca.campus_club_management_system.Domain.MeetingRoom;
import rw.ac.auca.campus_club_management_system.Repositories.ClubRepository;
import rw.ac.auca.campus_club_management_system.Repositories.MeetingRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import rw.ac.auca.campus_club_management_system.Repositories.ClubCategoryRepository;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubCategoryRepository clubCategoryRepository;
    private final MeetingRoomRepository meetingRoomRepository;

  public ClubService(
        ClubRepository clubRepository,
        ClubCategoryRepository clubCategoryRepository,
        MeetingRoomRepository meetingRoomRepository) {
    this.clubRepository = clubRepository;
    this.clubCategoryRepository = clubCategoryRepository;
    this.meetingRoomRepository = meetingRoomRepository;
}
    public Club saveClub(Club club) {
        return clubRepository.save(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Optional<Club> getClubById(UUID id) {
        return clubRepository.findById(id);
    }

    public String updateClub(UUID id, Club updatedClub) {
        Optional<Club> existingClub = clubRepository.findById(id);

        if (existingClub.isEmpty()) {
            return "The club with that id does not exist";
        }

        Club club = existingClub.get();

        club.setClubName(updatedClub.getClubName());
        club.setPatronName(updatedClub.getPatronName());
        club.setEmail(updatedClub.getEmail());
        club.setMeetingRoom(updatedClub.getMeetingRoom());
        club.setClubCategories(updatedClub.getClubCategories());

        clubRepository.save(club);

        return "Club updated successfully";
    }

    public String deleteClub(UUID id) {
        if (clubRepository.existsById(id)) {
            clubRepository.deleteById(id);
            return "Club deleted successfully";
        }

        return "The club with that id does not exist";
    }

    public List<Club> findClubsByCategoryName(String name) {
    return clubRepository.findClubsByCategoryName(name);
}


public boolean clubExists(UUID id) {
    return clubRepository.existsById(id);
}

public String addCategoryToClub(UUID clubId, UUID categoryId) {
    Optional<Club> clubOptional = clubRepository.findById(clubId);

    if (clubOptional.isEmpty()) {
        return "The club with that id does not exist";
    }

    Optional<ClubCategory> categoryOptional =
            clubCategoryRepository.findById(categoryId);

   if (categoryOptional.isEmpty()) {
    return "That club category does not exist";
}

    Club club = clubOptional.get();
    ClubCategory category = categoryOptional.get();

    boolean alreadyAdded = club.getClubCategories().stream()
            .anyMatch(c -> c.getId().equals(categoryId));

    if (alreadyAdded) {
        return "The club already has that club category";
    }

    club.getClubCategories().add(category);
    clubRepository.save(club);

    return "Club category added to the club";
}

public String assignMeetingRoom(UUID clubId, int roomNumber) {
    Optional<Club> clubOptional = clubRepository.findById(clubId);

    if (clubOptional.isEmpty()) {
        return "The club with that id does not exist";
    }

    Optional<MeetingRoom> roomOptional =
            meetingRoomRepository.findByRoomNumber(roomNumber);

    if (roomOptional.isEmpty()) {
        return "The meeting room with that roomNumber does not exist";
    }

    Club club = clubOptional.get();
    MeetingRoom room = roomOptional.get();

    boolean roomTaken = clubRepository
            .existsByMeetingRoom_IdAndIdNot(room.getId(), clubId);

    if (roomTaken) {
        return "That meeting room is taken by another club";
    }

    club.setMeetingRoom(room);
    clubRepository.save(club);

    return "Meeting room changed successfully";
}

}
