package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import beans.AstaAperta;
import beans.Offerta;

// controllo che il valore sia correttamnte parsato deve essere fatto nel controller

//controllare che l'offerta sia magiore di quella precedente  

public class OffertaDao {

	private Connection con;

	public OffertaDao(Connection con) {
		this.con = con;
		
		
	}
	
	public List<Offerta> getAllOfferteByAstaId(int asta_id) {
		String query = "SELECT * from asta.offerta WHERE id_asta = ?";
		PreparedStatement p = null;
		ResultSet result = null;
		List<Offerta> offList = new ArrayList<Offerta>();
		
		try {
			p = con.prepareStatement(query);
			p.setInt(1, asta_id);
			result = p.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
		}
	
		try {
			while(result.next()) {
				String offerente = result.getString("offerente");
				Date data_offerta = result.getTimestamp("data_offerta");
				float importo = result.getFloat("importo");
				Offerta o = new Offerta(offerente,data_offerta,importo,asta_id);
				offList.add(o);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(p!=null)
				try {
					p.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(result != null) {
				try {
					result.close();	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		 	
		return offList;
		
	}
	
	public void createNewOffert(int asta_id,String new_offerta, String username, Date time_of_offer) {
		String query = "INSERT INTO offerta VALUES(?,?,?,?);";
		// da rifattorizzare con pstatement
		
		PreparedStatement p = null;
		Timestamp d = new java.sql.Timestamp(time_of_offer.getTime());
		try {
			p = con.prepareStatement(query);
			p.setInt(4, asta_id);
			p.setFloat(3,Float.parseFloat(new_offerta) );
			p.setString(1,username);
			p.setTimestamp(2, d);
			p.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if(p!=null)
				try {
					p.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}
		
		
	}
	
	public void updateMaxOffertaInAstaAperta(float new_max_offerta,int id_asta) {
		String query = "UPDATE asta_aperta SET max_offerta = ? WHERE asta_id = ?";
		PreparedStatement p = null;
		try {
			p =con.prepareStatement(query);
			p.setFloat(1 , new_max_offerta);
			p.setInt(2, id_asta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		try {
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if(p!=null)
				try {
					p.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}
		
	}
	
	public List<Offerta> getAllListOfferteMax (){
		ResultSet returnListeQuery = null;
		List<Offerta> listaOfferte = new ArrayList<Offerta>();
		String query = "SELECT MAX(importo),id_asta FROM asta.offerta GROUP BY id_asta";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(query);
			returnListeQuery = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(returnListeQuery.next()) {
				
			int id_asta = returnListeQuery.getInt("id_asta");
				float importo = returnListeQuery.getFloat("MAX(importo)");
			
				Offerta a = new Offerta(id_asta,importo);
				listaOfferte.add(a);
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
		
		
		return listaOfferte;
	
	}
		
}