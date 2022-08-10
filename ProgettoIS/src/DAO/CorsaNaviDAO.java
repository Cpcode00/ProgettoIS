package DAO;

import Entity.Nave;
import Entity.Traghetto;
import Entity.Aliscafo;
import Entity.Corsa;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import Control.naveNonTrovataException;

public class CorsaNaviDAO {
	
	public static Nave readNave(String persistentID) throws naveNonTrovataException {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM CORSANAVI WHERE CORSAID=?");
			s.setString(1,persistentID);
			
			Nave n=null;
			ResultSet rs = s.executeQuery();
			if(!rs.next()) {
				throw new naveNonTrovataException("Nave non trovata");
			}
			
			rs = s.executeQuery();
			while(rs.next()) {
				long Naveid = rs.getLong("Naveid");
				String navePersistentID = String.valueOf(Naveid);
				n = NaveDAO.readNave(navePersistentID);
			}
			return n;
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
	
	public static Corsa readCorsa(String navePersistentID) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("SELECT * FROM CORSANAVI WHERE NAVEID=?");
			s.setString(1,navePersistentID);
			
			Corsa c=null;
			ResultSet rs = s.executeQuery();
			
			
			rs = s.executeQuery();
			while(rs.next()) {
				long Corsaid = rs.getLong("Corsaid");
				String corsaPersistentID = String.valueOf(Corsaid);
				c = CorsaDAO.readCorsa(corsaPersistentID);
			}
			return c;
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
	
	public static void createCorsaNavi(String navePersistentID, String corsaPersistentID) {
		Connection conn = DBManager.Instance().getConnection();
		PreparedStatement s = null;
		
		try {
			s = conn.prepareStatement("INSERT INTO CORSANAVI (Naveid,Corsaid) VALUES (?,?)");
			s.setString(1,navePersistentID);
			s.setString(2,corsaPersistentID);
			
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
