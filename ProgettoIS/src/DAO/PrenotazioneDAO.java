package DAO;

import Entity.Prenotazione;
import Entity.Traghetto;
import Entity.PPasseggero;
import Entity.Aliscafo;
import Entity.Nave;
import Entity.PAutoveicolo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Classe DAO per l'entita' Prenotazione. 
 * 
 * CREATE TABLE Prenotazione (
 *   id long auto_increment primary key,
 *   Confermata number(1) default 0,
 *   tipo varchar(20) not null,
 *   targa varchar(20),
 *   constraint check_confermata check (Confermata in (1,0))
 *   );
 * 
 * Realizza le operazioni CRUD per l'entita' che gestisce (Create, Read, Update, Delete).
 * 
 */

public class PrenotazioneDAO {
	
	private static HashMap<String,Prenotazione> persistanceMap = new HashMap<String,Prenotazione>();
	public static final String PRENOTAZIONE_PASSEGGERO = "PASSEGGERO";
	public static final String PRENOTAZIONE_AUTOVEICOLO = "AUTOVEICOLO";
	
	private static Prenotazione restorePrenotazione(ResultSet rs) throws SQLException {
		Prenotazione p;
		
		final long id = rs.getLong("id");
		final String persistentID = rs.getString("id");
		final boolean confermata = rs.getBoolean("Confermata");
		final String tipo = rs.getString("tipo");
		if (tipo.equalsIgnoreCase(PRENOTAZIONE_PASSEGGERO)) {
			p = new PPasseggero(confermata);
		} else if (tipo.equalsIgnoreCase(PRENOTAZIONE_AUTOVEICOLO)) {
			final String targa = rs.getString("targa");
			p = new PAutoveicolo(confermata,targa);
		} else {
			throw new SQLException("Invalido");
		}
		p.setPersistentID(persistentID);
		return p;
	}
	
	public static ArrayList<Prenotazione> readAll() {
		Connection conn = DBManager.Instance().getConnection();
		Statement s = null;
		
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM PRENOTAZIONE");
			ArrayList<Prenotazione> listaPrenotazioni = new ArrayList<Prenotazione>();
			
			while (rs.next()) {
				Prenotazione p;
				final String persistentID = rs.getString("id");
				if (persistanceMap.containsKey(persistentID)) {
					p = persistanceMap.get(persistentID);
				} else {
					p = restorePrenotazione(rs);
					persistanceMap.put(p.getPersistentID(), p);
				}
				listaPrenotazioni.add(p);
			}
			return listaPrenotazioni;
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
		return null;
	}
	
	public static Prenotazione readPrenotazione(String persistentID) {
		if (persistanceMap.containsKey(persistentID)) {
			return persistanceMap.get(persistentID);
		}
		
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM PRENOTAZIONE WHERE ID = ?");
			s.setString(1, persistentID);
			
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				Prenotazione p = restorePrenotazione(rs);
				persistanceMap.put(p.getPersistentID(), p);
				return p;
			}
			return null;
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
		return null;
	}
	
	public static Prenotazione readUltimaPrenotazione() {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM PRENOTAZIONE WHERE id=(SELECT max(id) FROM PRENOTAZIONE)");
			
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				Prenotazione p = restorePrenotazione(rs);
				persistanceMap.put(p.getPersistentID(), p);
				return p;
			}
			return null;
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
		return null;
	}
	
	public static void createPrenotazione(Prenotazione p) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			if (p instanceof PPasseggero) {
				PPasseggero a = (PPasseggero)p;
				s = conn.prepareStatement("INSERT INTO PRENOTAZIONE (confermata, tipo) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setBoolean(1, a.getConfermata());
				s.setString(2, PRENOTAZIONE_PASSEGGERO);
			} else if (p instanceof PAutoveicolo) {
				PAutoveicolo t = (PAutoveicolo)p;
				s = conn.prepareStatement("INSERT INTO PRENOTAZIONE (confermata, tipo, targa) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setBoolean(1, t.getConfermata());
				s.setString(2, PRENOTAZIONE_AUTOVEICOLO);
				s.setString(3, t.getTarga());
			}
			s.executeUpdate();
			ResultSet generatedKeys = s.getGeneratedKeys();
			generatedKeys.next();
			String persistentID = generatedKeys.getString(1);
			p.setPersistentID(persistentID);
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
	}
	
	public static void updatePrenotazione(Prenotazione p) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;

		try {
			if (p instanceof PPasseggero) {
				PPasseggero a = (PPasseggero)p;
				s = conn.prepareStatement("UPDATE PRENOTAZIONE SET confermata=?, tipo=? WHERE ID=?");
				s.setBoolean(1, a.getConfermata());
				s.setString(2, PRENOTAZIONE_PASSEGGERO);
				s.setLong(3, a.getCodice());
			} else if (p instanceof PAutoveicolo) {
				PAutoveicolo t = (PAutoveicolo)p;
				s = conn.prepareStatement("UPDATE PRENOTAZIONE SET confermata=?, tipo=?, targa=? WHERE ID=?");
				s.setBoolean(1, t.getConfermata());
				s.setString(2, PRENOTAZIONE_AUTOVEICOLO);
				s.setString(3,t.getTarga());
				s.setLong(4, t.getCodice());
			}
			s.executeUpdate();
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
	}
	
	public static void deletePrenotazione(Prenotazione p) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE PRENOTAZIONECORSA WHERE ID = ?;" +
					                  "DELETE PRENOTAZIONE WHERE ID = ?;");
			s.setString(1, p.getPersistentID());
			s.setString(2, p.getPersistentID());
			s.executeUpdate();
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
	}
	
	public static void deleteAllPrenotazione() {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE PRENOTAZIONECORSA ;" +
					                  "DELETE PRENOTAZIONE;");
			s.executeUpdate();
		} catch(SQLException e) {
			System.err.println("SQLException");
		} finally {
			try {
				if (s!=null) {
					s.close();
				}
			} catch (SQLException e) {
				System.err.println("Errore");
			}
		}
	}

}
