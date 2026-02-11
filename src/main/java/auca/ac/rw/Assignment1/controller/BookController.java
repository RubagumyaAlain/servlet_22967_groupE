package auca.ac.rw.Assignment1.controller;

import auca.ac.rw.Assignment1.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Clean Code", "Robert Martin", "978-0132350884", 2008),
            new Book(2L, "Effective Java", "Joshua Bloch", "978-0134685991", 2018),
            new Book(3L, "Rich Dad Poor Dad", "Robert Kiyosaki", "978-1612680194", 1997)
    ));
    private long nextId = 4;
    @GetMapping
    public List<Book> getAll() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Book book = findById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public List<Book> searchByTitle(@RequestParam(name = "title", required = false) String title) {
        // Accept missing or blank title instead of throwing 400
        if (title == null || title.isBlank()) {
            return books;
        }
        String term = title.toLowerCase();
        List<Book> matches = new ArrayList<>();
        for (Book book : books) {
            String t = book.getTitle();
            if (t != null && t.toLowerCase().contains(term)) {
                matches.add(book);
            }
        }
        return matches;
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        if (book == null) {
            return ResponseEntity.badRequest().build();
        }
        if (book.getId() == null) {
            book.setId(nextId++); // auto-assign when client omits id
        } else if (findById(book.getId()) != null) {
            return ResponseEntity.status(409).build();
        }
        books.add(book);
        return ResponseEntity.created(URI.create("/api/books/" + book.getId())).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Book>> delete(@PathVariable Long id) {
        Book existing = findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        books.remove(existing);
        return ResponseEntity.noContent().build();
    }

    private Book findById(Long id) {
        if (id == null) {
            return null;
        }
        for (Book book : books) {
            if (id.equals(book.getId())) {
                return book;
            }
        }
        return null;
    }
}
