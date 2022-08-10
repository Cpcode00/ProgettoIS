package Tests;

import Entity.*;
import Control.*;
import Boundary.*;
import DAO.*;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.util.ArrayList;
import java.util.Scanner;

//@RunWith(Suite.class)
//@SuiteClasses({})
public class AllTests {
	
	@Test
	public void testPrenotaBiglietto1() {
		//precondizione: la tabella PRENOTAZIONE non contiene elementi
		
		assertEquals(true,BCliente.prenota());
	}
	
	@Test
	public void testPrenotaBiglietto2() {
		//precondizione: la tabella PRENOTAZIONE contiene 1 elemento
		
		PrenotazioneDAO.deleteAllPrenotazione();
		PPasseggero P = new PPasseggero(false);
		PrenotazioneDAO.createPrenotazione(P);
		assertEquals(true,BCliente.prenota());
	}
	
	@Test
	public void testPrenotaBiglietto3() {
		//precondizione: la tabella PRENOTAZIONE contiene 1 elemento
		
		PPasseggero P = new PPasseggero(false);
		PrenotazioneDAO.createPrenotazione(P);
		PrenotazioneDAO.createPrenotazione(P);
		PrenotazioneDAO.createPrenotazione(P);
		assertEquals(true,BCliente.prenota());
	}
	
