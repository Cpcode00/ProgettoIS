package DAO;

import Entity.Impiegato;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Classe DAO per l'entita' Impiegato. 
 * 
 * CREATE TABLE Impiegato (
 *   id long auto_increment primary key,
 *   Autenticato number(1) default 0,
 *   constraint check_boolean check (Autenticato in (1,0))
 *   );
 * 
 * Realizza le operazioni CRUD per l'entita' che gestisce (Create, Read, Update, Delete).
 * 
 */

public class ImpiegatoDAO {

	private static HashMap<String,Impiegato> persistanceMap = new HashMap<String,Impiegato>();
	
	private static Impiegato restoreImpiegato(ResultSet rs) throws SQLException {
		Impiegato i;
		
		final long id = rs.getLong("id");
		final String persistentID = rs.getString("id");
		final boolean autenticato = rs.getBoolean("autenticato");
		i = new Impiegato(autenticato);
		i.setPersistentID(persistentID);
		return i;
	}
	
	public static ArrayList<Impiegato> readAll() {
		Connection conn = DBManager.Instance().getConnection();
		Statement s = null;
		
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM IMPIEGATO");
			ArrayList<Impiegato> listaImpiegati = new ArrayList<Impiegato>();
			
			while (rs.next()) {
				Impiegato i;
				final String persistentID = rs.getString("id");
				if (persistanceMap.containsKey(persistentID)) {
					i = persistanceMap.get(persistentID);
				} else {
					i = restoreImpiegato(rs);
					persistanceMap.put(i.getPersistentID(), i);
				}
				listaImpiegati.add(i);
			}
			return listaImpiegati;
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
	
	public static Impiegato readImpiegato(String persistentID) {
		if (persistanceMap.containsKey(persistentID)) {
			return persistanceMap.get(persistentID);
		}
		
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM IMPIEGATO WHERE ID = ?");
			s.setString(1, persistentID);
			
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				Impiegato i = restoreImpiegato(rs);
				persistanceMap.put(i.getPersistentID(), i);
				return i;
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
	
	public static void createImpiegato(Impiegato i) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("INSERT INTO IMPIEGATO (Autenticato) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			s.setBoolean(1, i.getAutenticato());
			s.executeUpdate();
			
			ResultSet generatedKeys = s.getGeneratedKeys();
			generatedKeys.next();
			String persistentID = generatedKeys.getString(1);
			i.setPersistentID(persistentID);
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
	
	public static void updateimpiegato(Impiegato i) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;

		try {
			s = conn.prepareStatement("UPDATE IMPIEGATO SET AUTENTICATO=? WHERE ID=?");
			s.setBoolean(1, i.getAutenticato());
			s.setLong(2, i.getCodice());
			
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
	
	public static void deleteImpiegato(Impiegato i) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE BIGLIETTOIMPIEGATO WHERE ID = ?;" +
									  "DELETE IMPIEGATO WHERE ID = ?;");
			s.setString(1, i.getPersistentID());
			s.setString(2, i.getPersistentID());
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
