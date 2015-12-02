package weekPlanner;

import java.util.ArrayList;
import java.util.Collections;

public class Schedule {

    private ArrayList<Event> allEvents;
    private ArrayList<Event> scheduledEvents;

    public Schedule(ArrayList<Event> allEvents) {
        Collections.sort(allEvents, Event.compareByImportanceAndDuration());
        this.allEvents = allEvents;
    }

    public ArrayList<Event> getAllEvents() {
        return this.allEvents;
    }

    public ArrayList<Event> getScheduledEvents() {
        return this.scheduledEvents;
    }
    
    public void setScheduledEvents(ArrayList<Event> events) {
        this.scheduledEvents = events;
        Collections.sort(events, Event.compareByStart());
    }

    public void addScheduledEvent(Event e) {
        this.scheduledEvents.add(e);
    }

    public void makeSchedule() {
        ArrayList<Event> finalSchedule = new ArrayList<Event>();
            for(Event e : this.getAllEvents()) {
                // TODO needs to be able to see where to put flexible events and move around later as needed
                // perhaps set less flexible events first before flexible events with multiple possible places
                // but don't just do all set time events first, since they might not be the most important
                if(!e.conflict(finalSchedule)) {
                    finalSchedule.add(e);
                }
            }
        this.setScheduledEvents(finalSchedule);   
    }
}
