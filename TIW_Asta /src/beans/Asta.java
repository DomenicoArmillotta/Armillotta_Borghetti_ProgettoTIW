package beans;

import java.util.Date;

public abstract class Asta {
	private int asta_id;
	private String descrizione;
	private String img;
	private Date scadenza; //scadenza formato:   ggmmaaaa:hhmm	
	private String id_utente;
	private String titolo;
	
	public Asta(int asta_id, String descrizione, String img, Date scadenza, String id_utente,String titolo) {
		super();
		this.id_utente = id_utente;
		this.asta_id = asta_id;
		this.descrizione = descrizione;
		this.img = img;
		this.scadenza = scadenza;
		this.titolo = titolo;
	}
	
	
	public String getTitolo() {
		return titolo;
	}


	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public int getAsta_id() {
		return asta_id;
	}


	public void setAsta_id(int asta_id) {
		this.asta_id = asta_id;
	}


	public String getId_utente() {
		return id_utente;
	}



	public void setId_utente(String id_utente) {
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
	public Date getScadenza() {
		return scadenza;
	}
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	
	
}
