package auca.ac.rw.Assignment1;

public class UserProfile {
    Long userId;
    String username;
    String email;
    String fullName;
    int age;
    String country;
    String bio;
    boolean active;

    public UserProfile(){}

    public UserProfile(Long userId, String username, String email, String fullName, int age, String country, String bio, boolean active) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.country = country;
        this.bio = bio;
        this.active = active;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getUserId(){
        return userId;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getFullName(){
        return fullName;
    }

    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return age;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return country;
    }
    
    public void setBio(String bio){
        this.bio = bio;
    }
    public String getBio(){
        return bio;
    }

    public void setActive(boolean active){
        this.active = active;
    }
    public boolean getActive(){
        return active;
    }
}
