package Back;

import javafx.beans.property.SimpleStringProperty;

public class Timeline {
	/* Timeline attributes */

	// private final SimpleStringProperty id;
	private final SimpleStringProperty title;
	private final SimpleStringProperty startTime;
	private final SimpleStringProperty endTime;
	private final SimpleStringProperty description;

	public Timeline(String tile, String sTime, String eTime, String des) {

		// this.id = new SimpleStringProperty(Id);
		this.title = new SimpleStringProperty(tile);
		this.startTime = new SimpleStringProperty(sTime);
		this.endTime = new SimpleStringProperty(eTime);
		this.description = new SimpleStringProperty(des);
	}
	/*
	 * }
	 * 
	 * public String getID() { return id.get();
	 * 
	 * }
	 * 
	 * public void setID(String id) { this.id.set(id);
	 * 
	 * }
	 */

	public String getTitle() {
		return title.get();

	}

	public void setTitle(String title) {
		this.title.set(title);

	}

	public String getStartTime() {
		return startTime.get();

	}

	public void setStartTime(String stime) {
		this.startTime.set(stime);

	}

	public String getEndTime() {
		return endTime.get();

	}

	public void setEndTime(String etime) {
		this.endTime.set(etime);

	}

	public String getDescription() {
		return this.description.get();

	}

	public void setDescription(String ds) {
		this.description.set(ds);

	}

	public static boolean isEmpty() {

		if (Timeline.isEmpty())

			return true;
		else
			return false;
	}

}
