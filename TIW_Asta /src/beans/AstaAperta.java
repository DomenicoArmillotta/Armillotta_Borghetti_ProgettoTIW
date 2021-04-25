package beans;

import java.util.Date;

public class AstaAperta extends Asta{
	private float minimo_rialzo;
	private float prezzo_iniziale;
	private Date dataApertura;
	private float max_offerta;
	
	public AstaAperta(int asta_id, String descrizione, String immagine, Date scadenza, float minimo_rialzo,float prezzo_iniziale,Date dataApertura ,String id_utente,String titolo,float max_offerta) {
		super(asta_id, descrizione, immagine , scadenza , id_utente,titolo);
		this.minimo_rialzo = minimo_rialzo;
		this.prezzo_iniziale = prezzo_iniziale;
		this.dataApertura = dataApertura;
		this.max_offerta = max_offerta;
	}

	public float getMinimo_rialzo() {
		return minimo_rialzo;
	}

	public void setMinimo_rialzo(float minimo_rialzo) {
		this.minimo_rialzo = minimo_rialzo;
	}

	public float getPrezzo_iniziale() {
		return prezzo_iniziale;
	}

	public void setPrezzo_iniziale(float prezzo_iniziale) {
		this.prezzo_iniziale = prezzo_iniziale;
	}

	public Date getDataApertura() {
		return dataApertura;
	}

	public float getMax_offerta() {
		return max_offerta;
	}

	public void setMax_offerta(float max_offerta) {
		this.max_offerta = max_offerta;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
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
	public Date getScadenza() {
		// TODO Auto-generated method stub
		return super.getScadenza();
	}

	@Override
	public void setScadenza(Date scadenza) {
		// TODO Auto-generated method stub
		super.setScadenza(scadenza);
	}

	
	
	
	
	
}
