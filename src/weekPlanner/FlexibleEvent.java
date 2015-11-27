package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;

public class FlexibleEvent extends Event {
    private LocalDateTime dueDate;
    private boolean splitable;
    private Duration minChunkSize;
    // TODO go late, leave early

    // general constructor
    public FlexibleEvent(String name, LocalDateTime startTime,
            Duration duration, int importance, LocalDateTime dueDate,
            boolean splitable, Duration minChunkSize)
    {
        super(name, startTime, duration, importance);
        this.dueDate = dueDate;
        this.splitable = splitable;
        this.minChunkSize = minChunkSize;
    }

    // constructor for non-splittable events
    public FlexibleEvent(String name, LocalDateTime startTime,
            Duration duration, int importance, LocalDateTime dueDate)
    {
        this(name, startTime, duration, importance, dueDate, false, duration);
    }
    
    // constructor for splitable events
    public FlexibleEvent(String name, LocalDateTime startTime,
            Duration duration, int importance, LocalDateTime dueDate,
            Duration minChunkSize)
    {
        this(name, startTime, duration, importance, dueDate, true, minChunkSize);
    }

    @Override
    public boolean isFlexible() {
        return true;
    }

}
