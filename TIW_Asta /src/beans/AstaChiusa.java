package beans;

import java.util.Date;

public class AstaChiusa extends Asta{
	private String aggiudicatario;
	private float prezzo_vendita;
	private String spedizione;
	private Date data_apertura;
	
	

	public AstaChiusa( int asta_id,String id_utente, Date scadenza, String titolo,String descrizione, String immagine,String aggiudicatario, float prezzo_vendita,String spedizione , Date data_apertura) {
		super(asta_id,descrizione, immagine,  scadenza, id_utente ,titolo);
		this.aggiudicatario = aggiudicatario;
		this.prezzo_vendita = prezzo_vendita;
		this.spedizione = spedizione;
		this.setData_apertura(data_apertura);
	}
	
	public String getSpedizione() {
		return spedizione;
	}


	public void setSpedizione(String spedizione) {
		this.spedizione = spedizione;
	}


	@Override
	public int getAsta_id() {
		// TODO Auto-generated method stub
		return super.getAsta_id();
	}


	@Override
	public void setAsta_id(int asta_id) {
		// TODO Auto-generated method stub
		super.setAsta_id(asta_id);
	}


	@Override
	public String getId_utente() {
		// TODO Auto-generated method stub
		return super.getId_utente();
	}


	@Override
	public void setId_utente(String id_utente) {
		// TODO Auto-generated method stub
		super.setId_utente(id_utente);
	}


	@Override
	public String getDescrizione() {
		// TODO Auto-generated method stub
		return super.getDescrizione();
	}


	@Override
	public void setDescrizione(String descrizione) {
		// TODO Auto-generated method stub
		super.setDescrizione(descrizione);
	}

	public String getAggiudicatario() {
		return aggiudicatario;
	}

	public void setAggiudicatario(String aggiudicatario) {
		this.aggiudicatario = aggiudicatario;
	}


	public float getPrezzo_vendita() {
		return prezzo_vendita;
	}


	public void setPrezzo_vendita(float prezzo_vendita) {
		this.prezzo_vendita = prezzo_vendita;
	}


	public Date getData_apertura() {
		return data_apertura;
	}


	public void setData_apertura(Date data_apertura) {
		this.data_apertura = data_apertura;
	}

}