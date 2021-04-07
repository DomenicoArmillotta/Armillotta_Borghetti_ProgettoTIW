package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.AstaChiusa;

public class AstaChiusaDao {
private Connection con;
	
	public AstaChiusaDao(Connection connection) {
		this.con = connection;
	}
	
	public List<AstaChiusa> getAllAste(){
		ResultSet returnListeQuery = null;
		List<AstaChiusa> listaAste = new ArrayList<AstaChiusa>();
		String queryAstaChiusa = "SELECT * FROM asta_chiusa";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(queryAstaChiusa);
			returnListeQuery = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(returnListeQuery.next()) {
				String nome = returnListeQuery.getString("nome");
				int asta_id = returnListeQuery.getInt("asta_id");
				String desc = returnListeQuery.getString("descrizione");
				String img = returnListeQuery.getString("immagine");
				String aggiudicatario = returnListeQuery.getString("aggiudicatario");
				float  prezzo = returnListeQuery.getFloat("prezzo");
				String spedizione = returnListeQuery.getString("spedizione");
				String dataApertura = returnListeQuery.getString("dataApertura");
				int id_utente = returnListeQuery.getInt("id_utente");
				AstaChiusa a = new AstaChiusa(nome,asta_id ,desc, img, aggiudicatario, spedizione, prezzo, dataApertura, id_utente);
				listaAste.add(a);
				a = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				returnListeQuery.close();
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
		
		return listaAste;
	} // fare commit quando faccio insert e alter

}
