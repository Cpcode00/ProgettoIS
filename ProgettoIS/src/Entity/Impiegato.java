package Entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Impiegato {
	private long codice;
	private boolean autenticato;
	private ArrayList<Biglietto> bigliettiEmessi;
	private String persistentID;
	
	public void login(long id) {
        /**molto probabile che si debba usare
         * l'accesso al DB, tramite una funzione
         * di impiegatoDAO che verifichi se l'ID è 
         * presente nel database.
         */
    }
	
	/**COSTRUTTORI**/
	public Impiegato() {
		super();
		this.codice=0;
		this.autenticato = false;
		this.bigliettiEmessi = null;
	}
	
	public Impiegato(boolean autenticato) {
		super();
		this.autenticato = autenticato;
		this.bigliettiEmessi = null;
	}
	
	public Impiegato(boolean autenticato, ArrayList<Biglietto> bigliettiEmessi) {
		this(autenticato);
		this.bigliettiEmessi = bigliettiEmessi;
	}
	
	public Impiegato(Impiegato i) {
		super();
		this.codice = i.codice;
		this.autenticato = i.autenticato;
		Iterator<Biglietto> iterator = i.bigliettiEmessi.iterator();
		while(iterator.hasNext()) {
			(this.bigliettiEmessi).add(new Biglietto(iterator.next()));
		}
	}
	
	/**FUNZIONI GET E SET*/
	public long getCodice() {
		return this.codice;
	}
	
	public void setCodice(long codice) {
		this.codice = codice;
	}
	
	public boolean isAutenticato() {
		return this.autenticato;
	}
	
	public boolean getAutenticato() {
		return this.autenticato;
	}
	
	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	
	public ArrayList<Biglietto> getBigliettiEmessi() {
		return this.bigliettiEmessi;
	}
	
	public void setBigliettiEmessi(ArrayList<Biglietto> bigliettiEmessi) {
		this.bigliettiEmessi = bigliettiEmessi;
	}
	
	public String getPersistentID() {
		return this.persistentID;
	}
	
	public void setPersistentID(String persistentID) {
		this.codice = Long.parseLong(persistentID);
		this.persistentID=persistentID;
	}
	
	public String toString() {
		return ("ID = " + persistentID + ", autenticato = " + String.valueOf(autenticato));
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Impiegato)) {
			return false;
		} else {
			Impiegato i = (Impiegato)altroOggetto;
			return (persistentID.equals(i.persistentID));
		}
	}


}
