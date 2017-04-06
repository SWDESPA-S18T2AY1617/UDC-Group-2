package model.storage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ClinicDB {
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;
	private static String DRIVER_NAME;
	private static BufferedReader reader;
	private static Connection connection;
	private static final String DATABASE = "clinic_db";
	private static final String FILE = "src/config/db.config";
	
	
	private static void load () {
		try {
			reader = new BufferedReader(new FileReader(FILE));
			DRIVER_NAME = reader.readLine();
			URL = reader.readLine();
			USERNAME = reader.readLine();
			PASSWORD = reader.readLine();
		} catch (IOException e) {
			System.err.println("Error! " + FILE + " not loaded!");
		}
	}
	
	public static boolean openConnection () {
		try {
			if(connection == null || connection.isClosed()) {
				if(connection == null)
					load();
				
				try {
					Class.forName(DRIVER_NAME);
					connection = DriverManager.getConnection(URL + DATABASE + "?autoReconnect=true&useSSL=false", USERNAME, PASSWORD);
					System.out.println("[" + LocalDateTime.now() + "] Connection to Database successful!");
					return true;
				} catch (Exception ex) {
					System.err.println("[" + LocalDateTime.now() + "] Exception Caught! Unable to connect to " + DATABASE);
					return false;
				}
			} else {
				System.err.println("[" + LocalDateTime.now() + "] Connection already open!");
				return false;
			}
		} catch (Exception ex) {
			System.err.println("[" + LocalDateTime.now() + "] Cannot determine connection!");
			return false;
		}
	}
		
	public static Connection getActiveConnection () {
		return connection;
	}
	
	public static void closeConnection() {
        try {
            System.out.println("[" + LocalDateTime.now() + "] Closed Connection to Database");
            connection.close();
        } catch (SQLException e) {
           System.err.println("[" + LocalDateTime.now() + "] Cannot close connection");
        } catch (NullPointerException n) {
        	System.err.println("[" + LocalDateTime.now() + "] No connection open");
        }
	}

	public static boolean isOpen() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException n) {
			return false;
		}
	}
}