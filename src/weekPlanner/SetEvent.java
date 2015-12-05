package weekPlanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SetEvent extends Event {
    private LocalDateTime startTime;

    public SetEvent(String name, LocalDateTime startTime, Duration duration, int importance) {
        super(name, duration, importance);
        this.startTime = startTime;
    }

    public boolean canFit() {
        return true;
    }

    public Duration getMinChunkSize() {
        return null;
    }

    @Override
    public boolean conflict(ArrayList<Event> existing) {
        if(existing == null || existing.size() == 0) {
            return false;
        }
        for(Event e : existing) {
            if((this.getStart().isBefore(e.getEnd()) && this.getStart().isAfter(e.getStart()))
                    || (this.getEnd().isAfter(e.getStart()) && this.getEnd().isBefore(e.getEnd())))
            {
                return true;
            }
        }
        return false;

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

    @Override
    public Duration slice() {
        return this.getDuration();
    }

}