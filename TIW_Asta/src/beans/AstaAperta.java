package beans;

public class AstaAperta extends Asta{
	private float minimo_rialzo;
	private float prezzo_iniziale;
	private String dataApertura;
	
	public AstaAperta(int asta_id, String descrizione, String immagine, String scadenza, float minimo_rialzo,float prezzo_iniziale,String dataApertura ,int id_utente) {
		super(asta_id, descrizione, immagine , scadenza , id_utente);
		this.minimo_rialzo = minimo_rialzo;
		this.prezzo_iniziale = prezzo_iniziale;
		this.dataApertura = dataApertura;
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

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	
	
	
	
}
