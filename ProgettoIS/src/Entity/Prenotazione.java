package Entity;

import java.sql.SQLException;

import Entity.Corsa;
import Entity.Prenotazione;

public class Prenotazione {
	protected long codice;
	protected boolean confermata;
	protected Corsa corsaPrenotata;
	protected String persistentID;
	
	/**COSTRUTTORI*/
	public Prenotazione() {
		super();
		this.codice = 0;
		this.confermata = false;
		corsaPrenotata = null;
	}
	
	public Prenotazione(boolean conf) {
		super();
		this.confermata = conf;
		this.corsaPrenotata=null;
	}
	
	public Prenotazione(boolean conf, Corsa c) {
		this(conf);
		this.corsaPrenotata=c;
	}
	
	public Prenotazione(Prenotazione p) {
		super();
		this.codice = p.codice;
		this.confermata = p.confermata;
		this.corsaPrenotata = p.corsaPrenotata;
	}
	
	/**FUNZIONI GET E SET**/
	public long getCodice() {
		return this.codice;
	}
	
	public void setCodice(long codice) {
		this.codice = codice;
	}
	
	public boolean getConfermata() {
		return this.confermata;
	}
	
	public void setConfermata(boolean confermata) {
		this.confermata = confermata;
	}
	
	public Corsa getCorsaPrenotata() {
		return this.corsaPrenotata;
	}
	
	public void setCorsaPrenotata(Corsa corsaPrenotata) {
		this.corsaPrenotata = corsaPrenotata;
	}
	
	public void confermaP(String tipo) throws SQLException {
	}
	
	public String getPersistentID() {
		return this.persistentID;
	}
	
	public void setPersistentID(String persistentID) {
		this.codice = Long.parseLong(persistentID);
		this.persistentID=persistentID;
	}
	
	public String toString() {
		return ("ID = " + persistentID + ", confermata = " + String.valueOf(confermata));
	}
	
	public void print() {
		System.out.print(toString());
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Prenotazione)) {
			return false;
		} else {
			Prenotazione p = (Prenotazione)altroOggetto;
			return (persistentID.equals(p.persistentID));
		}
	}

}
