package beans;

public class AstaChiusa extends Asta{
	private String aggiudicatario;
	private float prezzo_vendita;
	private String spedizione;
	private String data_apertura;
	
	
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
	public int getId_utente() {
		// TODO Auto-generated method stub
		return super.getId_utente();
	}


	@Override
	public void setId_utente(int id_utente) {
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


	@Override
	public String getImg() {
		// TODO Auto-generated method stub
		return super.getImg();
	}


	@Override
	public void setImg(String img) {
		// TODO Auto-generated method stub
		super.setImg(img);
	}


	@Override
	public String getScadenza() {
		// TODO Auto-generated method stub
		return super.getScadenza();
	}


	public AstaChiusa(String data_apertura , int asta_id, String descrizione, String immagine, String scadenza,String aggiudicatario, float prezzo_vendita,String spedizione ,int id_utente) {
		super(asta_id, descrizione, immagine,scadenza ,id_utente);
		this.aggiudicatario = aggiudicatario;
		this.prezzo_vendita = prezzo_vendita;
		this.spedizione = spedizione;
		this.setData_apertura(data_apertura);
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


	public String getData_apertura() {
		return data_apertura;
	}


	public void setData_apertura(String data_apertura) {
		this.data_apertura = data_apertura;
	}

}
