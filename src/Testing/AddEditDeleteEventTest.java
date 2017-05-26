package Testing;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import project.*; 
import javafx.scene.layout.GridPane;


public class AddEditDeleteEventTest {



	  @Before
			public void setUp()   {
		   
			String name="testevent";
			String desc="testdesc";
			String start="01/01/2017";
			String end ="02/01/2017";
			try {
			
				adding(name,desc,start, end);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 
		@After
			public void tearDown() {
			Connection conn;
			String name="testevent";
			try {
				conn = Sqlconnection.DbConnector();
				 String st = "DELETE FROM SaveEvent WHERE Name ='"+ name +"'";
				PreparedStatement state = conn.prepareStatement(st);
			   
			    state.execute();
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	@Test
	public void AddEvent() throws SQLException  {
	
			//test event
			String name="testevent";
			String desc="testdesc";
			String start="01/01/2017";
			String end ="02/01/2017";
			//adding the test event to the database
			adding(name,desc,start, end);
			Connection connect = Sqlconnection.DbConnector();
		    String query = "SELECT * FROM SaveEvent WHERE Name ='"+ name+"'";
		    PreparedStatement prep = connect.prepareStatement(query);
		    ResultSet result = prep.executeQuery();
		
	      
		    //checks if it added it to the database and if the values are the same
		    assertEquals( result.getString("Name"),name);
		    assertEquals( result.getString("Description"),desc);
		    assertEquals( result.getString("StartDate"),start);
		    assertEquals( result.getString("EndDate"),end);
		   
		    
		   
		    prep.execute();
		    prep.close();
		}
		
	@Test
	public void EditEvent() throws SQLException  {
		String name="testevent";
		String desc="testdesc";
		String start="01/01/2017";
		String end ="02/01/2017";
		Connection con = Sqlconnection.DbConnector();
		//updating the event previously added
		String sql = "update  SaveEvent set Description=?,StartDate=?,EndDate=? where Name='"
				+ name + "'";
	    PreparedStatement pre = con.prepareStatement(sql);
	    
	    pre = con.prepareStatement(sql);	
	   //providing new values
		pre.setString(1, "newtestdesc");
		pre.setString(2, "newteststart");
		pre.setString(3, "newtestend");
				
		pre.execute();
		pre.close();
		   	
				
				
	
		//checks if the edit went through and works		
		Connection connect = Sqlconnection.DbConnector();
	    String query = "SELECT * FROM SaveEvent WHERE Name ='"+ name+"'";
	    PreparedStatement prep = connect.prepareStatement(query);
	    ResultSet res = prep.executeQuery();		
	     
	    //name didnt get changed so should be true
	    assertEquals( res.getString("Name"),name);
	    //see if description is different from before editing
	    assertNotEquals(res.getString("Description"),desc);
	    //new value works?
	    assertEquals(res.getString("Description"),"newtestdesc");
	    //see if description is different from before editing
	    assertNotEquals( res.getString("StartDate"),start);
	    //new value works?
	    assertEquals( res.getString("StartDate"),"newteststart");
	    //see if description is different from before editing
	    assertNotEquals( res.getString("EndDate"),end);
	    //new value works?
	    assertEquals( res.getString("EndDate"),"newtestend");
		    
		prep.execute();
		prep.close();
		}
			
	@Test
	public void DeleteEvent() throws SQLException  {
		//adding an event to the database
		String name="qweqwe";
		String desc="testqweqwewqdesc";
		String start="01/qweqewqe01/2017";
		String end ="02/0qweqe1/2017";
	    adding(name,desc,start, end);
		
	    //trying to delete it again
	    Connection conn;
		conn = Sqlconnection.DbConnector();
		String st = "DELETE FROM SaveEvent WHERE Name ='"+ name +"'";
		PreparedStatement state = conn.prepareStatement(st);
		state.execute();
		state.close();
		
		Connection connect = Sqlconnection.DbConnector();
	    String query = "SELECT * FROM SaveEvent WHERE Name ='"+ name+"'";
	    PreparedStatement prep = connect.prepareStatement(query);
	    ResultSet res = prep.executeQuery();
	    //empty result since the evenet doesnt exist
	     assertEquals(res.next(),false);
	
	   
		
	}
	
	
	public void adding(String name,String desc,String start,String end) throws SQLException  {
		
		Connection conn = Sqlconnection.DbConnector();
			String sql = "INSERT INTO SaveEvent (ID ,Name, Description, StartDate, EndDate,Image) VALUES (?,?,?,?,?,?)";
			PreparedStatement pree = conn.prepareStatement(sql);
			pree = conn.prepareStatement(sql);
			pree.setString(1,null);	
			pree.setString(2, name);
			pree.setString(3, desc);

			pree.setString(4, start);

			pree.setString(5, end);
			pree.execute();
	        pree.close();
	
		
	}

}
