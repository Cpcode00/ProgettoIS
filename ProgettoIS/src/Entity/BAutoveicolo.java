package Entity;

public class BAutoveicolo extends Biglietto {
	private String targa;
	
	/**COSTRUTTORI*/
	public BAutoveicolo() {
		super();
		targa="";
	}
	
	public BAutoveicolo(String data, String ora) {
		super(data, ora);
	}
	
	public BAutoveicolo(String data, String ora, String targa) {
		super(data, ora);
		this.targa=targa;
	}
	
	public BAutoveicolo(String data, String ora, Corsa c, Impiegato i, String targa) {
		super(data, ora, c, i);
		this.targa=targa;
	}
	
	public BAutoveicolo(BAutoveicolo b) {
		super(b);
		this.targa=b.targa;
	}
	
	public String getTarga() {
		return this.targa;
	}
	
	public void setTarga(String targa) {
		this.targa=targa;
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof BAutoveicolo)) {
			return false;
		} else {
			BAutoveicolo b = (BAutoveicolo)altroOggetto;
			return (super.equals(b) && targa == b.targa);
		}
	}
	
	@Override
	public String toString() {	
		if(this.isValid()){
			return ("ID = " + persistentID + ", data = " + data + ", ora = " + ora + ", targa = " + targa);
		}else{
			System.out.println(this.ora.isEmpty() + " " + this.data.isEmpty() + " " +  (corsaAssociata == null) + " " + (impiegatoAssociato == null) + " " + this.targa.isEmpty());
			return("Parametri biglietto autoveicolo non validi.");
		}
	}
	
	public boolean isValid(){
		if(ora.isEmpty() || data.isEmpty() || targa.isEmpty()) {
			return false;
		}
		return true;
	}


}
