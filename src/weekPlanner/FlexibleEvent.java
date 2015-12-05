package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        if(existing == null || existing.size() == 0) {
            return false;
        }
        ArrayList<Event> conflictable = new ArrayList<Event>();

        // make list of possibly conflicting events
        for(Event e : existing) {
            if(e.getEnd().isAfter(getEarliestStart()) || e.getStart().isBefore(getDueDate())) {
                conflictable.add(e);
            }
        }

        // check if there is a large enough gap in the schedule for the event
        for(int i = 0; i < conflictable.size() - 1; i++) {
            LocalDateTime gapStart = conflictable.get(i).getEnd();
            LocalDateTime gapEnd = conflictable.get(i + 1).getStart();
            Duration gap = Duration.between(gapStart, gapEnd);
            if(gap.compareTo(this.getDuration()) < 0) {
                this.setStart(gapStart);
                return false;
            }
        }
        return true;
    }

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

    public Duration timeFrame() {
        return Duration.between(this.getEarliestStart(), this.getDueDate());

    }

    public static Duration timeFrame(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);

    }

    public boolean canFit() {
        return !earliestStart.plus(getDuration()).isAfter(getDueDate());

    }

    public Duration slice() {
        return Duration.between(getEarliestStart(), getDueDate());
    }

}