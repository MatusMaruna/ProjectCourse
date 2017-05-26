package project;



public class Timeline {
	/* Timeline attributes */

	// private final SimpleStringProperty id;
	public String title;
	public String  startTime;
	public String  endTime;
	public String  description;
    public int TimeID ;
	
	//add id
	public Timeline(int id, String tile, String des, String sTime, String eTime) {

		// this.id = new SimpleStringProperty(Id);
		this.title = tile;
		this.startTime = sTime;
		this.endTime = eTime;
		this.description = des;
		this.TimeID = id;
		
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
   
	public int getId(){
		return TimeID;
	}
	
	
	public String getTitle() {
		return title;

	}

	

	public String getStartTime() {
		return startTime;

	}

	

	public String getEndTime() {
		return endTime;

	}

	

	public String getDescription() {
		return this.description;

	}



	/*public static boolean isEmpty() {

		if (Timeline.isEmpty())

			return true;
		else
			return false;
	}*/

}
