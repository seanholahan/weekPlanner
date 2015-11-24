package weekPlanner;

import java.util.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.time.LocalDateTime;

public abstract class Event {
    String name;
    LocalDateTime startTime; // flexible events won't know their start time at the time of initialization :  THINK OF IT AS 
    //WHEN YOU WANT TO START EVEN WORKING ON THE PROJECT OR THINKING ABOUT IT
    Duration duration;
    private int importance; // 1-10

    public Event(String name, LocalDateTime startTime, Duration duration, int importance) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
    }

    public abstract boolean isFlexible();
    
    public int getImportance() {
        return this.importance;
    }
    
    public LocalDateTime getStartTime() {
        return this.startTime;
    }
    
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

}
