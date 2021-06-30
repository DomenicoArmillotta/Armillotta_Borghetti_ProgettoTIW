package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.AstaChiusa;

public class AstaChiusaDao {
	private Connection con;

	public AstaChiusaDao(Connection connection) {
		this.con = connection;
	}

	public List<AstaChiusa> getAllAste() throws SQLException {
		ResultSet returnListeQuery = null;
		List<AstaChiusa> listaAste = new ArrayList<AstaChiusa>();
		String queryAstaChiusa = "SELECT * from asta_chiusa";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(queryAstaChiusa);
			returnListeQuery = preparedStatement.executeQuery();

			while (returnListeQuery.next()) {
				int asta_id = returnListeQuery.getInt("asta_id");
				String titolo = returnListeQuery.getString("titolo");
				String descrizione = returnListeQuery.getString("descrizione");
				String immagine = returnListeQuery.getString("immagine");
				String aggiudicatario = returnListeQuery.getString("aggiudicatario");
				float prezzo_vendita = returnListeQuery.getFloat("prezzo");
				String spedizione = returnListeQuery.getString("spedizione");
				Date data_apertura = returnListeQuery.getTimestamp("data_apertura");
				String id_utente = returnListeQuery.getString("id_utente");
				AstaChiusa a = new AstaChiusa(asta_id, id_utente, null, titolo, descrizione, immagine, aggiudicatario,
						prezzo_vendita, spedizione, data_apertura);
				listaAste.add(a);
				a = null;
			}
		}

		finally {
			try {
				returnListeQuery.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw e;
			}
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}

		return listaAste;
	} // fare commit quando faccio insert e alter

	public List<AstaChiusa> getAllAsteByUser(String id_utente) throws SQLException {
		ResultSet returnListeQuery = null;
		List<AstaChiusa> listaAste = new ArrayList<AstaChiusa>();
		String queryAstaChiusa = "SELECT * from asta_chiusa WHERE id_utente = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(queryAstaChiusa);
			preparedStatement.setString(1, id_utente);
			returnListeQuery = preparedStatement.executeQuery();

			while (returnListeQuery.next()) {
				int asta_id = returnListeQuery.getInt("asta_id");
				String titolo = returnListeQuery.getString("titolo");
				String descrizione = returnListeQuery.getString("descrizione");
				String immagine = returnListeQuery.getString("immagine");
				String aggiudicatario = returnListeQuery.getString("aggiudicatario");
				float prezzo_vendita = returnListeQuery.getFloat("prezzo");
				String spedizione = returnListeQuery.getString("spedizione");
				// Date data_apertura = returnListeQuery.getDate("ora_apertura");
				AstaChiusa a = new AstaChiusa(asta_id, id_utente, null, titolo, descrizione, immagine, aggiudicatario,
						prezzo_vendita, spedizione, returnListeQuery.getTimestamp("data_apertura"));
				listaAste.add(a);
				a = null;
			}
		} finally {
			try {
				returnListeQuery.close();
			} catch (SQLException e) {
				throw e;
			}
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				throw e;
			}
		}

		return listaAste;
	}

	public AstaChiusa getAstaById(int asta_id) throws SQLException {
		AstaChiusa asta = null;
		String query = "SELECT * from asta_chiusa WHERE asta_id = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, asta_id);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					String titolo = result.getString("titolo");
					String descrizione = result.getString("descrizione");
					String immagine = result.getString("immagine");
					String aggiudicatario = result.getString("aggiudicatario");
					float prezzo_vendita = result.getFloat("prezzo");
					String spedizione = result.getString("spedizione");
					Date data_apertura = result.getTimestamp("data_apertura");
					String id_utente = result.getString("id_utente");
					asta = new AstaChiusa(asta_id, id_utente, null, titolo, descrizione, immagine, aggiudicatario,
							prezzo_vendita, spedizione, data_apertura);

				}
			}
		}

		return asta;
	}

}