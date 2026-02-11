package auca.ac.rw.Assignment1.controller;

import auca.ac.rw.Assignment1.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1L, "iPhone 15", "128GB flagship phone", 999.0, "Electronics", 15, "Apple"),
            new Product(2L, "Galaxy S24", "Android flagship phone", 899.0, "Electronics", 25, "Samsung"),
            new Product(3L, "ThinkPad X1", "14\" business laptop", 1499.0, "Computers", 8, "Lenovo"),
            new Product(4L, "AirPods Pro", "Noise-cancelling earbuds", 249.0, "Accessories", 0, "Apple"),
            new Product(5L, "Kindle Paperwhite", "E-ink reader", 139.0, "Electronics", 30, "Amazon"),
            new Product(6L, "Instant Pot Duo", "7-in-1 electric pressure cooker", 119.0, "Home", 18, "Instant Pot"),
            new Product(7L, "Nike Pegasus 41", "Running shoes", 130.0, "Footwear", 40, "Nike"),
            new Product(8L, "Sony WH-1000XM5", "Over-ear noise cancelling headphones", 399.0, "Electronics", 12, "Sony"),
            new Product(9L, "Dyson V15 Detect", "Cordless vacuum cleaner", 699.0, "Home", 6, "Dyson"),
            new Product(10L, "Cuisinart Chef's Knife", "8-inch stainless steel knife", 49.0, "Kitchen", 55, "Cuisinart"),
            new Product(11L, "Adidas Ultraboost", "Performance running shoes", 180.0, "Footwear", 22, "Adidas")
    ));
    private long nextId = 12L;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "limit", defaultValue = "10") int limit) {
        if (page < 1 || limit < 1) {
            return List.of();
        }
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, products.size());
        if (start >= products.size()) {
            return List.of();
        }
        return products.subList(start, end);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = findById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        List<Product> matches = new ArrayList<>();
        if (category == null || category.isBlank()) {
            return matches;
        }
        String normalized = category.toLowerCase();
        for (Product product : products) {
            String pCat = product.getCategory();
            if (pCat != null && pCat.toLowerCase().equals(normalized)) {
                matches.add(product);
            }
        }
        return matches;
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getByBrand(@PathVariable String brand) {
        List<Product> matches = new ArrayList<>();
        if (brand == null || brand.isBlank()) {
            return matches;
        }
        String normalized = brand.toLowerCase();
        for (Product product : products) {
            String pBrand = product.getBrand();
            if (pBrand != null && pBrand.toLowerCase().equals(normalized)) {
                matches.add(product);
            }
        }
        return matches;
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return products;
        }
        String term = keyword.toLowerCase();
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            String name = product.getName();
            String description = product.getDescription();
            if ((name != null && name.toLowerCase().contains(term))
                    || (description != null && description.toLowerCase().contains(term))) {
                matches.add(product);
            }
        }
        return matches;
    }

    @GetMapping("/price-range")
    public List<Product> priceRange(@RequestParam(name = "min", required = false) Double min,
                                    @RequestParam(name = "max", required = false) Double max) {
        double lower = min == null ? Double.NEGATIVE_INFINITY : min;
        double upper = max == null ? Double.POSITIVE_INFINITY : max;
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            Double price = product.getPrice();
            if (price != null && price >= lower && price <= upper) {
                matches.add(product);
            }
        }
        return matches;
    }

    @GetMapping("/in-stock")
    public List<Product> inStock() {
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            if (product.getStockQuantity() > 0) {
                matches.add(product);
            }
        }
        return matches;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        if (newProduct == null) {
            return ResponseEntity.badRequest().build();
        }
        if (newProduct.getProductId() == null) {
            newProduct.setProductId(nextId++);
        } else if (findById(newProduct.getProductId()) != null) {
            return ResponseEntity.status(409).build();
        }
        products.add(newProduct);
        return ResponseEntity.created(URI.create("/api/products/" + newProduct.getProductId())).body(newProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updated) {
        Product existing = findById(productId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        existing.setProductId(productId);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setBrand(updated.getBrand());
        return ResponseEntity.ok(existing);
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId,
                                               @RequestParam(name = "quantity") Integer quantity) {
        Product existing = findById(productId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (quantity == null) {
            return ResponseEntity.badRequest().build();
        }
        existing.setStockQuantity(quantity);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Product existing = findById(productId);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        products.remove(existing);
        return ResponseEntity.noContent().build();
    }

    private Product findById(Long id) {
        if (id == null) {
            return null;
        }
        for (Product product : products) {
            if (id.equals(product.getProductId())) {
                return product;
            }
        }
        return null;
    }
}
