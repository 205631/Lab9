package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnect {
	
	private static final String jdbcURL = "jdbc:mysql://localhost/porto?user=root" ;
	
	private static ComboPooledDataSource dataSource = null ;
	
	public static Connection getConnection() {
		
		Connection conn;
		try {
			if(dataSource==null){
				dataSource=new ComboPooledDataSource();
				dataSource.setJdbcUrl(jdbcURL);
			}
			return dataSource.getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nella connessione", e) ;
		}
	}

}