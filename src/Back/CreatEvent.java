package Back;

import javafx.beans.property.SimpleStringProperty;

public class CreatEvent {

	/*
	 * here we dont border about the types bc we only decide them when adding
	 * them to the database so all shd be strings for now
	 */
	private final SimpleStringProperty name;
	private final SimpleStringProperty Description;
	private final SimpleStringProperty sDate;
	private final SimpleStringProperty eDate;

	public CreatEvent(String name, String dis, String sdate, String edate) {

		this.name = new SimpleStringProperty(name);
		this.Description = new SimpleStringProperty(dis);
		this.sDate = new SimpleStringProperty(sdate);
		this.eDate = new SimpleStringProperty(edate);

	}

	public String getEndDate() {
		return eDate.get();

	}

	public void etEndDate(String name) {
		this.sDate.set(name);

	}

	public String getStartDate() {
		return sDate.get();

	}

	public void setStartDate(String name) {
		this.sDate.set(name);

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
