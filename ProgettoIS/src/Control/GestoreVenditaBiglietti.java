package Control;

import Entity.*;
import DAO.CorsaDAO;
import Control.corsaNonTrovataException;
import Control.naveNonTrovataException;
import Control.postiAutoNonDisponibiliException;
import Control.postiPersoneNonDisponibiliException;
import Entity.Aliscafo;
import Entity.Corsa;
import Entity.Nave;
import Entity.PAutoveicolo;
import Entity.PPasseggero;
import Entity.Prenotazione;
import Entity.Traghetto;

import java.lang.Integer;
import java.lang.Character;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.ToIntFunction;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.mutable.MutableInt;

import Control.*;
import DAO.*;

public class GestoreVenditaBiglietti {
	private static final String BIGLIETTO_PASSEGGERO = "PASSEGGERO";
	private static final String BIGLIETTO_AUTOVEICOLO = "AUTOVEICOLO";
	private static ArrayList<Corsa> listaCorse = new ArrayList<Corsa>();
	private static Iterator<Corsa> iterator;
	private static Nave n;
	private static GestoreVenditaBiglietti instance;
	private static ArrayList<Biglietto> listaBiglietti = new ArrayList<Biglietto>();
	
	/**Costruttore privato, come previsto da pattern singleton**/
	private GestoreVenditaBiglietti() {
		super();
	}
	
	public static GestoreVenditaBiglietti Instance() {
		if (instance == null) {
			instance = new GestoreVenditaBiglietti();
		}
		return instance;
	}
	
	/**FUNZIONI CONTROL**/
	public int[] contaPosti(Nave n) {
		//il Boundary di Impiegato si è occupato di gestire l'autenticazione
		int[] posti = new int[2];
		if(n instanceof Aliscafo) {
			Aliscafo a = (Aliscafo)n;
			posti[0] = a.contaPosti();
			posti[1] = 0;
        }
        else if(n instanceof Traghetto) {
            Traghetto t = (Traghetto)n;
            posti[0] = t.contaPosti();
            posti[1] = t.contaPostiAutoveicoli();
        }
		return posti;
	}
	
