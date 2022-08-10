package DAO;

import Entity.Nave;
import Entity.Traghetto;
import Entity.Aliscafo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Classe DAO per l'entita' Nave. 
 * 
 * CREATE TABLE Nave (
 *   id long auto_increment primary key,
 *   nome varchar(30) not null,
 *   tipo varchar(20) not null,
 *   capienzaPersone int not null,
 *   postiRimanentiPersone int not null,
 *   capienzaAutoveicoli int default null,
 *   postiRimanentiAutoveicoli int default null
 * 	 );
 * 
 * Realizza le operazioni CRUD per l'entita' che gestisce (Create, Read, Update, Delete).
 * 
 */

public class NaveDAO {
	
	private static HashMap<String,Nave> persistanceMap = new HashMap<String,Nave>();
	private static final String NAVE_TRAGHETTO = "TRAGHETTO";
	private static final String NAVE_ALISCAFO = "ALISCAFO";
	
	private static Nave restoreNave(ResultSet rs) throws SQLException {
		Nave n;
		final long id = rs.getLong("id");
		final String persistentID = rs.getString("id");
		final String nome = rs.getString("nome");
		final String tipo = rs.getString("tipo");
		final int capienzaPersone = rs.getInt("capienzaPersone");
		int postiRimanentiPersone = rs.getInt("postiRimanentiPersone");
		if (tipo.equalsIgnoreCase(NAVE_TRAGHETTO)) {
			final int capienzaAutoveicoli = rs.getInt("capienzaAutoveicoli");
			int postiRimanentiAutoveicoli = rs.getInt("postiRimanentiAutoveicoli");
			n = new Traghetto(id,nome,capienzaPersone,capienzaAutoveicoli);
		} else if (tipo.equalsIgnoreCase(NAVE_ALISCAFO)) {
			n = new Aliscafo(id,nome,capienzaPersone);
		} else {
			throw new SQLException("DATABASE invalido!");
		}
		
		n.setPersistentID(persistentID);
		return n;
	}
	
	public static ArrayList<Nave> readAll() {
		Connection conn = DBManager.Instance().getConnection();
		Statement s = null;
		
		try {
			ArrayList<Nave> listaNavi = new ArrayList<Nave>();
			
			s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM NAVE");
			
			while (rs.next()) {
				Nave n;
				
				final String persistentID = rs.getString("id");
				if (persistanceMap.containsKey(persistentID)) {
					n = persistanceMap.get(persistentID);
				} else {
					n = restoreNave(rs);
					persistanceMap.put(n.getPersistentID(), n);
				}
				listaNavi.add(n);
			}
			return listaNavi;
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
	
	public static Nave readNave(String persistentID) {
		if (persistanceMap.containsKey(persistentID)) {
			return persistanceMap.get(persistentID);
		}
		
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM NAVE WHERE ID=?");
			s.setString(1,persistentID);
			
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Nave n = restoreNave(rs);
				persistanceMap.put(n.getPersistentID(),n);
				return n;
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
	
	public static void createNave(Nave n) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			if (n instanceof Aliscafo) {
				Aliscafo a = (Aliscafo)n;
				s = conn.prepareStatement("INSERT INTO NAVE (nome, tipo, capienzaPersone, postiRimanentiPersone) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setString(1, a.getNome());
				s.setString(2, NAVE_ALISCAFO);
				s.setInt(3, a.getCapienzaPersone());
				s.setInt(4, a.getPostiRimanentiPersone());
			} else if (n instanceof Traghetto) {
				Traghetto t = (Traghetto)n;
				s = conn.prepareStatement("INSERT INTO NAVE (nome, tipo, capienzaPersone, postiRimanentiPersone, capienzaAutoveicoli, postiRimanentiAutoveicoli) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				s.setString(1, t.getNome());
				s.setString(2, NAVE_TRAGHETTO);
				s.setInt(3, t.getCapienzaPersone());
				s.setInt(4, t.getPostiRimanentiPersone());
				s.setInt(5, t.getCapienzaAutoveicoli());
				s.setInt(6, t.getPostiRimanentiAutoveicoli());
			}
			s.executeUpdate();
			ResultSet generatedKeys = s.getGeneratedKeys();
			generatedKeys.next();
			String persistentID = generatedKeys.getString(1);
			n.setPersistentID(persistentID);
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
	
	public static void updateNave(Nave n) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			if (n instanceof Aliscafo) {
				Aliscafo a = (Aliscafo)n;
				s = conn.prepareStatement("UPDATE NAVE SET nome=?, tipo=?, capienzaPersone=?, postiRimanentiPersone=? WHERE ID=?");
				s.setString(1, a.getNome());
				s.setString(2, NAVE_ALISCAFO);
				s.setInt(3,a.getCapienzaPersone());
				s.setInt(4, a.getPostiRimanentiPersone());
				s.setString(5, a.getPersistentID());
			} else if (n instanceof Traghetto) {
				Traghetto t = (Traghetto)n;
				s = conn.prepareStatement("UPDATE NAVE SET nome=?, tipo=?, capienzaPersone=?, postiRimanentiPersone=?, capienzaAutoveicoli=?, postiRimanentiAutoveicoli=? WHERE ID=?");
				s.setString(1, t.getNome());
				s.setString(2, NAVE_TRAGHETTO);
				s.setInt(3,t.getCapienzaPersone());
				s.setInt(4, t.getPostiRimanentiPersone());
				s.setInt(5,t.getCapienzaAutoveicoli());
				s.setInt(6,t.getPostiRimanentiAutoveicoli());
				s.setString(7, t.getPersistentID());
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
	
	public static void deleteNave(Nave n) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE CORSANAVI WHERE ID = ?;" +
					                  "DELETE NAVE WHERE ID = ?;");
			s.setString(1, n.getPersistentID());
			s.setString(2, n.getPersistentID());
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
