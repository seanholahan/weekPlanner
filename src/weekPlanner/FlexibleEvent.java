package weekPlanner;

import java.util.Date;

public class FlexibleEvent extends Event {
    private Date dueDate;
    private boolean splitable; // later
    private int minChunkSize; // later

    public FlexibleEvent(String name, Date startTime, int duration,
            int importance, Date dueDate, boolean splitable, int minChunkSize)
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
