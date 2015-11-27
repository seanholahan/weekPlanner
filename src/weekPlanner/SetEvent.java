package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;

public class SetEvent extends Event {

    public SetEvent(String name, LocalDateTime startTime, Duration duration,
            int importance)
    {
        super(name, startTime, duration, importance);
    }

    @Override
    public boolean isFlexible() {
        return false;
    }

}
