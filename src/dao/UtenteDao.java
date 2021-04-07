package dao;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.AstaAperta;


public class UtenteDao {
	private Connection con;

	public UtenteDao(Connection con) {
		this.con = con;
	}
	
	public String getPasswordById(int id_utente) {
		PreparedStatement preparedStatement = null;
		String password = new String();
		String query = "SELECT * FROM utente WHERE utente.id_utente = "+ id_utente;
		ResultSet returnQuery = null;
		try {
			preparedStatement = con.prepareStatement(query);
			returnQuery = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(returnQuery.next()) {
				password= returnQuery.getString("password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				returnQuery.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return password;
		
	}
	

}
