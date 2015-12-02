package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
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
        events.addAll(Arrays.asList(algClass, twerkingParty, mathClass));
        //events.addAll(Arrays.asList(read, dinner));
        Schedule mySchedule = new Schedule(events);

        mySchedule.makeSchedule();

        System.out.println("Here is your schedule:\n");
        for(Event e : mySchedule.getScheduledEvents()) {
            System.out.println(e);
        }
    }

}
