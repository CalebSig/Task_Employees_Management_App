package DataModel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits ComplexTask, SimpleTask  {
    private int idTask;
    private String statusTask;

    public Task(String statusTask, int idTask) {
        this.statusTask = statusTask;
        this.idTask = idTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public abstract int estimateDuration();

}
