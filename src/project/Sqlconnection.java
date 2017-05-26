package project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sqlconnection {

	

public static Connection DbConnector() throws SQLException {

		try {
			Connection con = null;
			
		     Class.forName("org.sqlite.JDBC");

			con = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
			
			if(!tableExist(con,"TimeLine"))
				TimelineCreator();
			
			if(!tableExist(con,"SaveEvent"))
				SaveEventCreator();
			
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
		
		return null;
	}


    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
    boolean tExists = false;
    try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
        while (rs.next()) { 
            String tName = rs.getString("TABLE_NAME");
            if (tName != null && tName.equals(tableName)) {
                tExists = true;
                break;
            }
        }
    }
    return tExists;
}
  
	public static  void SaveEventCreator(){
		   Connection conn = null;
		   Statement stmt = null;
		   
		   
		   
		   try{
		      
			   Class.forName("org.sqlite.JDBC");

		      
		      System.out.println("Connecting to a selected database...");
		  	conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		      
		      
		      System.out.println("Connected database successfully...");
		      
		      
		    
		      System.out.println("Creating table in given database...");
		      stmt = conn.createStatement();
		      
		      String sql = "CREATE TABLE SaveEvent " +
		                   "(ID INTEGER , " +
		                   " Name TEXT, " + 
		                   " Description TEXT, " +  
		                   " StartDate TEXT, " +
		                   " EndDate TEXT, " +
		                   " Image BLOB)";
		      stmt.executeUpdate(sql);
		      
		    
		      System.out.println("Created table in given database...");
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}//end main
		
	   public static  void TimelineCreator(){
		   Connection conn = null;
		   Statement stmt = null;
		   
		    
		   
		   try{
		      //STEP 2: Register JDBC driver
			   Class.forName("org.sqlite.JDBC");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		  	conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		      
		      
		      System.out.println("Connected database successfully...");
		      
//		      "INSERT INTO TimeLine (Title, Description, StartDate, EndDate) VALUES (?,?,?,?)  ";
		            
		      
		      //STEP 4: Execute a query
		      System.out.println("Creating table in given database...");
		      stmt = conn.createStatement();
		      
		      String sql = "CREATE TABLE TimeLine " +
		                   "(Title TEXT, " + 
		                   " Description TEXT, " +  
		                   " StartDate TEXT, " +
		                   " EndDate TEXT) ";
		                   
		      stmt.executeUpdate(sql);
		      
		      
		      
		      System.out.println("Created table in given database...");
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}//end main
		//end JDBCExample
		
		



}
