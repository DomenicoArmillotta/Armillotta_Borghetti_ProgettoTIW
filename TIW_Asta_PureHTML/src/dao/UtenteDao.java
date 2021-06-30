package dao;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Utente;



public class UtenteDao {
	private Connection con;

	public UtenteDao(Connection con) {
		this.con = con;
	}
	
	public Utente getUtente(String username) throws SQLException {
		PreparedStatement preparedStatement = null;
		Utente utente = null;
		String query = "SELECT * FROM utente WHERE id_utente = ?";
		
		ResultSet returnQuery = null;
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, username);
			returnQuery = preparedStatement.executeQuery();
		
			if(returnQuery == null)
				return null;
			else {
				try {
					while(returnQuery.next()) {
						utente = new Utente(returnQuery.getString("id_utente"), returnQuery.getString("password").replace("\n", ""), returnQuery.getString("indirizzo_spedizione"));
					}
				} catch (SQLException e) {
					throw e;
				}
			}
		}
			
			finally {
				
				try {
					if(returnQuery != null)
						returnQuery.close();
				} catch (SQLException e) {
				throw e;
				}
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw e;
				}
			}
			
			return utente;
		}
		
	}	