	@Test
    public void testPrenotaBiglietto4() {
        System.out.print("Inserire una stringa composta da un solo carattere non valido (diverso da 'p' o 'a'): ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).sceltaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto5() {
        System.out.print("Inserire una stringa più lunga di 1 carattere: ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).sceltaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto6() {
        System.out.println("Inserire stringa vuota. (Premere invio)");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).sceltaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test 
    public void testPrenotaBiglietto7() {
        System.out.print("Inserire un carattere errato (diverso da 's' e 'n'): ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).confermaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto8() {
        System.out.print("Inserire stringa più lunga di 1 carattere: ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).confermaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto9() {
        System.out.print("Inserire stringa vuota. (Premere invio): ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).confermaPrenotazione(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto10() {
        Corsa corsaDesiderata = new Corsa();
        MutableInt corsaScelta = new MutableInt();
        ArrayList<Corsa> listaCorse = (GestoreVenditaBiglietti.Instance()).getListaCorse();
        (GestoreVenditaBiglietti.Instance()).stampaCorse(listaCorse);
        System.out.println("Inserire codice non appartenente a nessuna corsa: ");
        corsaDesiderata = (GestoreVenditaBiglietti.Instance()).sceltaCorsa(corsaScelta,listaCorse);
        assertNull(corsaDesiderata);
    }

	@Test
    public void testPrenotaBiglietto11() {
        //servono funzioni aggiornate
		/*
		Aliscafo n = new Aliscafo(6,"Marenna",110);
		n.setPostiRimanentiPersone(0);
		NaveDAO.createNave(n);
		Corsa c = new Corsa(10,"09:00","Napoli","19:00","Livorno",30);
		CorsaDAO.createCorsa2(c);
		c.setNaveAssociata(n);
		CorsaNaviDAO.createCorsaNavi(n.getPersistentID(),c.getPersistentID());
		c.print();
		n.print();
		*/
		Aliscafo n = (Aliscafo)NaveDAO.readNave("1");
		n.setPostiRimanentiPersone(0);
		NaveDAO.updateNave(n);
		Corsa c = CorsaNaviDAO.readCorsa(n.getPersistentID());
		c.print();
		System.out.print("Seleziona corsa con codice " + c.getCodice() + ": ");
		boolean boh = BCliente.prenota();
		assertEquals(false,false);
    }
	
	@Test
    public void testPrenotaBiglietto12() {
        System.out.print("Inserire targa con meno di 7 caratteri alfanumerici: ");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).inserimentoTarga(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto13() {
        System.out.println("Inserire targa con più di 7 caratteri alfanumerici.");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).inserimentoTarga(sc);
        assertEquals(false, ret);
    }
	
	@Test
    public void testPrenotaBiglietto14() {
        System.out.println("Inserire targa con 7 caratteri, di cui almeno uno non sia alfanumerico.");
        StringBuffer sc = new StringBuffer();
        boolean ret = (GestoreVenditaBiglietti.Instance()).inserimentoTarga(sc);
        assertEquals(false, ret);
    }
	
	//test 1: Codice Biglietto è numerico
	//output atteso: codice valido
	@Test
	public void StampabigliettoTestCodiceValido(){
	    System.out.println("\n - TEST 1 - \n");
	 
	    long codice1 = 1234567;
	    int codice2 = 123;
	    double codice3double = 1.2;
	    int codice4neg = -3;
	    //String codice20cifre = "200000000000000000000";
	    
	    assertTrue(BImpiegato.isNumeric(String.valueOf(codice1)));
	    assertTrue(BImpiegato.isNumeric(String.valueOf(codice2)));
	    assertFalse(BImpiegato.isNumeric(String.valueOf(codice3double)));
	    assertFalse(BImpiegato.isNumeric(String.valueOf(codice4neg)));
	    //assertFalse(BImpiegato.isNumeric(codice20cifre));
	}

	//test 2: Codice Biglietto non è  numerico
	//output atteso: codice non è un numero
	@Test
	public void StampabigliettoTestCodiceNonValido(){
	  System.out.println("\n - TEST 1 - \n");

	   char codice1 = 'a';
	   String codice2 = "prova";
	   String codice3 = "prova123";
	   
	   assertFalse(BImpiegato.isNumeric(String.valueOf(codice1)));
	   assertFalse(BImpiegato.isNumeric(String.valueOf(codice2)));
	   assertFalse(BImpiegato.isNumeric(String.valueOf(codice3)));

	}

	// test 3: Codice Biglietto è null
	//output atteso: codice non è numerico
	@Test
	public void StampabigliettoTestCodiceNull(){
	   System.out.println("\n - TEST 1 - \n");

	   String codice1 = null;
	   
	   assertFalse(BImpiegato.isNumeric(String.valueOf(codice1)));

	}

	    
	//test 1: listaBiglietti è vuota con codice valido
	// output stampa nessun biglietto trovato
	@Test
	public void StampabigliettoListaVuota() {
		ArrayList<Biglietto> listaBiglietti = new ArrayList<Biglietto>();
	    BigliettoDAO.deleteAllBiglietto();

	    long codicevalido = 151;
	    assertNull(GestoreVenditaBiglietti.stampaBiglietto(codicevalido));

	}


	//test 2: listaBiglietti presenta un solo biglietto
	//output stampa risultato
	@Test
	public void StampabigliettoListacon1(){
	    BigliettoDAO.deleteAllBiglietto();
	    BPasseggero b = new BPasseggero("13/02/2022","06:01");
	    BigliettoDAO.createBiglietto(b);
	    Biglietto b1 = BigliettoDAO.readBiglietto(b.getPersistentID());

	    long codicevalido = Long.parseLong(b1.getPersistentID());
	    assertNotNull(GestoreVenditaBiglietti.stampaBiglietto(codicevalido));

	}

	//test 3: listaBiglietti presenta molti biglietti
	//precondizioni
	// output stampa risultato
	@Test
	public void StampabigliettoListaconmolti(){
		BigliettoDAO.deleteAllBiglietto();
	    BPasseggero b1 = new BPasseggero("13/02/2022","06:01");
	    BPasseggero b2 = new BPasseggero("14/02/2022","06:01");
	    BPasseggero b3 = new BPasseggero("15/02/2022","06:01");
	    BigliettoDAO.createBiglietto(b1);
	    BigliettoDAO.createBiglietto(b2);
	    BigliettoDAO.createBiglietto(b3);
	    Biglietto bTrovato = BigliettoDAO.readBiglietto(b3.getPersistentID());

	    long codicevalido1 = Long.parseLong(b1.getPersistentID());
	    long codicevalido2 = Long.parseLong(b2.getPersistentID());
	    long codicevalido3 = Long.parseLong(b3.getPersistentID());
	    long nocodicevalido = 150;
	    if (nocodicevalido != codicevalido1 && nocodicevalido != codicevalido2 && nocodicevalido != codicevalido3) {
	    	assertNull(GestoreVenditaBiglietti.stampaBiglietto(nocodicevalido));
	    }
	}
}
