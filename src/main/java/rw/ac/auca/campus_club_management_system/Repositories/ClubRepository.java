
package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.campus_club_management_system.Domain.Club;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, UUID> {

    @Query("SELECT c FROM Club c JOIN c.clubCategories cat WHERE LOWER(cat.name) = LOWER(:name)")
    List<Club> findClubsByCategoryName(@Param("name") String name);

    boolean existsByMeetingRoom_IdAndIdNot(UUID roomId, UUID clubId);
}
