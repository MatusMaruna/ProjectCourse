package plugg;

public class Event {
	private int start;
	private int end;
	private String name;
	private String description;
	
	
	
	public Event(int s,int e,String n){
		start=s;
		end=e;
		name=n;
		
	}
	public void setStart(int n){
		start=n;
	}
	public int getStart(){
		return start;
	}
	public void setEnd(int n){
		end=n;
	}
	public int getEnd(){
		return end;
	}
	public void setDesc(String s){
		description=s;
		
	}
	public String getDesc(){
	return description;
	}
		public String toString(){
			return name+"\nStart: "+start+"\nEnd: "+end;
		}
}
