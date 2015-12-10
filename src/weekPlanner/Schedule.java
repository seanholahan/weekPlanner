package weekPlanner;

import java.util.ArrayList;
import java.util.Collections;

public class Schedule {

    private ArrayList<Event> allEvents;
    private ArrayList<Event> scheduledEvents;

    public Schedule(ArrayList<Event> allEvents) {
        Collections.sort(allEvents, Event.compareByImportanceAndDurationAndType());
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

    public void addScheduledEvent(Event e) {
        this.scheduledEvents.add(e);
    }

    public void makeSchedule() {
        ArrayList<Event> finalSchedule = new ArrayList<Event>();
        for(Event e : this.getAllEvents()) {
            if(!e.conflict(finalSchedule)) {
                finalSchedule.add(e);
            }
        }
        this.setScheduledEvents(finalSchedule);
    }
}