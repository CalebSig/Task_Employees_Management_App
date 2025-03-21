package DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task implements Serializable {

    List<Task> tasks;

    public ComplexTask(String statusTask, int idTask, List<Task> tasks) {
        super(statusTask, idTask);
        this.tasks = tasks;
    }

    public List<Task> getTasks() { return this.tasks; }

    @Override
    public int estimateDuration(){
        int s=0;
        for(Task t: tasks){
            s=s+t.estimateDuration();
        }
        return s;
    }

    @Override
    public String toString() {
        return this.getIdTask() + " (complex) " + this.getStatusTask() + " ";
    }

}
