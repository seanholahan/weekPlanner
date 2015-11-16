
package weekPlanner;

import java.util.Date;

public class SetEvent extends Event{
	boolean canMiss;

	public SetEvent(String name, Date startTime, int duration, boolean canMiss) {
		super(name, startTime, duration);
		// TODO Auto-generated constructor stub
		this.canMiss = canMiss;
	}

}

