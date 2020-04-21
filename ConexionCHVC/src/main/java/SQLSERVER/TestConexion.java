package SQLSERVER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConexion {

	public Connection conect;

	
	private final String serverName = "DESKTOP-68APQNQ";
	private final String portNumber = "1433";
	private final String databaseName = "BDFACFYM";
	private final String userName = "sa";
	private final String password = "1234";
	private final String timeout = "30";
	
//	query para saber puerto
//	USE MASTER
//	GO
//	xp_readerrorlog 0, 1, N'Server is listening on'
//	GO
	
	
	// Informs the driver to use server a side-cursor,
	// which permits more than one active statement
	// on a connection.
	private final String selectMethod = "Direct";
	private final String statement = "select * from SIA_Trabajador;";
	
	private String getConnectionUrl() {
		  return
	                "jdbc:sqlserver://"+serverName+":"+portNumber+";"
	                        + "database="+databaseName +";"
	                        + "user="+userName+";"
	                        + "password="+password+";"
	                        + "encrypt=true;"
	                        + "trustServerCertificate=true;"
	                        + "loginTimeout=" + timeout  + ";";
	}

	public Connection getConnection() throws ClassNotFoundException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			conect = DriverManager.getConnection(getConnectionUrl());
			
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
		     System.out.println("Apellido: " + result.getString(2) + "\n");
		     System.out.println("Dni: " + result.getString(3) + "\n");
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
