package weekPlanner;

import java.util.ArrayList;

public class Schedule {

    ArrayList<Event> events;

    public Schedule(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<FlexibleEvent> getFlexibleEvents() {
        ArrayList<FlexibleEvent> flexible = new ArrayList<FlexibleEvent>();
        for (Event e : this.events) {
            if (e.isFlexible()) {
                flexible.add((FlexibleEvent) e);
            }
        }
        return flexible;
    }

    public ArrayList<SetEvent> getSetEvents() {
        ArrayList<SetEvent> set = new ArrayList<SetEvent>();
        for (Event e : this.events) {
            if (!e.isFlexible()) {
                set.add((SetEvent) e);
            }
        }
        return set;
    }

    /*
     * conflict if either: NEW_START < OLD_START + OLD_DURATION NEW_START +
     * NEW_DURATION > OLD_START
     */
    public static boolean conflict(ArrayList<Event> existing, Event newEvent) {
        return false; // Just here to prevent errors for now
        // for(Event e : existing) {
        // if(newEvent.startTime.before(new Date(e))) CANT DO UNTIL I HAVE AN
        // END TIME METHOD
        // }
    }

    public ArrayList<Event> makeSchedule(ArrayList<Event> events) {
        // sort events by importance and length
        ArrayList<Event> finalSchedule = new ArrayList<Event>();

        for (int i = 10; i > 0; i--) {
            for (Event e : events) {
                if (e.getImportance() == i && !conflict(finalSchedule, e)) {
                    finalSchedule.add(e);
                }
            }
        }
        return null; // Just here to prevent errors for now
    }
}
