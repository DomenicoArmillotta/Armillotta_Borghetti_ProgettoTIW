package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import beans.AstaAperta;

// controllo che il valore sia correttamnte parsato deve essere fatto nel controller

//controllare che l'offerta sia magiore di quella precedente  

public class OffertaDao {

	private Connection con;

	public OffertaDao(Connection con) {
		this.con = con;
	}

	public int createOffert(int offerente, String data_offerta, float importo, int id_asta) {
		//
		int retcode = 0;
		String query = "INSERT into dettaglio_asta (offerente,data_offerta,importo,id_asta) VALUES(?,?,?,?); COMMIT";
		PreparedStatement p = null;
		try {
			p = con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			p.setInt(1, offerente);
			p.setString(2, data_offerta);
			p.setFloat(3, importo);
			p.setInt(4, id_asta);

			retcode = p.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				if (p != null) {
					p.close();
				}
			} catch (Exception e1) {

			}
		}

		return retcode;
	}
	
	public List<AstaAperta> findOffertByAstaId(int asta_id){
		PreparedStatement p = null;
		List<AstaAperta> asta = new ArrayList<AstaAperta>();
		String query = "SELECT * FROM dettaglio_asta WHERE dettaglio_asta.id_asta = ?";
		ResultSet rs = null;
		try {
			p = con.prepareStatement(query);
			p.setInt(1, asta_id);
			
			rs = p.executeQuery();
			while(rs.next()) {
				AstaAperta a = new AstaAperta(rs.getInt("asta_id"), rs.getString("descrizione"), rs.getString("img"), rs.getString("scadenza"),rs.getFloat("minimo_rialzo"),rs.getFloat("prezzo_iniziale"),rs.getString("dataApertura"),rs.getInt("utente_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			p.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return asta;
	}
	
}