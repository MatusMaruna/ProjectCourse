package Testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import project.*; 
import javafx.scene.layout.GridPane;


public class CreateLoadTimelineTest {
	public int id;
	 @Before
		public void setUp() throws SQLException   {
		 CreateTimeline test = new CreateTimeline();
			test.tlt="testTitle";
			test.des="testDescription";
			String start ="01/01/2017";
			String end ="02/02/2017";
			// creates a timeline with these values
			builder(test.tlt,test.des,start,end);
	
		}
	 
	@After
		public void tearDown() {
		Connection conn;
		try {
			conn = Sqlconnection.DbConnector();
			 String st = "DELETE FROM TimeLine WHERE Title ='"+ "testTitle" +"'";
			PreparedStatement state = conn.prepareStatement(st);
		   
		    state.execute();
			state.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testCreateTimeline() throws SQLException   {
	
		CreateTimeline test = new CreateTimeline();
		test.tlt="testTitle";
		test.des="testDescription";
		String start ="01/01/2017";
		String end ="02/02/2017";
		// creates a timeline with these values
		
		//checks if timeline is created and if values are correct
		Connection con = Sqlconnection.DbConnector();
	    String sql = "SELECT rowid, * FROM TimeLine WHERE Title ='"+ test.tlt +"'";
	    PreparedStatement pre = con.prepareStatement(sql);
	    ResultSet result = pre.executeQuery();
	    assertEquals(result.getString("Title"),test.tlt);
	    assertEquals(result.getString("Description"),test.des);
	    assertEquals(result.getString("StartDate"),start);
	    assertEquals(result.getString("EndDate"),end);
	    id=result.getRow();
	    System.out.println(id);
	    pre.execute();
		pre.close();
	
	}
	@Test
	public void testLoadTimeline() throws SQLException   {
	
		String titles="";
		String descriptions="";
		String start="";
		String end="";
		
		Connection con = Sqlconnection.DbConnector();
		//loads everything from the database
		String sql = "select * FROM TimeLine ";
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet result = pre.executeQuery();
		
		while (result.next()) {
			//adds all titles and description to strings
			titles+=result.getString("Title");
			descriptions+=result.getString("Description");
			start+=result.getString("StartDate");
			end+=result.getString("EndDate");
			
		}
		
		//checks if these strings contain values that we know should be loaded
		assertEquals(titles.contains("testTitle"),true);
		assertEquals(descriptions.contains("testDescription"),true);
		assertEquals(start.contains("2017"),true);
		//end dates loaded can/should not contain letters like this
		assertEquals(end.contains("WQWEWQEQWEQWEWQEEWQEWQE"),false);
		
		pre.execute();
		pre.close();	
	
		
		  
		
			
	 
	    
	
	
	}

	public void builder(String title,String desc,String sDate,String eDate) throws SQLException{
		 String query = "INSERT INTO TimeLine (Title, Description, StartDate, EndDate) VALUES (?,?,?,?)  ";
    	 Connection con = Sqlconnection.DbConnector();
         PreparedStatement pre = con.prepareStatement(query);
         pre.setString(1, title);
		 pre.setString(2, desc);

		 pre.setString(3, sDate);
         pre.setString(4, eDate);
         pre.execute();
         
		  
		  
			
			
	     pre.close();
	}

}
