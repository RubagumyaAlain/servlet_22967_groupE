package auca.ac.rw.Assignment1.controller;

import auca.ac.rw.Assignment1.MenuItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final List<MenuItem> menuItems = new ArrayList<>(List.of(
            new MenuItem(1L, "Margherita Pizza", "Classic cheese pizza with fresh basil", 12.50, "Main", true),
            new MenuItem(2L, "Caesar Salad", "Romaine lettuce with parmesan and croutons", 8.50, "Starter", true),
            new MenuItem(3L, "Chocolate Cake", "Rich dark chocolate slice", 6.00, "Dessert", false)
    ));
    private long nextId = 4L;
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem item = findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getByCategory(@PathVariable String category) {
        List<MenuItem> matches = new ArrayList<>();
        if (category == null || category.isBlank()) {
            return matches;
        }
        String normalized = category.toLowerCase();
        for (MenuItem item : menuItems) {
            String itemCategory = item.getCategory();
            if (itemCategory != null && itemCategory.toLowerCase().equals(normalized)) {
                matches.add(item);
            }
        }
        return matches;
    }

    @GetMapping("/available")
    public List<MenuItem> getAvailable(@RequestParam(name = "available", defaultValue = "true") boolean available) {
        List<MenuItem> matches = new ArrayList<>();
        for (MenuItem item : menuItems) {
            boolean isAvailable = Boolean.TRUE.equals(item.getAvailable());
            if (available == isAvailable) {
                matches.add(item);
            }
        }
        return matches;
    }

    @GetMapping("/search")
    public List<MenuItem> searchByName(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            return menuItems;
        }
        String term = name.toLowerCase();
        List<MenuItem> matches = new ArrayList<>();
        for (MenuItem item : menuItems) {
            String itemName = item.getName();
            if (itemName != null && itemName.toLowerCase().contains(term)) {
                matches.add(item);
            }
        }
        return matches;
    }

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem newItem) {
        if (newItem == null) {
            return ResponseEntity.badRequest().build();
        }
        if (newItem.getId() == null) {
            newItem.setId(nextId++);
        } else if (findById(newItem.getId()) != null) {
            return ResponseEntity.status(409).build();
        }
        if (newItem.getAvailable() == null) {
            newItem.setAvailable(true);
        }
        menuItems.add(newItem);
        return ResponseEntity.created(URI.create("/api/menu/" + newItem.getId())).body(newItem);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        MenuItem item = findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        boolean newValue = !Boolean.TRUE.equals(item.getAvailable());
        item.setAvailable(newValue);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMenuItem(@PathVariable Long id) {
        MenuItem existing = findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        menuItems.remove(existing);
        return ResponseEntity.noContent().build();
    }

    private MenuItem findById(Long id) {
        if (id == null) {
            return null;
        }
        for (MenuItem item : menuItems) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }
}
