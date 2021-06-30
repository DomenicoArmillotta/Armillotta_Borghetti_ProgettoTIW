package beans;

import java.util.Date;

public class Offerta {
	
	private String offerente;
	private Date data_offerta;
	private float importo;
	private int id_asta;
	
	public Offerta(String offerente, Date data_offerta, float importo, int id_asta) {
		this.offerente = offerente;
		this.data_offerta = data_offerta;
		this.importo = importo;
		this.id_asta = id_asta;
	}
	
	public Offerta(int id_asta , float importo) {
		super();
		this.id_asta = id_asta;
		this.importo = importo;
	}

	public String getOfferente() {
		return offerente;
	}
	public void setOfferente(String offerente) {
		this.offerente = offerente;
	}
	public Date getData_offerta() {
		return data_offerta;
	}
	public void setData_offerta(Date data_offerta) {
		this.data_offerta = data_offerta;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	public int getId_asta() {
		return id_asta;
	}
	public void setId_asta(int id_asta) {
		this.id_asta = id_asta;
	}
	
	
}
