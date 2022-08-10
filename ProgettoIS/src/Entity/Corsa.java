package Entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Corsa {
	private long codice;
	private String orarioPartenza;
	private String portoPartenza;
	private String orarioArrivo;
	private String portoArrivo;
	private double prezzo;
	private Nave naveAssociata;
	private ArrayList<Biglietto> bigliettiCorsa;
	private ArrayList<Prenotazione> prenotazioniCorsa;
	private String persistentID;
	
	/**COSTRUTTORI*/
	public Corsa() {
		this.codice=0;
		this.orarioPartenza="";
		this.portoPartenza="";
		this.orarioArrivo="";
		this.portoArrivo="";
		this.prezzo=0.0;
		this.naveAssociata = null;
		this.bigliettiCorsa = null;
		this.prenotazioniCorsa = null;
	}
	
	public Corsa(long codice, String oraP, String portoP, String oraA, String portoA) {
		super();
		this.codice = codice;
		this.orarioPartenza = oraP;
		this.portoPartenza = portoP;
		this.orarioArrivo = oraA;
		this.portoArrivo = portoA;
		this.prezzo = 0;
		this.persistentID=String.valueOf(codice);
		this.naveAssociata = null;
		this.bigliettiCorsa = null;
		this.prenotazioniCorsa = null;
	}
	
	public Corsa(long codice, String oraP, String portoP, String oraA, String portoA, double prezzo) {
		this(codice, oraP, portoP, oraA, portoA);
		this.prezzo = prezzo;
		this.persistentID=String.valueOf(codice);
	}
	
	public Corsa(String oraP, String portoP, String oraA, String portoA) {
		super();
		this.orarioPartenza = oraP;
		this.portoPartenza = portoP;
		this.orarioArrivo = oraA;
		this.portoArrivo = portoA;
		this.prezzo = 0;
		this.naveAssociata = null;
		this.bigliettiCorsa = null;
		this.prenotazioniCorsa = null;
	}
	
	public Corsa(String oraP, String portoP, String oraA, String portoA, double prezzo) {
		super();
		this.orarioPartenza = oraP;
		this.portoPartenza = portoP;
		this.orarioArrivo = oraA;
		this.portoArrivo = portoA;
		this.prezzo = prezzo;
		this.naveAssociata = null;
		this.bigliettiCorsa = null;
		this.prenotazioniCorsa = null;
	}
	
	public Corsa(Corsa c) {
		super();
		this.codice=c.codice;
		this.orarioPartenza = c.orarioPartenza;
		this.portoPartenza = c.portoPartenza;
		this.orarioArrivo = c.orarioArrivo;
		this.portoArrivo = c.portoArrivo;
		this.prezzo=c.prezzo;
		this.persistentID=c.persistentID;
		naveAssociata = c.naveAssociata;
		
		if(c.bigliettiCorsa!=null) {
			Iterator<Biglietto> iteratorB = c.bigliettiCorsa.iterator();
			while(iteratorB.hasNext()) {
				(this.bigliettiCorsa).add(new Biglietto(iteratorB.next()));
			}
		}
		
		if(c.prenotazioniCorsa!=null) {
			Iterator<Prenotazione> iteratorP = c.prenotazioniCorsa.iterator();
			while (iteratorP.hasNext()) {
				(this.prenotazioniCorsa).add(new Prenotazione(iteratorP.next()));
			}
		}
		
	}
	
	/**FUNZIONI GET E SET*/
	public long getCodice() {
		return this.codice;
	}
	
	public void setCodice(long codice) {
		this.codice = codice;
	}
	
	public String getOrarioPartenza() {
		return this.orarioPartenza;
	}
	
	public void setOrarioPartenza(String orarioPartenza) {
		this.orarioPartenza = orarioPartenza;
	}
	
	public String getPortoPartenza() {
		return this.portoPartenza;
	}
	
	public void setPortoPartenza(String portoPartenza) {
		this.portoPartenza = portoPartenza;
	}
	
	public String getOrarioArrivo() {
		return this.orarioArrivo;
	}
	
	public void setOrarioArrivo(String orarioArrivo) {
		this.orarioArrivo = orarioArrivo;
	}
	
	public String getPortoArrivo() {
		return this.portoArrivo;
	}
	
	public void setPortoArrivo(String portoArrivo) {
		this.portoArrivo = portoArrivo;
	}
	
	public double getPrezzo() {
		return this.prezzo;
	}
	
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public Nave getNaveAssociata() {
		return this.naveAssociata;
	}
	
	public void setNaveAssociata(Nave n) {
		this.naveAssociata = n;
	}
	
	public ArrayList<Biglietto> getListaBiglietti() {
		return this.bigliettiCorsa;
	}
	
	public void setListaBiglietti(ArrayList<Biglietto> bigliettiCorsa) {
		this.bigliettiCorsa = bigliettiCorsa;
	}
	
	public ArrayList<Prenotazione> getListaPrenotazioni() {
		return this.prenotazioniCorsa;
	}
	
	public void setListaPrenotazioni(ArrayList<Prenotazione> prenotazioniCorsa) {
		this.prenotazioniCorsa = prenotazioniCorsa;
	}
	
	public String getPersistentID() {
		return this.persistentID;
	}
	
	public void setPersistentID(String persistentID) {
		this.codice = Long.parseLong(persistentID);
		this.persistentID=persistentID;
	}
	
	public String toString() {
		return ("ID = " + codice + ", orario partenza = " + orarioPartenza +
				", porto partenza = " + portoPartenza + ", orario arrivo = " + orarioArrivo +
				", porto arrivo = " + portoArrivo + ", prezzo = " + prezzo);
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	/*
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Corsa)) {
			return false;
		} else {
			Corsa c = (Corsa)altroOggetto;
			return (persistentID.equals(c.persistentID));
		}
	}
	*/
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Corsa)) {
			return false;
		} else {
			Corsa c = (Corsa)altroOggetto;
			return (orarioPartenza.equals(c.orarioPartenza)&&
					portoPartenza.equals(c.portoPartenza)&&
					orarioArrivo.equals(c.orarioArrivo)&&
					portoArrivo.equals(c.portoArrivo));
		}
	}


}
