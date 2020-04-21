package POSTGRESSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TestConexion {

	public Connection conect;

	
	private final String serverName = "localhost";
	private final String portNumber = "5432";
	private final String databaseName = "BDAuth";
	private final String userName = "postgres";
	private final String password = "1234";
	private final String timeout = "30";
	
//	query para saber puerto
//	SELECT * FROM pg_settings where name like '%port%'
	
	
	// Informs the driver to use server a side-cursor,
	// which permits more than one active statement
	// on a connection.
	private final String selectMethod = "Direct";
	private final String statement = "select * from compania;";
	
	private String getConnectionUrl() {
		  return
				  "jdbc:postgresql://"+serverName+":"+portNumber+"/"+databaseName ;
	}

	public Connection getConnection() throws ClassNotFoundException {

		try {
			Class.forName("org.postgresql.Driver");
			Properties props = new Properties();
			props.setProperty("user",userName);
			props.setProperty("password",password);
			props.setProperty("loginTimeout ",timeout);
			conect = DriverManager.getConnection(getConnectionUrl(),props);
			
			if (conect != null)				
				System.out.println("Conectado.");
			
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return conect;
	}
	
	public void displayDbProperties() {
		  java.sql.DatabaseMetaData dm = null;
		  java.sql.ResultSet result = null;
		  try {
			  conect = this.getConnection();
		   if (conect != null) {
		    dm = conect.getMetaData();
		    System.out.println("Driver Information");
		    System.out.println("\tDriver Name: " + dm.getDriverName());
		    System.out.println("\tDriver Version: " + dm.getDriverVersion());
		    System.out.println("\nDatabase Information ");
		    System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
		    System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());

		    Statement select = conect.createStatement();
		    result = select.executeQuery(statement);

		    while (result.next()) {
		     System.out.println("Nombre: " + result.getString(1) + "\n");
		     System.out.println("edad: " + result.getString(2) + "\n");
		     System.out.println("direccion: " + result.getString(3) + "\n");
		    }
		    result.close();
		    result = null;
		    closeConnection();
		   } else
		    System.out.println("Error: No active Connection");
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  dm = null;
		 }

		 private void closeConnection() {
		  try {
		   if (conect != null)
			   conect.close();
		   conect = null;
		  } catch (Exception e) {
		   e.printStackTrace();	
		  }
		 }

		 public static void main(String[] args) throws Exception {
		  TestConexion myDbTest = new TestConexion();
		  myDbTest.displayDbProperties();
		 }
	
	
	

}
