package weekPlanner;

import java.util.Date;

public class SetEvent extends Event {

    public SetEvent(String name, Date startTime, int duration, int importance) {
        super(name, startTime, duration, importance);
    }

    @Override
    public boolean isFlexible() {
        return false;
    }

}
