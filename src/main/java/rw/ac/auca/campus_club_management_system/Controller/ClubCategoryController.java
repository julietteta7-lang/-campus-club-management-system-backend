
package rw.ac.auca.campus_club_management_system.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.campus_club_management_system.Domain.ClubCategory;
import rw.ac.auca.campus_club_management_system.Service.ClubCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/club-categories")
public class ClubCategoryController {

    private final ClubCategoryService clubCategoryService;

    public ClubCategoryController(ClubCategoryService clubCategoryService) {
        this.clubCategoryService = clubCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveClubCategory(
            @RequestBody ClubCategory category) {

        clubCategoryService.saveClubCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Club category saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClubCategory>> getAllClubCategories() {
        return ResponseEntity.ok(
                clubCategoryService.getAllClubCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClubCategoryById(@PathVariable UUID id) {
        Optional<ClubCategory> category =
                clubCategoryService.getClubCategoryById(id);

        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The club category with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClubCategory(
            @PathVariable UUID id,
            @RequestBody ClubCategory updatedCategory) {

        String result = clubCategoryService.updateClubCategory(
                id, updatedCategory);

        if (result.equals("Club category updated successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClubCategory(@PathVariable UUID id) {
        String result = clubCategoryService.deleteClubCategory(id);

        if (result.equals("Club category deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    
@GetMapping("/unused")
public ResponseEntity<List<ClubCategory>> findUnusedCategories() {
    return ResponseEntity.ok(
            clubCategoryService.findUnusedCategories());
}

}
