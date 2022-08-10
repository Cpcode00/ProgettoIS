package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	/*Singleton DBManager*/
	private static DBManager instance=null;
	
	//Costruttore privato
	private DBManager() throws SQLException{
	}
	
	public static DBManager Instance() {
		if (instance == null) {
			try {
				Class.forName("org.h2.Driver");
				instance = new DBManager();
			} catch (ClassNotFoundException e) {
				System.err.println("Errore");
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				this.connection = DriverManager.getConnection("jdbc:h2:"+dbPath,"admin","");
			}
		} catch (SQLException e) {
			System.err.println("Errore");
		}
		return connection;
	}
	
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Errore");
		}
	}
	
	protected Connection connection;
	protected final String dbPath = "./test";

}
