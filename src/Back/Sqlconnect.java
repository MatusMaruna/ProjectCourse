package Back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sqlconnect {

	

	public static Connection DbConnector() throws SQLException {

		try {
			Connection con = null;
			
		Class.forName("org.sqlite.JDBC");

			con = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
		return null;
	}
}