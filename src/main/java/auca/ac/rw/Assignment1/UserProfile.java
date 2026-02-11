package auca.ac.rw.Assignment1;

public class UserProfile {
    Long userId;
    String userName;
    String email;
    String fullName;
    int age;
    String country;
    String bio;
    boolean active;

    public UserProfile(){}

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getUserId(){
        return userId;
    }

    public void setUserName(String username){
        this.userName = username;
    }
    public String getUserName(){
        return userName;
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
    public String getfullName(){
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
