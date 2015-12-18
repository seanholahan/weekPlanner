package weekPlanner;

import java.util.ArrayList;
import java.util.Collections;

public class Schedule {

    private ArrayList<Event> allEvents;
    private ArrayList<Event> scheduledEvents;

    public Schedule(ArrayList<Event> allEvents) {
        Collections.sort(allEvents, Event.compareByType_Importance_Duration());
        this.allEvents = allEvents;
    }

    public ArrayList<Event> getAllEvents() {
        return this.allEvents;
    }

    public ArrayList<Event> getScheduledEvents() {
        return this.scheduledEvents;
    }

    public void setScheduledEvents(ArrayList<Event> events) {
        Collections.sort(events, Event.compareByStart());
        this.scheduledEvents = events;
    }

    public void makeSchedule() {
        ArrayList<Event> finalSchedule = new ArrayList<Event>();
        for(Event e : this.getAllEvents()) {
            System.out.println(e.getName() + " " + e.getImportance() + " " + e.getDuration().toMinutes() + " " + e.isFlexible());
            if(!e.conflict(finalSchedule)) {
                finalSchedule.add(e);
            }
        }
        this.setScheduledEvents(finalSchedule);
    }
}
