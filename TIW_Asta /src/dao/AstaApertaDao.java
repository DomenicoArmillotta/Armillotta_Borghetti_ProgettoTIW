package dao;
	import beans.*;
	
	
	
	import java.sql.Connection;
	import java.util.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
	
	import java.util.List;
	
	
	
	
	public class AstaApertaDao {
		private Connection con;
	
	
		public AstaApertaDao(Connection connection) {
			this.con = connection;
		}
	
	
		public List<AstaAperta> getAllAste(){
			ResultSet returnListeQuery = null;
			List<AstaAperta> listaAste = new ArrayList<AstaAperta>();
			String queryAstaAperta = "SELECT * from asta_aperta";
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
					String id_utente = returnListeQuery.getString("id_utente");
					String desc = returnListeQuery.getString("descrizione");
					String immagine = returnListeQuery.getString("immagine");
					float prezzo_iniziale = returnListeQuery.getFloat("prezzo_iniziale");
					float  minimo_rialzo = returnListeQuery.getFloat("minimo_rialzo");
					Date dataapertura = returnListeQuery.getDate("data_apertura");
					Date scadenza = returnListeQuery.getDate("scadenza");
					String titolo = returnListeQuery.getString("titolo");
					float max_offera = returnListeQuery.getFloat("max_offerta");
					AstaAperta a = new AstaAperta(asta_id,desc,immagine,scadenza,minimo_rialzo,prezzo_iniziale, dataapertura,id_utente,titolo,max_offera);
	
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
	
		public void creaAsta (String titolo , String descrizione,float prezzo_iniziale, float minimo_rialzo , String id_utente , Date data_apertura , Date data_scadenza) {
			
			String query = "INSERT into asta_aperta (id_utente, descrizione, prezzo_iniziale, minimo_rialzo, data_apertura, scadenza ,titolo)   VALUES(?,?,?,?,?,?,?)";
			SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp d1=null;
			try {
				Date parsedDate1 = dateFormat.parse(dateFormat.format(data_apertura));
				 d1 = new java.sql.Timestamp(parsedDate1.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Timestamp d2=null;
			try {
				Date parsedDate2 = dateFormat.parse(dateFormat.format(data_scadenza));
				 d2 = new java.sql.Timestamp(parsedDate2.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				try {
					
					PreparedStatement pstatement = con.prepareStatement(query);
					pstatement.setString(2, descrizione);
					pstatement.setFloat(3, prezzo_iniziale);
					pstatement.setFloat(4, minimo_rialzo);
					pstatement.setString(1, id_utente);
					pstatement.setTimestamp(5 , d1);
					pstatement.setTimestamp(6 , d2);
					pstatement.setString(7, titolo);
					pstatement.executeUpdate();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}
	
		public AstaAperta getAstaApertaById(int astaId) {
			ResultSet astaById = null;
			String query = "SELECT * FROM asta_aperta where asta_id = ?";
			PreparedStatement p = null;
			AstaAperta asta = null;
	
			try {
				p = con.prepareStatement(query);
				p.setInt(1, astaId);
				astaById = p.executeQuery();
				while(astaById.next()) {
					asta = new AstaAperta(astaById.getInt("asta_id"), astaById.getString("descrizione"),astaById.getString("immagine"),astaById.getTimestamp("scadenza") ,astaById.getFloat("minimo_rialzo"), astaById.getFloat("prezzo_iniziale"),  astaById.getTimestamp("data_apertura"),astaById.getString("id_utente"), astaById.getString("titolo"),  astaById.getFloat("max_offerta"));
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
		
		
		public List<AstaAperta> getAllAsteByUser (String id_utente){
			ResultSet returnListeQuery = null;
			List<AstaAperta> listaAste = new ArrayList<AstaAperta>();
			String queryAstaChiusa = "SELECT * from asta_aperta WHERE id_utente = ?";
			PreparedStatement preparedStatement = null;
			
			try {
				preparedStatement = con.prepareStatement(queryAstaChiusa);
				preparedStatement.setString(1,id_utente);
				returnListeQuery = preparedStatement.executeQuery();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				while(returnListeQuery.next()) {
					int asta_id = returnListeQuery.getInt("asta_id");
					String titolo = returnListeQuery.getString("titolo");
					String desc = returnListeQuery.getString("descrizione");
					String immagine = returnListeQuery.getString("immagine");
					float prezzo_iniziale = returnListeQuery.getFloat("prezzo_iniziale");
					float  minimo_rialzo = returnListeQuery.getFloat("minimo_rialzo");
					Date dataapertura = returnListeQuery.getDate("data_apertura");
					float  max_offerta = returnListeQuery.getFloat("max_offerta");
					Date scadenza = returnListeQuery.getTimestamp("scadenza");

					AstaAperta a = new AstaAperta(asta_id,desc,immagine,scadenza,minimo_rialzo,prezzo_iniziale, dataapertura,id_utente,titolo,max_offerta);
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
	
		public List<AstaAperta> astaPerKeyword(String keyword){
	
			ResultSet returnListeQuery = null;
			List<AstaAperta> listaAste = new ArrayList<AstaAperta>();
			String queryAstaAperta = "SELECT * FROM asta_aperta WHERE descrizione LIKE ? OR titolo LIKE ?;";
	
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = con.prepareStatement(queryAstaAperta);
				preparedStatement.setString(1,"%"+keyword+"%");
				preparedStatement.setString(2,"%"+keyword+"%");
	
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
					Date scadenza = returnListeQuery.getTimestamp("scadenza");
					//scadenza = returnListeQuery.getTime("scadenza");
					float  minimo_rialzo = returnListeQuery.getFloat("minimo_rialzo");
					float prezzo_iniziale = returnListeQuery.getFloat("prezzo_iniziale");
					Date dataapertura = returnListeQuery.getTimestamp("data_apertura");
					String id_utente= returnListeQuery.getString("id_utente");
					String titolo = returnListeQuery.getString("titolo");
					float max_offerta = returnListeQuery.getFloat("max_offerta");
					AstaAperta a = new AstaAperta(asta_id,desc,img,scadenza,minimo_rialzo,prezzo_iniziale, dataapertura,id_utente,titolo,max_offerta);
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
	
	
		public void closeAsta(int asta_id) throws SQLException, ParseException {
			// query che salva la riga Asta Aperta da trasformare
			SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			Timestamp d1=null;
			con.setAutoCommit(false);
			PreparedStatement pstatement = null;
			PreparedStatement pstatement2 = null;
			ResultSet retQueryChiusa = null;
			ResultSet queryOffSet = null;
			//salva l'asta che voglio chiudere
			AstaAperta astaAperta = getAstaApertaById(asta_id);
			
			//elimina asta aperta da asta_aperta
			String query = "DELETE FROM asta.asta_aperta WHERE asta_id = ?";
			//creare un asta chiusa in asta_chiusa
			String query2 = "INSERT into asta.asta_chiusa (id_utente,descrizione,immagine,prezzo,data_apertura,titolo) VALUES (?,?,?,?,?,?)";
			
			try {
				pstatement = con.prepareStatement(query);
				pstatement.setInt(1, asta_id);
				pstatement.executeUpdate();
				
				
				
				pstatement2 = con.prepareStatement(query2);
				pstatement2.setString(1,astaAperta.getId_utente());
				pstatement2.setString(6, astaAperta.getTitolo());
				pstatement2.setString(2,astaAperta.getDescrizione());
				pstatement2.setString(3,astaAperta.getImg());
				pstatement2.setFloat(4,astaAperta.getMax_offerta());
				Date parsedDate1 = dateFormat.parse(dateFormat.format(astaAperta.getDataApertura()));
				 d1 = new java.sql.Timestamp(parsedDate1.getTime());
				 pstatement2.setTimestamp(5, d1);
				 pstatement2.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con.rollback();
			}finally {
				con.setAutoCommit(true);
				if (pstatement != null) {
					try {
						pstatement.close();
					} catch (Exception e) {
						throw e;
					}
				}
				if (pstatement2 != null) {
					try {
						pstatement2.close();
					} catch (Exception e) {
						throw e;
					}
				}
			}
			
		}
	
		/**
		 * controllo che l'utente che fa la richiesta di chiusura viene fatto nel controller vedendo che lo user sia lo stesso che ha un asta con quell'id ?
		 * @param asta_id
		 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		}