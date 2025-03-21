package DataModel;

import java.io.Serializable;

public non-sealed class SimpleTask extends Task implements Serializable {

    private int startHour;
    private int endHour;

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public SimpleTask(String statusTask, int idTask, int startHour, int endHour) {
        super(statusTask, idTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration(){
        return this.endHour-this.startHour;
    }

    @Override
    public String toString() {
        return this.getIdTask() + " (simple) " + this.getStatusTask() + " ";
    }

}
