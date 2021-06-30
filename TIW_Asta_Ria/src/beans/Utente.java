package beans;

public class Utente {
	private String id_utente;
	private String password;
	private String indirizzo_spedizione;
	
	public Utente(String id_utente, String password, String indirizzo_spedizione) {
		super();
		this.id_utente = id_utente;
		this.password = password;
		this.indirizzo_spedizione = indirizzo_spedizione;
	}
	
	public Utente() {
		// TODO Auto-generated constructor stub
	}
	public String getId_utente() {
		return id_utente;
	}
	public void setId_utente(String id_utente) {
		this.id_utente = id_utente;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIndirizzo_spedizione() {
		return indirizzo_spedizione;
	}
	public void setIndirizzo_spedizione(String indirizzo_spedizione) {
		this.indirizzo_spedizione = indirizzo_spedizione;
	}

	

}
