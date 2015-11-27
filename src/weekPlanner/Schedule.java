package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Schedule {

    private ArrayList<Event> allEvents;
    private ArrayList<Event> scheduledEvents;

    public Schedule(ArrayList<Event> allEvents) {
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
    }

    public void addScheduledEvent(Event e) {
        this.scheduledEvents.add(e);
    }

    public ArrayList<FlexibleEvent> getFlexibleEvents() {
        ArrayList<FlexibleEvent> flexible = new ArrayList<FlexibleEvent>();
        for(Event e : this.allEvents) {
            if(e.isFlexible()) {
                flexible.add((FlexibleEvent) e);
            }
        }
        return flexible;
    }

    public ArrayList<SetEvent> getSetEvents() {
        ArrayList<SetEvent> set = new ArrayList<SetEvent>();
        for(Event e : this.allEvents) {
            if(!e.isFlexible()) {
                set.add((SetEvent) e);
            }
        }
        return set;
    }

    public void makeSchedule() {
        // TODO sort events by importance and length
        // order: short-10, medium-10, long-10, short-9, medium-9, long-9...
        ArrayList<Event> finalSchedule = new ArrayList<Event>();
        for(int i = 10; i > 0; i--) {
            for(Event e : this.getAllEvents()) {
                // TODO needs to be able to see where to put flexible events and move around later as needed
                // perhaps set less flexible events first before flexible events with multiple possible places
                // but don't just do all set time events first, since they might not be the most important
                if(e.getImportance() == i && !e.conflict(finalSchedule)) {
                    finalSchedule.add(e);
                }
            }
        }
        this.setScheduledEvents(finalSchedule);
    }

    public static void main(String[] args) {

        LocalDateTime mathStart = LocalDateTime.of(2015, 11, 26, 9, 15);
        Duration mathDuration = Duration.ofMinutes(65);
        Event mathClass = new SetEvent("Math Class", mathStart, mathDuration, 10);

        LocalDateTime algStart = LocalDateTime.of(2015, 11, 26, 11, 45);
        Duration algDuration = Duration.ofMinutes(100);
        Event algClass = new SetEvent("Algorithms Class", algStart, algDuration, 9);

        LocalDateTime twerkingStart = LocalDateTime.of(2015, 11, 26, 10, 30);
        Duration twerkingDuration = Duration.ofMinutes(120);
        Event twerkingParty = new SetEvent("Twerking Party", twerkingStart, twerkingDuration, 8); // don't have time for this
        
        LocalDateTime dinnerStart = LocalDateTime.of(2015, 11, 26, 17, 0);
        LocalDateTime dinnerDue = LocalDateTime.of(2015, 11, 26, 23, 0);
        Duration dinnerDuration = Duration.ofHours(1);
        Event dinner = new FlexibleEvent("Dinner", dinnerStart, dinnerDuration, 10, dinnerDue);
        
        LocalDateTime readStart = LocalDateTime.of(2015, 11, 26, 22, 0);
        LocalDateTime readDue = LocalDateTime.of(2015, 11, 27, 0, 0);
        Duration readDuration = Duration.ofMinutes(30);
        Event read = new FlexibleEvent("Read Book", readStart, readDuration, 5, readDue);

        // TODO test with splitable event, implement splitting
        
        ArrayList<Event> events = new ArrayList<Event>();
        events.addAll(Arrays.asList(mathClass, algClass, twerkingParty, dinner, read));
        Schedule mySchedule = new Schedule(events);

        mySchedule.makeSchedule();

        System.out.println("Here is your schedule:\n");
        for(Event e : mySchedule.getScheduledEvents()) {
            System.out.println(e); // TODO should print out in chronological order
        }
    }
}
