package beans;

public abstract class Asta {
	private int asta_id;
	private String descrizione;
	private String img;
	private String scadenza; //scadenza formato:   ggmmaaaa:hhmm	
	private int id_utente;
	
	public Asta(int asta_id, String descrizione, String img, String scadenza, int id_utente) {
		super();
		this.id_utente = id_utente;
		this.asta_id = asta_id;
		this.descrizione = descrizione;
		this.img = img;
		this.scadenza = scadenza;
	}
	
	
	public int getAsta_id() {
		return asta_id;
	}


	public void setAsta_id(int asta_id) {
		this.asta_id = asta_id;
	}


	public int getId_utente() {
		return id_utente;
	}



	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}



	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getScadenza() {
		return scadenza;
	}
	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}
	
	
}
