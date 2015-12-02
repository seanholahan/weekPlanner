package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;

public class FlexibleEvent extends Event {
    private LocalDateTime earliestStart; // need a separate field to distinguish from when the event actually starts
    private LocalDateTime dueDate;
    private boolean splitable;
    private Duration minChunkSize;

    // general constructor
    public FlexibleEvent(String name, LocalDateTime earliestStart,
            Duration duration, int importance, LocalDateTime dueDate,
            boolean splitable, Duration minChunkSize)
    {
        super(name, duration, importance);
        this.earliestStart = earliestStart;
        this.dueDate = dueDate;
        this.splitable = splitable;
        this.minChunkSize = minChunkSize;
    }

    // constructor for non-splittable events
    public FlexibleEvent(String name, LocalDateTime earliestStart,
            Duration duration, int importance, LocalDateTime dueDate)
    {
        this(name, earliestStart, duration, importance, dueDate, false, duration);
    }
    
    // constructor for splitable events
    public FlexibleEvent(String name, LocalDateTime earliestStart,
            Duration duration, int importance, LocalDateTime dueDate,
            Duration minChunkSize)
    {
        this(name, earliestStart, duration, importance, dueDate, true, minChunkSize);
    }

    @Override
    public boolean isFlexible() {
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
        return null;
    }

    @Override
    public LocalDateTime getEnd() {
        return null;
    }

}
