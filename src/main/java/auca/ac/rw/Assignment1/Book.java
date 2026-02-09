package auca.ac.rw.Assignment1;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;

    // Needed by Jackson for JSON deserialization when fields are provided by the client.
    public Book() {}

    public Book(Long id, String title, String author, String isbn, int publicationYear){
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getIsbn(){
        return this.isbn;
    }

    public void setIsbn (String isbn){
        this.isbn = isbn;
    }

    public int getPublicationYear(){
        return this.publicationYear;
    }

    public void setPublicationYear( int publicationYear){
        this.publicationYear = publicationYear;
    }
}

