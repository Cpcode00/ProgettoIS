package Entity;

import java.sql.SQLException;

import DAO.NaveDAO;
import DAO.PrenotazioneDAO;

public class PPasseggero extends Prenotazione {

	/*COSTRUTTORI*/
	public PPasseggero() {
		super();
	}
	
	public PPasseggero(boolean conf) {
		super(conf);
	}
	
	public PPasseggero(boolean conf, Corsa c) {
		super(conf, c);
	}
	
	public PPasseggero(PPasseggero p) {
		super(p);
	}

    public void confermaP(String tipo) throws SQLException {
        long codice = ((this.corsaPrenotata).getNaveAssociata()).getCodice();
        String persistentID = String.valueOf(codice);
        PrenotazioneDAO.createPrenotazione(this);
        Nave nave = NaveDAO.readNave(persistentID);
        nave.setPostiRimanentiPersone(nave.getPostiRimanentiPersone() - 1);
        NaveDAO.updateNave(nave);
    }
    
}
