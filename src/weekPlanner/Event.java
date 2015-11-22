package weekPlanner;

import java.util.Date;

public abstract class Event {
    String name;
    Date startTime; // flexible events won't know their start time at the time of initialization
    int duration;
    private int importance; // 1-10

    public Event(String name, Date startTime, int duration, int importance) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
    }

    public abstract boolean isFlexible();
    
    public int getImportance() {
        return this.importance;
    }
    
    public Date getStartTime() {
        return this.startTime;
    }
    
    //public int getEndTime() {
        // need a method to get the end time
    //}

}
