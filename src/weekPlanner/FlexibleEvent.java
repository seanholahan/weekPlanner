package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class FlexibleEvent extends Event {
    private LocalDateTime earliestStart;
    private LocalDateTime dueDate;
    private boolean splitable;
    private Duration minChunkSize;
    private LocalDateTime startTime;

    // general constructor
    public FlexibleEvent(String name, LocalDateTime earliestStart, Duration duration,
            int importance, LocalDateTime dueDate, boolean splitable, Duration minChunkSize)
    {
        super(name, duration, importance);
        this.earliestStart = earliestStart;
        this.dueDate = dueDate;
        this.splitable = splitable;
        this.minChunkSize = minChunkSize;
    }

    // constructor for non-splittable events
    public FlexibleEvent(String name, LocalDateTime earliestStart, Duration duration,
            int importance, LocalDateTime dueDate)
    {
        this(name, earliestStart, duration, importance, dueDate, false, duration);
    }

    // constructor for splitable events
    public FlexibleEvent(String name, LocalDateTime earliestStart, Duration duration,
            int importance, LocalDateTime dueDate, Duration minChunkSize)
    {
        this(name, earliestStart, duration, importance, dueDate, true, minChunkSize);
    }

    @Override
    public boolean isFlexible() {
        return true;
    }

    @Override
    public boolean conflict(ArrayList<Event> existing) {
        
        // if there are no existing events, then no conflict 
        if(existing == null || existing.size() == 0) {
            return this.noConflict();
        }
        
        ArrayList<Event> conflictable = this.getConflictingEvents(existing);      

        // if there are no conflitables, then no conflict
        if(conflictable.size() == 0) {
            return this.noConflict();
        }

        Collections.sort(conflictable, Event.compareByStart());

        // the earliest possible gap start and end
        LocalDateTime tempBegin = this.getEarliestStart();
        LocalDateTime tempEnd = this.getDueDate();
                
        LocalDateTime firstEventStart = conflictable.get(0).getStart();
        LocalDateTime firstEventEnd = conflictable.get(0).getEnd();
        LocalDateTime lastEventStart = conflictable.get(conflictable.size() - 1).getStart();
        LocalDateTime lastEventEnd = conflictable.get(conflictable.size() - 1).getEnd();

        // loop starting end ending iterations
        int loopStart, loopEnd;
        
        // set loop start
        if(firstEventStart.isBefore(this.getEarliestStart())) {
            tempBegin = firstEventEnd;
            loopStart = 0;
        }
        else {
            loopStart = -1;
        }
        
        // set loop end
        if(lastEventEnd.isAfter(this.getDueDate())) {
            tempEnd = lastEventStart;
            loopEnd = conflictable.size() - 1;
        }
        else {
            loopEnd = conflictable.size();
        }
        
        // find the shortest gap and place the event in that space, if it exists
        return findShortestGap(loopStart, loopEnd, conflictable, tempBegin, tempEnd);
    }
    
    // if there are no conflicting events
    private boolean noConflict() {
        this.setStart(this.getEarliestStart());
        return false;
    }
    
    // make list of possibly conflicting events
    private ArrayList<Event> getConflictingEvents(ArrayList<Event> existing) {
        
        ArrayList<Event> conflictable = new ArrayList<Event>();
        for(Event e : existing) {
            
            // if an existing event falls within the allowable range of this event, add it to conflictables
            if(e.getEnd().isAfter(this.getEarliestStart()) && e.getStart().isBefore(this.getDueDate())
                    || e.getStart().isBefore(this.getDueDate()) && e.getEnd().isAfter(this.getEarliestStart()))
            {
                conflictable.add(e);
            }
        }
        return conflictable;
    }
    
    private boolean findShortestGap(int loopStart, int loopEnd, ArrayList<Event> conflictable, LocalDateTime tempBegin, LocalDateTime tempEnd) {
     
        // keep track of shortest gap
        Duration shortestGap = null;
        LocalDateTime shortestStart = null;
        
        // find shortest gap
        for(int i = loopStart; i < loopEnd; i++) {
            LocalDateTime gapStart, gapEnd;
            
            // if one event takes up the whole block, then there is a conflict
            
//            if(e.getStart().isEqual(this.getEarliestStart()) && e.getEnd().isEqual(this.getDueDate())
//                    || e.getStart().isBefore(this.getEarliestStart()) && e.getEnd().isAfter(this.getDueDate()))
//            {
//                return true;
//            }  // TODO test without this

            // first gap
            if(i == -1) {
                gapStart = tempBegin;
                gapEnd = conflictable.get(0).getStart();
            }
            
            // middle gaps
            else if(i + 1 == conflictable.size()) {
                gapStart = conflictable.get(i).getEnd();
                gapEnd = tempEnd;
            }
            
            // last gap
            else {
                gapStart = conflictable.get(i).getEnd();
                gapEnd = conflictable.get(i + 1).getStart();
            }

            // calculate the gap size and store info on the shortest gap
            Duration gap = Duration.between(gapStart, gapEnd);
            if(gapStart.isBefore(gapEnd) && gap.compareTo(this.getDuration()) >= 0) {
                if(shortestGap == null || gap.compareTo(shortestGap) <= 0) {
                    shortestGap = gap;
                    shortestStart = gapStart;
                }
            }
        }
        if(shortestStart != null) {
            this.setStart(shortestStart);
            return false;
        }
        return true;
    }
    
    
//    private LocalDateTime kickout(ArrayList<Event> conflictables) {
//        ArrayList<Event> kickedOut = new ArrayList<Event>();
//        for(Event e : conflictables) {
//            if(e.getImportance() < this.getImportance()) {
//                kickedOut.add(e);
//                conflictables.remove(e);
//            }
//            
//            // find 
//            
//        }
//    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public boolean isSplitable() {
        return this.splitable;
    }

    public Duration getMinChunkSize() {
        return this.minChunkSize;
    }

    @Override
    public LocalDateTime getEarliestStart() {
        return this.earliestStart;
    }

    @Override
    public LocalDateTime getStart() {
        return startTime;
    }

    private void setStart(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public LocalDateTime getEnd() {
        return getStart().plus(this.getDuration());
    }

}