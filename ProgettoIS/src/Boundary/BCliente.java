package Boundary;

import java.io.IOException;
import java.sql.SQLException;

import Control.*;
import DAO.*;
import Entity.*;

public class BCliente {
	
	public static boolean prenota() {
		System.out.println("Avvio main...");
    	GestoreVenditaBiglietti g = GestoreVenditaBiglietti.Instance();
    	boolean ritorna = false;
    	try {
    		ritorna = g.prenotaBiglietto();
    	} catch(naveNonTrovataException n) {
    		System.err.println("Errore naveNonTrovataException");
    	} catch(corsaNonTrovataException n) {
    		System.err.println("Errore corsaNonTrovataException");
    	} catch(postiPersoneNonDisponibiliException n) {
    		System.err.println("Errore postiPersoneNonDisponibiliException");
    	} catch(postiAutoNonDisponibiliException n) {
    		System.err.println("Errore postiAutoNonDisponibiliException");
    	} catch(SQLException n) {
    		System.err.println("Errore SQLException");
    	} catch(IOException n) {
    		System.err.println("Errore IOException");
    	} finally {
    		ritorna=false;
    		//return ritorna;
    	}
    	return ritorna;
    	
	}
    
	public static void main(String[] args) throws  naveNonTrovataException, IOException, corsaNonTrovataException, postiPersoneNonDisponibiliException, postiAutoNonDisponibiliException, SQLException {
    	System.out.println("Avvio main...");
    	GestoreVenditaBiglietti g = GestoreVenditaBiglietti.Instance();
    	g.prenotaBiglietto();
    }
}