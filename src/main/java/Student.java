public class Student {
 
    Long studentId;
    String firstName;
    String lastName;
    String email;
    String major;
    Double gpa;

    public Student(Long studentId, String firstName, String lastName, String email, String major, Double gpa){
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.gpa = gpa;
    }

    public void setStudentId(Long studentId){
        this.studentId = studentId;
    }
    public Long getStudentId(){
        return studentId;
    }
    public void setfirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFistName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setMajor(String major){
        this.major = major;
    }
    public String getMajor(){
        return major;
    }
    public void setGpa(Double gpa){
        this.gpa = gpa;
    }
    public Double getGpa(){
        return gpa;
    }
}
