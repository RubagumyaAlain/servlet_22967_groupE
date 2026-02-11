package auca.ac.rw.Assignment1;

public class Task {
    
    Long taskId;
    String title;
    String description;
    boolean completed;
    String priority;
    String dueDate;

    public Task(){ }

    public void setTaskId(Long taskId){
        this.taskId = taskId;
    }

    public Long getTaskId(){
        return taskId;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public boolean getCompleted(){
        return completed;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }
    public String getPriority(){
        return priority;
    }

    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
    public String getDueDate(){
        return dueDate;
    }

}
