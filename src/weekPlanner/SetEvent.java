package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;

public class SetEvent extends Event {
    private LocalDateTime startTime;

    public SetEvent(String name, LocalDateTime startTime, Duration duration,
            int importance)
    {
        super(name, duration, importance);
        this.startTime = startTime;
    }

    @Override
    public boolean isFlexible() {
        return false;
    }

    @Override
    public LocalDateTime getStart() {
        return this.startTime;
    }

    @Override
    public LocalDateTime getEarliestStart() {
        return this.startTime;
    }

    @Override
    public LocalDateTime getEnd() {
        return this.getStart().plusMinutes(this.getDuration().toMinutes());
    }

}
