package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Event {
    private String name;
    public Duration duration;
    private int importance; // 1-10
    // TODO private boolean canStartLate;
    // TODO private boolean canEndEarly;

    public Event(String name, Duration duration, int importance) {
        this.name = name;
        this.duration = duration;
        this.importance = importance;
    }

    public abstract boolean isFlexible();

    public Duration getDuration() {
        return this.duration;
    }

    public String getName() {
        return this.name;
    }

    public int getImportance() {
        return this.importance;
    }

    public abstract LocalDateTime getStart();

    public abstract LocalDateTime getEarliestStart();

    public abstract LocalDateTime getEnd();

    public abstract boolean canFit();

    public abstract Duration slice();

    public abstract Duration getMinChunkSize();

    @Override
    public String toString() {
        return this.getName() + " [ " + this.getStart().getMonth() + " "
                + this.getStart().getDayOfMonth() + ", " + this.getStart().getYear() + " @ "
                + this.getStart().getHour() + ":" + this.getStart().getMinute() + " - "
                + this.getEnd().getMonth() + " " + this.getEnd().getDayOfMonth() + ", "
                + this.getEnd().getYear() + " @ " + this.getEnd().getHour() + ":"
                + this.getEnd().getMinute() + " ]" + " (" + this.getImportance() + ")";
    }

    public abstract boolean conflict(ArrayList<Event> existing);

    public static Comparator<Event> compareByStart() {
        return new Comparator<Event>() {

            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        };
    }

    public static Comparator<Event> compareByImportanceAndDuration() {
        return new Comparator<Event>() {

            @Override
            public int compare(Event o1, Event o2) {
                int impComp = new Integer(o1.getImportance()).compareTo(o2.getImportance());
                if(impComp != 0) {
                    return (-1) * impComp;
                }
                else {
                    return o1.getDuration().compareTo(o2.getDuration());
                }
            }
        };
    }
}