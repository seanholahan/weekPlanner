
package weekPlanner;
import java.util.Date;


public class FlexibleEvent extends Event {
	Date dueDate;
	boolean splitAble;
	int minChunkSize; 
	int importanceWeight; //1-10

	public FlexibleEvent(String name, Date startTime, int duration, Date dueDate,
			boolean splitAble, int minChunkSize, int importanceWeight) {
		super(name, startTime, duration);
		// TODO Auto-generated constructor stub
		this.dueDate = dueDate; 
		this.splitAble = splitAble; 
		this.minChunkSize = minChunkSize; 
		this.importanceWeight = importanceWeight; 
	}

}

