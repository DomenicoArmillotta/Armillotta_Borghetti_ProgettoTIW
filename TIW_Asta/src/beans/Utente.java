package beans;

public class Utente {
	private int id_utente;
	private String username;
	private String password;
	private String indirizzo_spedizione;
	
	
	
	
	public Utente(int id_utente, String username, String password, String indirizzo_spedizione) {
		super();
		this.id_utente = id_utente;
		this.username = username;
		this.password = password;
		this.indirizzo_spedizione = indirizzo_spedizione;
	}
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
