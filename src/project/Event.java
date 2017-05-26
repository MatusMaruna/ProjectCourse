package project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Event {

	/*
	 * here we dont border about the types bc we only decide them when adding
	 * them to the database so all shd be strings for now
	 */
	public int id;
	public String name;
	public String Description;
	public String sDate;
	public String eDate;
    public int Eventduration;
    public int StartPoint;
    public boolean durational  ;
    public int EventEndPoint;
    
    
	public Event(int id, String name, String dis, String sdate, String edate) {
		this.id = id;
		this.name = name;
		this.Description = dis;
		this.sDate = sdate;
		this.eDate = edate;

	}
	
	
	//the method
   public boolean durational(){
	 return  durational;
   }
	
   public void setEndPoint(int sp){
		this.EventEndPoint = sp;
	}
   public int getEndPoint(){
		return EventEndPoint;
	}
	
   
	public void setStartPoint(int sp){
		this.StartPoint = sp;
	}
	//if the duration has 0 days then is not durational
	public void setEventDuration(int sed){
		if(sed == 0)
			this.durational = false;
		else
			this.durational = true;
		
		
		this.Eventduration = sed;
	}
	
	public int getStartPoint(){
		return StartPoint;
	}
	
	public int getEventduration(){
		return Eventduration;
	}
	
	public int getID() {
		  return id;
	}
	 
	
	public String getEndDate() {
		return eDate;

	}

	
	public String getStartDate() {
		return sDate;

	}

	

	public String getName() {
		return name;

	}

	
	public String getDescription() {
		return this.Description;

	}

	

}