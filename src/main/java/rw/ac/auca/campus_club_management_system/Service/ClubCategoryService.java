
package rw.ac.auca.campus_club_management_system.Service;

import org.springframework.stereotype.Service;
import rw.ac.auca.campus_club_management_system.Domain.ClubCategory;
import rw.ac.auca.campus_club_management_system.Repositories.ClubCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClubCategoryService {

    private final ClubCategoryRepository clubCategoryRepository;

    public ClubCategoryService(ClubCategoryRepository clubCategoryRepository) {
        this.clubCategoryRepository = clubCategoryRepository;
    }

    public ClubCategory saveClubCategory(ClubCategory category) {
        return clubCategoryRepository.save(category);
    }

    public List<ClubCategory> getAllClubCategories() {
        return clubCategoryRepository.findAll();
    }

    public Optional<ClubCategory> getClubCategoryById(UUID id) {
        return clubCategoryRepository.findById(id);
    }

    public String updateClubCategory(UUID id, ClubCategory updatedCategory) {
        Optional<ClubCategory> existingCategory =
                clubCategoryRepository.findById(id);

        if (existingCategory.isEmpty()) {
            return "The club category with that id does not exist";
        }

        ClubCategory category = existingCategory.get();
        category.setName(updatedCategory.getName());

        clubCategoryRepository.save(category);

        return "Club category updated successfully";
    }

    public String deleteClubCategory(UUID id) {
        if (clubCategoryRepository.existsById(id)) {
            clubCategoryRepository.deleteById(id);
            return "Club category deleted successfully";
        }

        return "The club category with that id does not exist";
    }

    
public List<ClubCategory> findUnusedCategories() {
    return clubCategoryRepository.findUnusedCategories();
}

}
