package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Event {
    private String name;
    public Duration duration;
    private int importance; // 1-10

    public Event(String name, Duration duration, int importance) {
        this.name = name;
        this.duration = duration;
        this.importance = Math.max(Math.min(importance, 10), 1);
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

    public static Comparator<Event> compareByType_Importance_Duration() {
        return new Comparator<Event>() {

            @Override
            public int compare(Event o1, Event o2) {
                
                boolean sameType = (o1.isFlexible() && o2.isFlexible()) || (!o1.isFlexible() && !o2.isFlexible());
                
                if(!sameType) {
                    if(!o1.isFlexible()) {
                        return -1;
                    }
                    return 1;
                }
                
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