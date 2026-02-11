package auca.ac.rw.Assignment1;

public class MenuItem {
    Long id;
    String name;
    String description;
    Double price;
    String category;
    Boolean available;

public MenuItem(){}

public MenuItem (Long id, String name, String description, Double price, String category, Boolean available){
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.category = category;
}

public void setId (Long id){
    this.id = id;
}
public Long getId(){
    return id;
}

public void setName (String name){
    this.name = name;
}
public String getName(){
    return name;
}

public void setDescription (String description){
    this.description = description;
}
public String getDescription(){
    return description;
}

public void setPrice (Double price){
    this.price = price;
}
public Double getPrice(){
    return price;
}

public void setCategory (String category){
    this.category = category;
}
public String getCategory(){
    return category;
}

public void setAvailable (Boolean available){
    this.available = available;
}
public Boolean getAvailable(){
    return available;
}

}


