package Back;

import javafx.beans.property.SimpleStringProperty;

public class CreateEvent {

	/*
	 * here we dont border about the types bc we only decide them when adding
	 * them to the database so all shd be strings for now
	 */
	private final SimpleStringProperty name;
	private final SimpleStringProperty Description;
	private final SimpleStringProperty startDate;
	private final SimpleStringProperty endDate;

	public CreateEvent(String name, String dis, String sdate, String edate) {

		this.name = new SimpleStringProperty(name);
		this.Description = new SimpleStringProperty(dis);
		this.startDate = new SimpleStringProperty(sdate);
		this.endDate = new SimpleStringProperty(edate);

	}

	public String getEndDate() {
		return endDate.get();

	}

	public void setEndDate(String name) {
		this.startDate.set(name);

	}

	public String getStartDate() {
		return startDate.get();

	}

	public void setStartDate(String name) {
		this.startDate.set(name);

	}

	public String getName() {
		return name.get();

	}

	public void setName(String name) {
		this.name.set(name);

	}

	public String getDescription() {
		return this.Description.get();

	}

	public void setDescription(String ds) {
		this.Description.set(ds);

	}

}
