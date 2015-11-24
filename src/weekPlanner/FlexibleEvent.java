package weekPlanner;
import java.time.Duration;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlexibleEvent extends Event {
    private LocalDateTime dueDate;
    private boolean splitable; // later
    private int minChunkSize; // later

    public FlexibleEvent(String name, LocalDateTime startTime, Duration duration,
            int importance, LocalDateTime dueDate, boolean splitable, int minChunkSize)
    {
        super(name, startTime, duration, importance);
        this.dueDate = dueDate;
        this.splitable = splitable;
        this.minChunkSize = minChunkSize;
    }

    @Override
    public boolean isFlexible() {
        return true;
    }

}
