
package rw.ac.auca.campus_club_management_system.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.ac.auca.campus_club_management_system.Domain.ClubCategory;

import java.util.UUID;
import java.util.List;


public interface ClubCategoryRepository extends JpaRepository<ClubCategory, UUID> {

    
@Query("SELECT cat FROM ClubCategory cat WHERE cat.clubs IS EMPTY")
List<ClubCategory> findUnusedCategories();

}
