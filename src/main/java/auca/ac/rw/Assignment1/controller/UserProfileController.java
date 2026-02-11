package auca.ac.rw.Assignment1.controller;

import auca.ac.rw.Assignment1.ApiResponse;
import auca.ac.rw.Assignment1.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final List<UserProfile> users = new ArrayList<>(List.of(
            new UserProfile(1L, "john_doe", "john@example.com", "John Doe", 28, "USA", "Love coding", true),
            new UserProfile(2L, "jane_smith", "jane@example.com", "Jane Smith", 31, "Canada", "Traveler and photographer", false),
            new UserProfile(3L, "alex_k", "alex@example.com", "Alex Kim", 24, "South Korea", "Backend dev", true),
            new UserProfile(4L, "maria_g", "maria@example.com", "Maria Garcia", 35, "Spain", "Product manager", true),
            new UserProfile(5L, "li_wei", "liwei@example.com", "Li Wei", 27, "China", "UI designer", false)
    ));
    private long nextId = 6L;

    @GetMapping
    public ApiResponse<List<UserProfile>> getAll() {
        return new ApiResponse<>(true, "All user profiles", users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getById(@PathVariable Long userId) {
        UserProfile found = findById(userId);
        if (found == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User found", found));
    }

    @GetMapping("/search/username/{username}")
    public ApiResponse<List<UserProfile>> searchByUsername(@PathVariable String username) {
        List<UserProfile> matches = new ArrayList<>();
        if (username != null) {
            String term = username.toLowerCase();
            for (UserProfile user : users) {
                String name = user.getUsername();
                if (name != null && name.toLowerCase().contains(term)) {
                    matches.add(user);
                }
            }
        }
        return new ApiResponse<>(true, "Search by username", matches);
    }

    @GetMapping("/search/country/{country}")
    public ApiResponse<List<UserProfile>> searchByCountry(@PathVariable String country) {
        List<UserProfile> matches = new ArrayList<>();
        if (country != null) {
            String term = country.toLowerCase();
            for (UserProfile user : users) {
                String c = user.getCountry();
                if (c != null && c.toLowerCase().equals(term)) {
                    matches.add(user);
                }
            }
        }
        return new ApiResponse<>(true, "Search by country", matches);
    }

    @GetMapping("/search/age-range")
    public ApiResponse<List<UserProfile>> searchByAgeRange(@RequestParam(name = "min", required = false) Integer min,
                                                           @RequestParam(name = "max", required = false) Integer max) {
        int lower = min == null ? Integer.MIN_VALUE : min;
        int upper = max == null ? Integer.MAX_VALUE : max;
        List<UserProfile> matches = new ArrayList<>();
        for (UserProfile user : users) {
            int age = user.getAge();
            if (age >= lower && age <= upper) {
                matches.add(user);
            }
        }
        return new ApiResponse<>(true, "Search by age range", matches);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> create(@RequestBody UserProfile newUser) {
        if (newUser == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid user data", null));
        }
        if (newUser.getUserId() == null) {
            newUser.setUserId(nextId++);
        } else if (findById(newUser.getUserId()) != null) {
            return ResponseEntity.status(409).body(new ApiResponse<>(false, "User ID already exists", null));
        }
        users.add(newUser);
        return ResponseEntity.created(URI.create("/api/users/" + newUser.getUserId()))
                .body(new ApiResponse<>(true, "User profile created successfully", newUser));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> update(@PathVariable Long userId, @RequestBody UserProfile updated) {
        UserProfile existing = findById(userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
        if (updated == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid user data", null));
        }
        existing.setUserId(userId);
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        existing.setFullName(updated.getFullName());
        existing.setAge(updated.getAge());
        existing.setCountry(updated.getCountry());
        existing.setBio(updated.getBio());
        existing.setActive(updated.getActive());
        return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated", existing));
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> activate(@PathVariable Long userId) {
        UserProfile existing = findById(userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
        existing.setActive(true);
        return ResponseEntity.ok(new ApiResponse<>(true, "User activated", existing));
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserProfile>> deactivate(@PathVariable Long userId) {
        UserProfile existing = findById(userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
        existing.setActive(false);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deactivated", existing));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long userId) {
        UserProfile existing = findById(userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
        users.remove(existing);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted", null));
    }

    private UserProfile findById(Long id) {
        if (id == null) {
            return null;
        }
        for (UserProfile user : users) {
            if (id.equals(user.getUserId())) {
                return user;
            }
        }
        return null;
    }
}
