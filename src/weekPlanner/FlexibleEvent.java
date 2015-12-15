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

    // is the event flexible
    @Override
    public boolean isFlexible() {
        return true;
    }

    // is there a conflict? if not, this will also set the start time of an event
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
                
        int[] loopBounds = getLoopBounds(conflictable, tempBegin, tempEnd);
        
        // find the shortest gap and place the event in that space, if it exists
        return findShortestGap(loopBounds[0], loopBounds[1], conflictable, tempBegin, tempEnd);
    }
    
    // determine conflict loop start and end indices
    private int[] getLoopBounds(ArrayList<Event> conflictable, LocalDateTime tempBegin, LocalDateTime tempEnd) {
        
        // loop starting end ending iterations
        int[] loopBounds = new int[]{-1, conflictable.size()};
                
        // set loop start
        Event firstConflictingEvent = conflictable.get(0);
        if(firstConflictingEvent.getStart().isBefore(this.getEarliestStart())) {
            tempBegin = firstConflictingEvent.getEnd();
            loopBounds[0] = 0;
        }
        
        // set loop end
        Event lastConflictingEvent = conflictable.get(conflictable.size() - 1);
        if(lastConflictingEvent.getEnd().isAfter(this.getDueDate())) {
            tempEnd = lastConflictingEvent.getStart();
            loopBounds[1] = conflictable.size() - 1;
        }
        
        return loopBounds;
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
    
    // finds the shortest gap for an event
    
    private boolean findShortestGap(int loopStart, int loopEnd, ArrayList<Event> conflictable, LocalDateTime tempBegin, LocalDateTime tempEnd) {
     
        // keep track of shortest gap information
        Duration shortestGap = null;
        LocalDateTime shortestStart = null;

        for(int i = loopStart; i < loopEnd; i++) {

            LocalDateTime[] gapBounds = this.getGapBounds(i, conflictable, tempBegin, tempEnd);
            LocalDateTime gapStart = gapBounds[0];
            LocalDateTime gapEnd = gapBounds[1];

            // calculate the gap size and store info on the shortest gap
            Duration gap = Duration.between(gapStart, gapEnd); 
            if(gapStart.isBefore(gapEnd) && gap.compareTo(this.getDuration()) >= 0 && (shortestGap == null || gap.compareTo(shortestGap) <= 0)) {
                shortestGap = gap;
                shortestStart = gapStart;
            }
        }
        
        if(shortestStart != null) {
            this.setStart(shortestStart);
            return false;
        }
        return true;
    }
    
    // gets the start and end times of a gap
    
    private LocalDateTime[] getGapBounds(int gapNumber, ArrayList<Event> conflictable, LocalDateTime tempBegin, LocalDateTime tempEnd) {
        
        int start = 0;
        int end = 1;
        LocalDateTime[] gapBounds = new LocalDateTime[2];
        
        // first gap
        if(gapNumber == -1) {
            gapBounds[start] = tempBegin;
            gapBounds[end] = conflictable.get(0).getStart();
        }
        
        // middle gaps
        else if(gapNumber + 1 == conflictable.size()) {
            gapBounds[start] = conflictable.get(gapNumber).getEnd();
            gapBounds[end] = tempEnd;
        }
        
        // last gap
        else {
            gapBounds[start] = conflictable.get(gapNumber).getEnd();
            gapBounds[end] = conflictable.get(gapNumber + 1).getStart();
        }
        return gapBounds;
    }
    
    // kicks out less important events to try to make room for this one
    
    private ArrayList<Event> kickout(ArrayList<Event> conflictable) {
        ArrayList<Event> kickedOut = new ArrayList<Event>();
        for(Event e : conflictable) {
            if(e.getImportance() < this.getImportance()) {
                kickedOut.add(e);
            }
        }
        return kickedOut;
    }

    // returns this event's due date
    
    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    // is this event splitable
    
    public boolean isSplitable() {
        return this.splitable;
    }

    // gets this event's minimum chunk size
    
    public Duration getMinChunkSize() {
        return this.minChunkSize;
    }

    // gets this event's earliest allowable start time
    
    @Override    
    public LocalDateTime getEarliestStart() {
        return this.earliestStart;
    }

    // get this event's start time
    @Override
    public LocalDateTime getStart() {
        return startTime;
    }

    // sets this event's start time
    
    private void setStart(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // get's this event's end time
    
    @Override
    public LocalDateTime getEnd() {
        return getStart().plus(this.getDuration());
    }

}