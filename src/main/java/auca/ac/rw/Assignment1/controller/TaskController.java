package auca.ac.rw.Assignment1.controller;

import auca.ac.rw.Assignment1.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>(List.of(
            new Task(1L, "Buy groceries", "Milk, eggs, bread", false, "MEDIUM", "2026-02-15"),
            new Task(2L, "Finish assignment", "Complete Spring Boot API", false, "HIGH", "2026-02-12"),
            new Task(3L, "Gym session", "Leg day workout", true, "LOW", "2026-02-10"),
            new Task(4L, "Team meeting", "Project sync with team", true, "MEDIUM", "2026-02-13"),
            new Task(5L, "Pay bills", "Electricity and internet", false, "HIGH", "2026-02-14")
    ));
    private long nextId = 6L;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = findById(taskId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/status")
    public List<Task> getByStatus(@RequestParam(name = "completed", defaultValue = "false") boolean completed) {
        List<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getCompleted() == completed) {
                matches.add(task);
            }
        }
        return matches;
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getByPriority(@PathVariable String priority) {
        List<Task> matches = new ArrayList<>();
        if (priority == null || priority.isBlank()) {
            return matches;
        }
        String desired = priority.toUpperCase();
        for (Task task : tasks) {
            String p = task.getPriority();
            if (p != null && p.toUpperCase().equals(desired)) {
                matches.add(task);
            }
        }
        return matches;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        if (newTask == null) {
            return ResponseEntity.badRequest().build();
        }
        if (newTask.getTaskId() == null) {
            newTask.setTaskId(nextId++);
        } else if (findById(newTask.getTaskId()) != null) {
            return ResponseEntity.status(409).build();
        }
        tasks.add(newTask);
        return ResponseEntity.created(URI.create("/api/tasks/" + newTask.getTaskId())).body(newTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task existing = findById(taskId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (updatedTask == null) {
            return ResponseEntity.badRequest().build();
        }
        existing.setTaskId(taskId);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setCompleted(updatedTask.getCompleted());
        existing.setPriority(updatedTask.getPriority());
        existing.setDueDate(updatedTask.getDueDate());
        return ResponseEntity.ok(existing);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markCompleted(@PathVariable Long taskId) {
        Task existing = findById(taskId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setCompleted(true);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        Task existing = findById(taskId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tasks.remove(existing);
        return ResponseEntity.noContent().build();
    }

    private Task findById(Long id) {
        if (id == null) {
            return null;
        }
        for (Task task : tasks) {
            if (id.equals(task.getTaskId())) {
                return task;
            }
        }
        return null;
    }
}
