package dao;
import beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class AstaApertaDao {
	private Connection con;


	public AstaApertaDao(Connection connection) {
		this.con = connection;
	}


	public List<AstaAperta> getAllAste(){
		ResultSet returnListeQuery = null;
		List<AstaAperta> listaAste = new ArrayList<AstaAperta>();
		String queryAstaAperta = "SELECT * FROM asta_aperta";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(queryAstaAperta);
			returnListeQuery = preparedStatement.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while(returnListeQuery.next()) {
				int asta_id = returnListeQuery.getInt("asta_id");
				String desc = returnListeQuery.getString("descrizione");
				int id_utente = returnListeQuery.getInt("id_utente");
				String img = returnListeQuery.getString("immagine");
				String scadenza = returnListeQuery.getString("scandenza");
				float  minimo_rialzo = returnListeQuery.getFloat("minimo_rialzo");
				float prezzo_iniziale = returnListeQuery.getFloat("prezzo_iniziale");
				String dataapertura = returnListeQuery.getString("dataApertura");
				AstaAperta a = new AstaAperta(asta_id, desc, img, scadenza, minimo_rialzo, prezzo_iniziale, dataapertura,id_utente);
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
	}

	//metto commit nella query dopo punto e virgola

	public int creaAsta (String nome , String descrizione, String immagine, float prezzo_iniziale, float minimo_rialzo ) throws SQLException {
		String query = "INSERT into asta_aperta (nome, descrizione, immagine, prezzo_iniziale, minimo_rialzo)   VALUES(?, ?, ?, ?,?) ; COMMIT";


		/**
		 * 
		 * 
		 * 
		 */


		int code = 0;
		PreparedStatement pstatement = null;
		try {
			pstatement = con.prepareStatement(query);
			pstatement.setString(1, nome);
			pstatement.setString(2, descrizione);
			pstatement.setString(2, immagine);
			pstatement.setFloat(4, prezzo_iniziale);
			pstatement.setFloat(4, minimo_rialzo);

			code = pstatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			try {
				if (pstatement != null) {
					pstatement.close();
				}
			} catch (Exception e1) {

			}
		}
		return code;
	}

	public AstaAperta getAstaApertaById(int astaId) {
		ResultSet astaById = null;
		String query = "SELECT * FROM asta_aperta where id_asta = ?";
		PreparedStatement p = null;
		AstaAperta asta = null;

		try {
			p = con.prepareStatement(query);
			p.setInt(1, astaId);
			astaById = p.executeQuery();
			while(astaById.next()) {
				asta = new AstaAperta(astaById.getInt("asta_id"), astaById.getString("descrizione"), astaById.getString("immagine"), astaById.getString("scadenza"), astaById.getFloat("minimo_rialzo"), astaById.getFloat("prezzo_minimo"), astaById.getString("dataApertura"),astaById.getInt("utente_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				p.close();
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
		}
		return asta;

	}

	public List<AstaAperta> astaPerKeyword(String keyword){

		ResultSet returnListeQuery = null;
		List<AstaAperta> listaAste = new ArrayList<AstaAperta>();
		String queryAstaAperta = "SELECT * FROM asta_aperta WHERE descrizione LIKE ? OR titolo LIKE ?";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(queryAstaAperta);
			preparedStatement.setString(1, keyword);
			preparedStatement.setString(2, keyword);
			returnListeQuery = preparedStatement.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while(returnListeQuery.next()) {
				int asta_id = returnListeQuery.getInt("asta_id");
				String desc = returnListeQuery.getString("descrizione");
				String img = returnListeQuery.getString("immagine");
				String scadenza = returnListeQuery.getString("scandenza");
				float  minimo_rialzo = returnListeQuery.getFloat("minimo_rialzo");
				float prezzo_iniziale = returnListeQuery.getFloat("prezzo_iniziale");
				String dataapertura = returnListeQuery.getString("dataApertura");
				int id_utente= returnListeQuery.getInt("utente_id");
				AstaAperta a = new AstaAperta(asta_id, desc, img, scadenza, minimo_rialzo, prezzo_iniziale, dataapertura,id_utente);
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
	}

	/**
	 * controllo che l'utente che fa la richiesta di chiusura viene fatto nel controller vedendo che lo user sia lo stesso che ha un asta con quell'id ?
	 * @param asta_id
	 */

	public void closeAsta(int asta_id) {
		PreparedStatement p = null;
		ResultSet retQueryChiusa = null;
		String queryAstaAperta = "SELECT * FROM asta_aperta WHERE asta_id = ? ";
		ResultSet queryOffSet = null;

		try {
			p = con.prepareStatement(queryAstaAperta);
			p.setString(1, Integer.toString(asta_id));
			retQueryChiusa = p.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		/**
		 * queryOfferta contiene l'importo e l'aggiudicatario che ha vinto l'asta con asta_id = asta_id;
		 */
		try {
			String queryOfferta = "SELECT importo,offerente FROM dettaglio_asta as d WHERE id_asta = ? AND WHERE importo >= (SELECT MAX(IMPORTO) "
					+ " FROM dettaglio_asta WHERE ? = d.id_asta);COMMIT;";
			p = con.prepareStatement(queryOfferta);
			p.setString(1,Integer.toString(asta_id));
			p.setString(2,Integer.toString(asta_id));
			queryOffSet = p.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String queryPerUtente = "SELECT indirizzo_spedizione FROM utente where id_utente = ?";
			p = con.prepareStatement(queryPerUtente);
			ResultSet indirizzoSpedizione = p.executeQuery();



			/**
			 * scrivo nel db la nuova asta chiusa con tutti i paramentri che mi servono!!
			 */

			if(retQueryChiusa.next()) {
				String addToAstaChiusa = "INSERT INTO asta_chiusa VALUES(?,?,?,?,?,?,?,?,?)";
				if(indirizzoSpedizione.next() && retQueryChiusa.next() && queryOffSet.next()) {
					String spedizione = indirizzoSpedizione.getString("indirizzo_spedizione");
					p.setInt(1, retQueryChiusa.getInt("asta_id"));
					p.setString(2, retQueryChiusa.getString("descrizione"));
					p.setString(3, retQueryChiusa.getString("immagine"));
					p.setString(4, retQueryChiusa.getString("scadenza"));
					p.setInt(5, retQueryChiusa.getInt("id_utente"));
					p.setString(6, queryOffSet.getString("offerente"));
					p.setFloat(7, queryOffSet.getFloat("importo"));
					p.setString(8, spedizione);
					p.setString(9, retQueryChiusa.getString("data_apertura"));
				}
				p.executeUpdate(addToAstaChiusa);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}