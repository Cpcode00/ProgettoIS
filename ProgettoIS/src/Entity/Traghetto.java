package Entity;

public class Traghetto extends Nave {
	private int capienzaAutoveicoli;
	private int postiRimanentiAutoveicoli;
	
	/*COSTRUTTORI*/
	public Traghetto() {
		super();
		this.capienzaAutoveicoli = 0;
		this.postiRimanentiAutoveicoli = 0;
	}
	
	public Traghetto(long codice, String s, int capienza, int capienzaAutoveicoli) {
		super(codice, s, capienza);
		this.capienzaAutoveicoli = capienzaAutoveicoli;
		this.postiRimanentiAutoveicoli = capienzaAutoveicoli;
	}
	
	public Traghetto(long codice, String s, int capienza, Corsa ca, Corsa cr) {
		super(codice, s, capienza, ca, cr);
	}
	
	public Traghetto(long codice, String s, int capienza, Corsa ca, Corsa cr, int capienzaAutoveicoli) {
		this(codice, s, capienza, ca, cr);
		this.capienzaAutoveicoli = capienzaAutoveicoli;
		this.postiRimanentiAutoveicoli = capienzaAutoveicoli;
	}
	
	public Traghetto(Traghetto t) {
		super(t);
		this.capienzaAutoveicoli = t.capienzaAutoveicoli;
		this.postiRimanentiAutoveicoli = t.postiRimanentiAutoveicoli;
	}
	
	/***FUNZIONI GET e SET***/
	public int getCapienzaAutoveicoli() {
		return this.capienzaAutoveicoli;
	}
	
	public void setCapienzaAutoveicoli(int capienzaAutoveicoli) {
		this.capienzaAutoveicoli = capienzaAutoveicoli;
	}
	
	public int getPostiRimanentiAutoveicoli() {
		return this.postiRimanentiAutoveicoli;
	}
	
	public void setPostiRimanentiAutoveicoli(int postiRimanentiAutoveicoli) {
		this.postiRimanentiAutoveicoli = postiRimanentiAutoveicoli;
	}
	
	public int contaPostiAutoveicoli() {
		return getPostiRimanentiAutoveicoli();
	}
	
	public String toString() {
		return ("ID = " + persistentID + ", nome = " + nome +
				", capienza persone = " + capienzaPersone + ", capienza autoveicoli = " + capienzaAutoveicoli);
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Traghetto)) {
			return false;
		} else {
			Traghetto t = (Traghetto)altroOggetto;
			return (super.equals(t) && capienzaAutoveicoli == t.capienzaAutoveicoli && postiRimanentiAutoveicoli == t.postiRimanentiAutoveicoli);
		}
	}
	

}