	public void aggiornaPosti(Nave n, String tipoBiglietto) {
		if (n instanceof Aliscafo && tipoBiglietto.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
			Aliscafo a = (Aliscafo)n;
			a.setPostiRimanentiPersone(a.getPostiRimanentiPersone()-1);
		} else if (n instanceof Traghetto) {
			Traghetto t = (Traghetto)n;
			if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
				t.setPostiRimanentiPersone(t.getPostiRimanentiPersone()-1);
			} else if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_AUTOVEICOLO)) {
				t.setPostiRimanentiAutoveicoli(t.getPostiRimanentiAutoveicoli()-1);
			}
			
		}
	}
	
	public ArrayList<Corsa> getListaCorse() {
		Nave n=null;
		
		listaCorse = CorsaDAO.readAll();
		Iterator<Corsa> iterator = listaCorse.iterator(); 
		while(iterator.hasNext()) {
            Corsa temp = iterator.next();
            n = getNave(temp.getPersistentID());
            temp.setNaveAssociata(n);
		}
		
		return listaCorse;
	}
	
	public Nave getNave(String persistentID) {
		Nave m = null;
		try {
			m = CorsaNaviDAO.readNave(persistentID);
		} catch (naveNonTrovataException n) {
			System.err.println("Errore");
		}
		
		return m;
	}
	
	public Corsa ricercaCorsa(long codice) {
		iterator = listaCorse.iterator();
		while(iterator.hasNext()) {
			Corsa c = new Corsa(iterator.next());
			if (codice == c.getCodice()) {
				return c;
			}
		}
		return null;
	}
	
	public Corsa ricercaCorsa(String orarioPartenza, String portoPartenza, String orarioArrivo, String portoArrivo) {
		Corsa cin = new Corsa(orarioPartenza, portoPartenza, orarioArrivo, portoArrivo);
		iterator = listaCorse.iterator();
		while(iterator.hasNext()) {
			Corsa c = new Corsa(iterator.next());
			if(cin.equals(c)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Corsa> ricercaCorse(String orarioPartenza, String portoPartenza, String orarioArrivo, String portoArrivo) {
		Corsa cin = new Corsa(orarioPartenza, portoPartenza, orarioArrivo, portoArrivo);
		iterator = listaCorse.iterator();
		ArrayList<Corsa> listaCorseRicerca = new ArrayList<Corsa>();
		while(iterator.hasNext()) {
			Corsa c = iterator.next();
			if(cin.equals(c)) {
				listaCorseRicerca.add(c);
			}
		}
		return listaCorseRicerca;
	}
	
	public Corsa[] getListaCorse(ArrayList<Corsa> listaCorse) {
		return listaCorse.toArray(new Corsa[listaCorse.size()]);
	}
	
	public void stampaCorse(ArrayList<Corsa> listaCorse) {
		System.out.println("Lista corse:");
		Corsa[] lista_corse = getListaCorse(listaCorse);
		for (int i=0; i < lista_corse.length; i++) {
			System.out.print("\t");
			lista_corse[i].print();
		}
		System.out.flush();
	}
	
	
	public Corsa isInLista(ArrayList<Corsa> corsaIn, long id) {
		Iterator<Corsa> it = corsaIn.iterator();
		for(int i=0; i < corsaIn.size(); i++) {
			if(corsaIn.get(i).getCodice()==id) {
				return corsaIn.get(i);
			}
		}
		return null;
		
	}
	
	public static ArrayList<Biglietto> getListaBiglietti() {
	
		listaBiglietti = BigliettoDAO.readAll();
		for (Biglietto b: listaBiglietti) {
			b.print();
		}
		return listaBiglietti;
	}
	
	public void stampaCorseCompatibili(StringBuffer prenotazioneScelta, ArrayList<Corsa> corseTrovate) throws naveNonTrovataException {
		System.out.println("Stampa corse compatibili con il tipo della prenotazione...");
        Iterator<Corsa> iterator = corseTrovate.iterator();
        
		while(iterator.hasNext()) {
            Corsa temp = new Corsa(iterator.next());
            Nave n = getNave(temp.getPersistentID());
            n.print();
            temp.setNaveAssociata(n);
            
            if((prenotazioneScelta.toString()).equals("a") && temp.getNaveAssociata() instanceof Traghetto) {
            	System.out.println("Tipo nave: " + temp.getNaveAssociata().getClass());
                System.out.println("Corsa: ");
                System.out.println("ID: "+temp.getCodice());
                System.out.println("Orario di partenza: "+temp.getOrarioPartenza());
                System.out.println("Porto di partenza: "+temp.getPortoPartenza());
                System.out.println("Orario di arrivo: "+temp.getOrarioArrivo());
                System.out.println("Porto di arrivo: "+temp.getPortoArrivo());
                System.out.println("Prezzo: "+temp.getPrezzo());
            } else if((prenotazioneScelta.toString()).equals("p")) {
            	System.out.println("Tipo nave: " + temp.getNaveAssociata().getClass());
                System.out.println("Corsa: ");
                System.out.println("ID: "+temp.getCodice());
                System.out.println("Orario di partenza: "+temp.getOrarioPartenza());
                System.out.println("Porto di partenza: "+temp.getPortoPartenza());
                System.out.println("Orario di arrivo: "+temp.getOrarioArrivo());
                System.out.println("Porto di arrivo: "+temp.getPortoArrivo());
                System.out.println("Prezzo: "+temp.getPrezzo());
            }
		}
	}
	
	/**************UTILITY PRENOTAZIONE*************/
	public boolean sceltaPrenotazione(StringBuffer prenotazioneScelta) {
		Scanner sc = new Scanner(System.in);
		
		prenotazioneScelta.insert(0,sc.nextLine());
		System.out.println(prenotazioneScelta);
		if (!((prenotazioneScelta.toString()).equals("p")) && !((prenotazioneScelta.toString()).equals("a"))) {
			System.out.print("Inserire un carattere valido! Inserisci di nuovo: ");
			return false;
		}
		
		return true;
	}

	public Corsa sceltaCorsa(MutableInt corsaScelta, ArrayList<Corsa> corseTrovate) {
		Scanner sc = new Scanner(System.in);
		corsaScelta.setValue(sc.nextInt());
		Corsa corsaDesiderata = isInLista(corseTrovate, corsaScelta.longValue());
		return corsaDesiderata;
		/*
		corsaDesiderata.print();
		if(corsaDesiderata==null) {
			System.out.println("Inserire un input valido. Inserisci di nuovo ID: ");
			return corsaDesiderata;
		}*/
		//return true;
	}

	public boolean confermaPrenotazione(StringBuffer conferma) {
		Scanner sc = new Scanner(System.in);
		conferma.insert(0,sc.nextLine());
		if (!((conferma.toString()).equals("s")) && !((conferma.toString()).equals("n"))) {
			System.out.print("Inserire un carattere valido! Inserisci di nuovo: ");
			return false;
		}		
		return true;
	}

	public static boolean inserimentoTarga(StringBuffer targaAuto) {
        Scanner sc = new Scanner(System.in);
        targaAuto.append(sc.nextLine());
        boolean alphanum = true;
        char ch = ' ';
        String controllo = targaAuto.toString();
        for(int i=0; i<controllo.length(); i++) {
            ch = controllo.charAt(i);
            if( !(ch >= 'A' && ch <= 'Z') && !(ch >= 'a' && ch <= 'z') && !(ch >= '0' && ch <= '9'))
            alphanum = false; 
        }
        if (targaAuto.length()!=7 || !alphanum) {
            System.out.print("Inserire una targa valida! Inserisci di nuovo: ");
        }
        return alphanum;
    }
	
	/***********************************************/
	
	public Biglietto emissioneBiglietto(String tipoBiglietto, String targa) throws naveNonTrovataException, SQLException {
		Biglietto b = null;
		
		try {
			int[] posti = new int[2];
			int postiRimanentiPersone;
			int postiRimanentiAutoveicoli;
			LocalDate data = LocalDate.now();
			DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = data.format(formatDate);
			
			LocalTime ora = LocalTime.now();
			DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
			String formattedTime = ora.format(formatTime);
			
			if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
				iterator = listaCorse.iterator();
				while(iterator.hasNext() && b==null) {
					Corsa c = new Corsa(iterator.next());
					//n = getNave(iterator.next().getPersistentID());   stessa di dopo
					n = getNave(iterator.next().getPersistentID());
					c.setNaveAssociata(n);
					posti = contaPosti(n);
					postiRimanentiPersone = posti[0];	
					if (postiRimanentiPersone>0) {
						b = new BPasseggero(formattedDate, formattedTime);
						BigliettoDAO.createBiglietto(b);
						aggiornaPosti(n,tipoBiglietto);
					}
				}
			} else if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_AUTOVEICOLO)) {
				iterator = listaCorse.iterator();
				while(iterator.hasNext() && b==null) {
					Corsa c = new Corsa(iterator.next());
					do {
						//n = getNave(iterator.next().getPersistentID());     stessa di dopo
						n = getNave(iterator.next().getPersistentID());
						c.setNaveAssociata(n);
						posti = contaPosti(n);
						postiRimanentiAutoveicoli = posti[1];
						if (postiRimanentiAutoveicoli>0) {
							b = new BAutoveicolo(formattedDate, formattedTime, targa);
							BigliettoDAO.createBiglietto(b);
							aggiornaPosti(n,tipoBiglietto);
						}
					} while (!(n instanceof Traghetto));
				}
			}
			if (b==null && tipoBiglietto.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
				throw new postiPersoneNonDisponibiliException();
			} else if (b==null && tipoBiglietto.equalsIgnoreCase(BIGLIETTO_AUTOVEICOLO)) {
				throw new postiAutoNonDisponibiliException();
			}
			return b;
		} catch (postiPersoneNonDisponibiliException p) {
			System.out.println("Posti persone finiti");
		} catch (postiAutoNonDisponibiliException p) {
			System.out.println("Posti persone finiti");
		}
		return b;
	}
	
	public boolean prenotaBiglietto()  throws naveNonTrovataException, IOException, corsaNonTrovataException, postiPersoneNonDisponibiliException, postiAutoNonDisponibiliException, SQLException {
    	ArrayList<Prenotazione> listaP = null;
    	StringBuffer prenotazioneScelta = new StringBuffer();
        getListaCorse();
        Scanner sc = new Scanner(System.in);
        System.out.print("Selezione tipo prenotazione (premere 'p' per persona o 'a' per autoveicolo): ");
        boolean risultato=false;
        
        do {
        	sceltaPrenotazione(prenotazioneScelta);
        } while(!((prenotazioneScelta.toString()).equals("p")) && !((prenotazioneScelta.toString()).equals("a")));
		
        ArrayList<Corsa> corseTrovate = getListaCorse();
        MutableInt corsaScelta=new MutableInt();
        stampaCorse(corseTrovate);
        
        if(corseTrovate.isEmpty()) {
            //Eccezione gestita in BCliente
            throw new corsaNonTrovataException("Errore!");
        }
        else {
        	stampaCorseCompatibili(prenotazioneScelta,corseTrovate);
            
            System.out.print("Selezionare l'ID della corsa desiderata: ");
            Corsa corsaDesiderata = new Corsa();
            boolean trovata = false;
            
            do {
            	corsaDesiderata = sceltaCorsa(corsaScelta, corseTrovate);
				corsaDesiderata.print();
            } while(corsaDesiderata==null);
            
            System.out.println("Corsa " + corsaScelta + " selezionata. Verifica posti");
            
            int[] posti = new int[2];
            (corsaDesiderata.getNaveAssociata()).print();
            posti = contaPosti(corsaDesiderata.getNaveAssociata());
            System.out.println(posti[0]);
            System.out.println(posti[1]);
            
            //controllo posti e conferma 
            if(corsaDesiderata.getNaveAssociata() instanceof Aliscafo && !(prenotazioneScelta.toString()).equals("p")) {
                if(posti[0] <= 0) {
                    //lancia eccezione postiPersoneNonDisponibili (verra'  gestita in BPrenotazione!)
                    throw new postiPersoneNonDisponibiliException("Errore posti passeggeri!");
                }
                else {
                    System.out.println("Conferma prenotazione per persona?(s/n)");
                    StringBuffer conferma= new StringBuffer();
                    do {
						confermaPrenotazione(conferma);
                    } while(!(conferma.toString()).equals("s") && !(conferma.toString()).equals("n"));
                    
                    if((conferma.toString()).equals("s")) {
                        PPasseggero p = new PPasseggero(true);
                        if(corsaDesiderata.getListaPrenotazioni()==null) {
                        	listaP = new ArrayList<Prenotazione>();
                        	corsaDesiderata.setListaPrenotazioni(listaP);
                        } else {
                        	(corsaDesiderata.getListaPrenotazioni()).add(p);
                        }
                        
                        p.setCorsaPrenotata(corsaDesiderata);
                        conferma(p, BIGLIETTO_PASSEGGERO);
                        risultato=true;
                    }
                    else if((conferma.toString()).equals("n")) {
                        System.out.println("Annullamento prenotazione...");
                        risultato=false;
                    }
                }
            }    
            else if(corsaDesiderata.getNaveAssociata() instanceof Traghetto) {
                if(posti[0] <= 0) {
                    //lancia eccezione postiPersoneNonDisponibili (verra' gestita in BPrenotazione!)
                    throw new postiPersoneNonDisponibiliException("Errore posti passeggeri!");

                }
                else if(posti[1] <= 0) {
                    //lancia eccezione postiAutoNonDisponibili (verra' gestita in BPrenotazione!)
                    throw new postiAutoNonDisponibiliException("Errore posti auto!");

                }
                else{
                    if((prenotazioneScelta.toString()).equals("p")) {
                    	System.out.println("Conferma prenotazione per persona?(s/n)");
                    	StringBuffer conferma=new StringBuffer();
                    	do {
							confermaPrenotazione(conferma);
                    	} while(!(conferma.toString()).equals("s") && !(conferma.toString()).equals("n"));
                    
                    	if((conferma.toString()).equals("s")) {
                        	PPasseggero p = new PPasseggero(true);
                        	
                        	if(corsaDesiderata.getListaPrenotazioni()==null) {
                            	listaP = new ArrayList<Prenotazione>();
                            	corsaDesiderata.setListaPrenotazioni(listaP);
                            } else {
                            	(corsaDesiderata.getListaPrenotazioni()).add(p);
                            }
                        	
                        	p.setCorsaPrenotata(corsaDesiderata);
                        	conferma(p, BIGLIETTO_PASSEGGERO);
                        	risultato=true;
                    	}
                    	else if((conferma.toString()).equals("n")) {
                    		System.out.println("Annullamento prenotazione...");
                    		risultato=false;
                    	}
                    }
                    else if((prenotazioneScelta.toString()).equals("a")) {
                        System.out.println("Conferma prenotazione per autoveicolo?(s/n)");
                        StringBuffer conferma=new StringBuffer();
                        do {
							confermaPrenotazione(conferma);
                    	} while(!(conferma.toString()).equals("s") && !(conferma.toString()).equals("n"));
                        if((conferma.toString()).equals("s")) {
                            StringBuffer targaAuto = new StringBuffer();
                            System.out.println("Inserire targa autoveicolo. Una targa e' una stringa composta da 7 caratteri alfanumerici.");
                            boolean cerca=true;
                            do {
                            	targaAuto.delete(0,targaAuto.length());
								cerca = inserimentoTarga(targaAuto);
                        	} while(targaAuto.length()!=7 || !cerca);
                            
                            PAutoveicolo p = new PAutoveicolo(true,targaAuto.toString());
                            if(corsaDesiderata.getListaPrenotazioni()==null) {
                            	listaP = new ArrayList<Prenotazione>();
                            	corsaDesiderata.setListaPrenotazioni(listaP);
                            } else {
                            	(corsaDesiderata.getListaPrenotazioni()).add(p);
                            }
                            
                            p.setCorsaPrenotata(corsaDesiderata);
                            p.setTarga(targaAuto.toString());

                            conferma(p, BIGLIETTO_AUTOVEICOLO);
                            risultato=true;
                        }
                        else if((conferma.toString()).equals("n")) {
                            System.out.println("Annullamento prenotazione...");
                            risultato=false;
                        }
                    }
                    
                }
            }
        }
        return risultato;
      
    }
    
    public void conferma(Prenotazione p, String tipo) {
        try {
			p.confermaP(tipo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    //IMPL IN ENTITY
    }
    
    public static Biglietto stampaBiglietto(long codice) {

		ArrayList<Biglietto> listaBiglietti = new ArrayList<Biglietto>();
		Biglietto matchBiglietto = null;

		listaBiglietti = BigliettoDAO.readAll();

		for(Biglietto b : listaBiglietti){
			if(codice == b.getCodice()){
				matchBiglietto = b;
			}
		}
		
		if (matchBiglietto!=null) {
			System.out.println(matchBiglietto);
			return matchBiglietto;
		} else {
			System.out.println("Parametri biglietto non validi.");
		}
		return null;
		
	}

}
