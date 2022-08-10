package DAO;

import Entity.Biglietto;
import Entity.BPasseggero;
import Entity.BAutoveicolo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Classe DAO per l'entita' Biglietto. 
 * 
 * CREATE TABLE Biglietto (
 *   id long auto_increment primary key,
 *   tipo varchar(20) not null,
 *   data varchar(20),
 *   ora varchar(20),
 *   targa varchar(20)
 *   );
 * 
 * Realizza le operazioni CRUD per l'entita' che gestisce (Create, Read, Update, Delete).
 * 
 */

public class BigliettoDAO {
	
	private static HashMap<String,Biglietto> persistanceMap = new HashMap<String,Biglietto>();
	public static final String BIGLIETTO_PASSEGGERO = "PASSEGGERO";
	public static final String BIGLIETTO_AUTOVEICOLO = "AUTOVEICOLO";
	
	private static Biglietto restoreBiglietto(ResultSet rs) throws SQLException {
		Biglietto b;
		
		final long id = rs.getLong("id");
		final String persistentID = rs.getString("id");
		final String tipo = rs.getString("tipo");
		final String data = rs.getString("data");
		final String ora = rs.getString("ora");
		if (tipo.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
			b = new BPasseggero(data,ora);
		} else if (tipo.equalsIgnoreCase(BIGLIETTO_AUTOVEICOLO)) {
			final String targa = rs.getString("targa");
			b = new BAutoveicolo(data,ora,targa);
		} else {
			throw new SQLException("Invalido");
		}
		b.setPersistentID(persistentID);
		return b;
	}
	
	public static ArrayList<Biglietto> readAll() {
		Connection conn = DBManager.Instance().getConnection();
		Statement s = null;
		
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM BIGLIETTO");
			ArrayList<Biglietto> listaBiglietti = new ArrayList<Biglietto>();
			
			while (rs.next()) {
				Biglietto b;
				final String persistentID = rs.getString("id");
				if (persistanceMap.containsKey(persistentID)) {
					b = persistanceMap.get(persistentID);
				} else {
					b = restoreBiglietto(rs);
					persistanceMap.put(b.getPersistentID(), b);
				}
				listaBiglietti.add(b);
			}
			return listaBiglietti;
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
	
	public static Biglietto readBiglietto(String persistentID) {
		if (persistanceMap.containsKey(persistentID)) {
			return persistanceMap.get(persistentID);
		}
		
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM BIGLIETTO WHERE ID = ?");
			s.setString(1, persistentID);
			
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				Biglietto b = restoreBiglietto(rs);
				persistanceMap.put(b.getPersistentID(), b);
				return b;
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
	
	public static void createBiglietto(Biglietto b) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			if (b instanceof BPasseggero) {
				BPasseggero a = (BPasseggero)b;
				s = conn.prepareStatement("INSERT INTO BIGLIETTO (tipo, data, ora) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setString(1, BIGLIETTO_PASSEGGERO);
				s.setString(2, a.getData());
				s.setString(3, a.getOra());
			} else if (b instanceof BAutoveicolo) {
				BAutoveicolo t = (BAutoveicolo)b;
				s = conn.prepareStatement("INSERT INTO BIGLIETTO (tipo, data, ora, targa) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setString(1, BIGLIETTO_AUTOVEICOLO);
				s.setString(2, t.getData());
				s.setString(3, t.getOra());
				s.setString(4, t.getTarga());
			}
			s.executeUpdate();
			
			ResultSet generatedKeys = s.getGeneratedKeys();
			generatedKeys.next();
			long codice = generatedKeys.getLong("id");
			
			String persistentID = String.valueOf(codice);
			b.setPersistentID(persistentID);
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
	
	public static void updateBiglietto(Biglietto b) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;

		try {
			if (b instanceof BPasseggero) {
				BPasseggero a = (BPasseggero)b;
				s = conn.prepareStatement("UPDATE BIGLIETTO SET tipo=?, data=?, ora=? WHERE ID=?");
				s.setString(1, BIGLIETTO_PASSEGGERO);
				s.setString(2, a.getData());
				s.setString(3, a.getOra());
				s.setLong(4, a.getCodice());
			} else if (b instanceof BAutoveicolo) {
				BAutoveicolo t = (BAutoveicolo)b;
				s = conn.prepareStatement("UPDATE BIGLIETTO SET tipo=?, data=?, ora=?, targa=? WHERE ID=?");
				s.setString(1, BIGLIETTO_AUTOVEICOLO);
				s.setString(2, t.getData());
				s.setString(3, t.getOra());
				s.setString(4, t.getTarga());
				s.setLong(5, t.getCodice());
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
	
	public static void deleteBiglietto(Biglietto b) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE BIGLIETTOCORSA WHERE ID = ?;" +
									  "DELETE BIGLIETTOIMPIEGATO WHERE ID = ?;" + 
					                  "DELETE BIGLIETTO WHERE ID = ?;");
			s.setString(1, b.getPersistentID());
			s.setString(2, b.getPersistentID());
			s.setString(3, b.getPersistentID());
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

	public static void deleteAllBiglietto() {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE BIGLIETTOCORSA;" +
									  "DELETE BIGLIETTOIMPIEGATO;" + 
					                  "DELETE BIGLIETTO;");
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
