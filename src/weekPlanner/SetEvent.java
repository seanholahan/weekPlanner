package weekPlanner;
import java.time.Duration;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SetEvent extends Event {

    public SetEvent(String name, LocalDateTime startTime, Duration duration, int importance) {
        super(name, startTime, duration, importance);
    }

    @Override
    public boolean isFlexible() {
        return false;
    }

}
