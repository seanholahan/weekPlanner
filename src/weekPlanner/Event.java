package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Event {
    private String name;
    private LocalDateTime startTime; // flexible events won't know their start
                                     // time at the time of initialization :
                                     // THINK OF IT AS
    // WHEN YOU WANT TO START EVEN WORKING ON THE PROJECT OR THINKING ABOUT IT
    private Duration duration;
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

    public LocalDateTime getStart() {
        return this.startTime;
    }

    public LocalDateTime getEnd() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public String toString() {
        return this.name + " [ " + this.getStart().getMonth() + " "
                + this.getStart().getDayOfMonth() + ", " + this.getStart().getYear() + " @ "
                + this.getStart().getHour() + ":" + this.getStart().getMinute() + " - "
                + this.getEnd().getMonth() + " " + this.getEnd().getDayOfMonth() + ", "
                + this.getEnd().getYear() + " @ " + this.getEnd().getHour() + ":"
                + this.getEnd().getMinute() + " ]" + " (" + this.importance + ")";
    }

    public boolean conflict(ArrayList<Event> existing) {
        if(existing == null) {
            return false;
        }
        for(Event e : existing) {
            if((this.getStart().isBefore(e.getEnd()) && this.getStart().isAfter(e.getStart()))
                    || (this.getEnd().isAfter(e.getStart()) && this.getEnd().isBefore(e.getEnd())))
            {
                return true;
            }
        }
        return false;

    }

}
