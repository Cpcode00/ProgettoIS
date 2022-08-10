package Entity;

import Entity.Corsa;
import Entity.PAutoveicolo;
import Entity.Prenotazione;
import Entity.Traghetto;

import java.sql.SQLException;

import  DAO.*;

public class PAutoveicolo extends Prenotazione {
	private String targa;
	
	public PAutoveicolo() {
		super();
		this.targa = "";
	}
	
	public PAutoveicolo(boolean conf) {
		super(conf);
	}
	
	public PAutoveicolo(boolean conf, String targa) {
		super(conf);
		this.targa = targa;
	}
	
	public PAutoveicolo(boolean conf, Corsa c) {
		super(conf, c);
		this.targa="";
	}
	
	public PAutoveicolo(boolean conf, Corsa c, String targa) {
		super(conf, c);
		this.targa = targa;
	}
	
	public PAutoveicolo(PAutoveicolo p) {
		super(p);
		this.targa = p.targa;
	}
	
	public String getTarga() {
		return this.targa;
	}
	
	public void setTarga(String Targa) {
		this.targa = targa;
	}
	
	/*
	public void conferma(PAutoveicolo p) {
		p.setConfermata(true);
		p = PrenotazioneDAO.create()
	}
	*/
	
	public void confermaP(String tipo) throws SQLException {
		Traghetto traghettoAssociato = (Traghetto) ((this.corsaPrenotata).getNaveAssociata());
		if (tipo.equalsIgnoreCase("PASSEGGERO")) {
			traghettoAssociato.setPostiRimanentiPersone(traghettoAssociato.getPostiRimanentiPersone() - 1);
		} else if (tipo.equalsIgnoreCase("AUTOVEICOLO")) {
			traghettoAssociato.setPostiRimanentiAutoveicoli(traghettoAssociato.getPostiRimanentiAutoveicoli() - 1);
		}
		PrenotazioneDAO.createPrenotazione(this);
        NaveDAO.updateNave(traghettoAssociato);
    }
	
	public String toString() {
		return ("ID = " + codice + ", confermata = " + String.valueOf(confermata) + ", targa = " + targa);
	}
	
	public void print() {
		System.out.print(toString());
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof PAutoveicolo)) {
			return false;
		} else {
			PAutoveicolo p = (PAutoveicolo)altroOggetto;
			return (super.equals(p) && targa == p.targa);
		}
	}
	
}