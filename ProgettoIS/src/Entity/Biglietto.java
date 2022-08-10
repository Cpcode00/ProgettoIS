package Entity;

public class Biglietto {
	protected long codice;
	protected String data;
	protected String ora;
	protected Corsa corsaAssociata;
	protected Impiegato impiegatoAssociato;
	protected String persistentID;
	
	/**COSTRUTTORI*/
	public Biglietto() {
		super();
		this.data="";
		this.ora="";
		this.corsaAssociata=null;
		this.impiegatoAssociato=null;
	}
	
	public Biglietto(String data, String ora) {
		super();
		this.data=data;
		this.ora=ora;
		this.corsaAssociata=null;
		this.impiegatoAssociato=null;
	}
	
	public Biglietto(String data, String ora, Corsa c) {
		this.data=data;
		this.ora=ora;
		this.corsaAssociata=c;
		this.impiegatoAssociato=null;
	}
	
	public Biglietto(String data, String ora, Corsa c, Impiegato i) {
		this.data=data;
		this.ora=ora;
		this.corsaAssociata=c;
		this.impiegatoAssociato=i;
	}
	
	public Biglietto(Biglietto b) {
		super();
		this.data=b.data;
		this.corsaAssociata=b.corsaAssociata;
		this.impiegatoAssociato=b.impiegatoAssociato;
	}
	
	/**FUNZIONI GET E SET*/
	public long getCodice() {
		return this.codice;
	}
	
	public void setCodice(long codice) {
		this.codice=codice;
	}
	
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getOra() {
		return this.ora;
	}
	
	public void setOra(String ora) {
		this.ora=ora;
	}
	
	public Corsa getCorsa_associata() {
		return this.corsaAssociata;
	}
	
	public void setCorsa_associata(Corsa corsa_associata) {
		this.corsaAssociata = corsa_associata;
	}
	
	public Impiegato getImpiegato_associato() {
		return this.impiegatoAssociato;
	}
	
	public void setImpiegato_associato(Impiegato impiegato_associato) {
		this.impiegatoAssociato=impiegato_associato;
	}
	
	public String getPersistentID() {
		return this.persistentID;
	}
	
	public void setPersistentID(String persistentID) {
		this.codice = Long.parseLong(persistentID);
		this.persistentID=persistentID;
	}
	
	public String toString() {
		return ("ID = " + codice + ", data = " + data + ", ora = " + ora);
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Biglietto)) {
			return false;
		} else {
			Biglietto b = (Biglietto)altroOggetto;
			return (persistentID.equals(b.persistentID));
		}
	}
	
	public boolean isValid(){

		if(ora.isEmpty() || data.isEmpty()){
			return false;
		}
		return true;
	}

}
