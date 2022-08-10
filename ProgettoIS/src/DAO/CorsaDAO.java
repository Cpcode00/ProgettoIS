package DAO;

import Entity.Corsa;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

/*
 * Classe DAO per l'entita' Corsa. 
 * 
 * CREATE TABLE Corsa (
 *   id long auto_increment primary key,
 *   orarioPartenza varchar(20),
 *   portoPartenza varchar(20),
 *   orarioArrivo varchar(20),
 *   portoArrivo varchar(20),
 *   prezzo double default 0
 *   );
 * 
 * Realizza le operazioni CRUD per l'entita' che gestisce (Create, Read, Update, Delete).
 * 
 */

public class CorsaDAO {
	/*
	 *  Le persistanceMap ci permettono di garantire che per ogni entita' del mondo relazionale esiste 
	 *  solamente una entita' del mondo degli oggetti che la rappresenta e viceversa.
	 */
	private static HashMap<String,Corsa> persistanceMap = new HashMap<String,Corsa>();
	
	private static Corsa restoreCorsa(ResultSet rs) throws SQLException {
		Corsa c;
		
		final long id = rs.getLong("id");
		final String persistentID = rs.getString("id");
		final String orarioPartenza = rs.getString("orarioPartenza");
		final String portoPartenza = rs.getString("portoPartenza");
		final String orarioArrivo = rs.getString("orarioArrivo");
		final String portoArrivo = rs.getString("portoArrivo");
		final double prezzo = rs.getDouble("prezzo");
		c = new Corsa(id, orarioPartenza, portoPartenza, orarioArrivo, portoArrivo, prezzo);
		c.setPersistentID(persistentID);
		return c;
	}
	
	public static ArrayList<Corsa> readAll() {
		Connection conn = DBManager.Instance().getConnection();
		Statement s = null;
		
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM CORSA");
			ArrayList<Corsa> listaCorse = new ArrayList<Corsa>();
			
			while(rs.next()) {
				Corsa c;
				final String persistentID = rs.getString("id");
				if (persistanceMap.containsKey(persistentID)) {
					c = persistanceMap.get(persistentID);
				} else {
					c = restoreCorsa(rs);
					persistanceMap.put(c.getPersistentID(), c);
				}
				listaCorse.add(c);
			}
			return listaCorse;
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
	
	public static Corsa readCorsa(String persistentID) {
		if (persistanceMap.containsKey(persistentID)) {
			return persistanceMap.get(persistentID);
		}
		
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM CORSA WHERE ID=?");
			s.setString(1, persistentID);
			
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				Corsa c = restoreCorsa(rs);
				return c;
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
	
	public static void createCorsa(Corsa c) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("INSERT INTO CORSA (orarioPartenza, portoPartenza, orarioArrivo, portoArrivo, prezzo) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			s.setString(1,c.getOrarioPartenza());
			s.setString(2,c.getPortoPartenza());
			s.setString(3,c.getOrarioArrivo());
			s.setString(4,c.getPortoArrivo());
			s.setDouble(5,c.getPrezzo());
			s.executeUpdate();
			
			ResultSet generatedKeys = s.getGeneratedKeys();
			generatedKeys.next();
			String persistentID = generatedKeys.getString(1);
			c.setPersistentID(persistentID);
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
	
	public static void createCorsa2(Corsa c) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("INSERT INTO CORSA (id,orarioPartenza, portoPartenza, orarioArrivo, portoArrivo, prezzo) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			s.setLong(1,c.getCodice());
			s.setString(2,c.getOrarioPartenza());
			s.setString(3,c.getPortoPartenza());
			s.setString(4,c.getOrarioArrivo());
			s.setString(5,c.getPortoArrivo());
			s.setDouble(6,c.getPrezzo());
			s.executeUpdate();
			
			
			String persistentID = String.valueOf(c.getCodice());
			persistanceMap.put(persistentID, c);
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
	
	public static void updateCorsa(Corsa c) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("UPDATE CORSA SET orarioPartenza=?, portoPartenza=?, orarioArrivo=?, portoArrivo=?, prezzo=? where ID=?");
			s.setString(1,c.getOrarioPartenza());
			s.setString(2,c.getPortoPartenza());
			s.setString(3,c.getOrarioArrivo());
			s.setString(4,c.getPortoArrivo());
			s.setDouble(5,c.getPrezzo());
			s.setLong(6,c.getCodice());
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
	
	public static void deleteCorsa(Corsa c) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("DELETE FROM CORSANAVI WHERE ID=?;" +
					                  "DELETE FROM CORSA WHERE ID=?;");
			s.setDouble(1,c.getCodice());
			s.setDouble(2,c.getCodice());
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
