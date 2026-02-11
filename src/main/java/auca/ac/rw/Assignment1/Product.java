package auca.ac.rw.Assignment1;

public class Product {
    
    Long productId;
    String name;
    String description;
    Double price;
    String category;
    int stockQuantity;
    String brand;

    public Product() { }

    public Product(Long productId, String name, String description, Double price, String category, int stockQuantity, String brand) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.brand = brand;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName (){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice(){
        return price;
    }
    
    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setStockQuantity(int stockQuantity){
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity(){
        return stockQuantity;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getBrand(){
        return brand;
    }
}
