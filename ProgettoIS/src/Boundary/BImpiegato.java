package Boundary;

import java.io.IOException;
import java.sql.SQLException;

import Control.*;
import Entity.*;

import java.io.BufferedReader;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Iterator;

public class BImpiegato {

	protected static Scanner tastiera;
	protected static ArrayList<Corsa> listaCorse = new ArrayList<Corsa>();
	protected static Corsa corsa=null;
	private static final String BIGLIETTO_PASSEGGERO = "PASSEGGERO";
	private static final String BIGLIETTO_AUTOVEICOLO = "AUTOVEICOLO";
	
	protected static String askUser(String message) throws IOException {
		System.out.print(message);
		System.out.flush();
		return tastiera.nextLine();
	}
	
	public static Corsa[] getListaCorse(ArrayList<Corsa> listaCorse) {
		return listaCorse.toArray(new Corsa[listaCorse.size()]);
	}
	
	public static void stampaCorse(ArrayList<Corsa> listaCorse) {
		System.out.println("Lista corse:");
		Corsa[] lista_corse = getListaCorse(listaCorse);
		for (int i=0; i < lista_corse.length; i++) {
			System.out.print("\t");
			lista_corse[i].print();
		}
		System.out.flush();
	}
	
	protected static boolean isValid(String in) {
		boolean[] v = new boolean[in.length()];
		for(int i=0; i < 2; i++) {
	         boolean flag = Character.isDigit(in.charAt(i));
	         if(flag) {
	            v[i] = true;
	         }
	         else {
	        	 v[i] = false;
	         }
	    }
		Character c = ':';
		if(((Character)in.charAt(2)).equals(c)) {
			v[2] = true;
		} else {
       	 	v[2] = false;
        }
		for(int i=3; i < 5; i++) {
	         boolean flag = Character.isDigit(in.charAt(i));
	         if(flag) {
	            v[i] = true;
	         }
	         else {
	        	 v[i] = false;
	         }
	    }
		int count = 0;
		for (int i=0; i < 5; i++) {
			if(v[i]==true) {
				count++;
			}
		}
		
		if (count==5) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int stringLenght = str.length();
		for (int i = 0; i < stringLenght; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public static Corsa ricercaCorsaConCodice() throws IOException {
		System.out.println("Ricerca corsa: ");
		try {
			long codice = Long.parseLong(askUser("Inserisci codice: "));
			corsa = GestoreVenditaBiglietti.Instance().ricercaCorsa(codice);
			
			if (corsa!=null) {
				System.out.println("La ricerca ha prodotto: ");
				corsa.print();
			}
			
			return corsa;
			
		} catch(IOException i) {
			throw new IOException("input sbagliato");
		} 
		
		
	}
	
	public static Corsa ricercaCorsa() throws IOException, invalidInput {
		System.out.println("Ricerca corsa: ");
		try {
			String orarioPartenza = askUser("Inserisci orario di partenza, nel formato XX:YY (XX ora e YY  minuti): ");
			if (!(isValid(orarioPartenza))) {
				throw new invalidInput("Input non valido");
			}
			String portoPartenza = askUser("Inserisci porto di partenza: ");
			String orarioArrivo = askUser("Inserisci orario di arrivo, nel formato XX:YY (XX ora e YY  minuti): ");
			if (!(isValid(orarioArrivo))) {
				throw new invalidInput("Input non valido");
			}
			String portoArrivo = askUser("Inserisci porto di arrivo: ");
			
			corsa = GestoreVenditaBiglietti.Instance().ricercaCorsa(orarioPartenza, portoPartenza, orarioArrivo, portoArrivo);
			
			if (corsa!=null) {
				System.out.println("La ricerca ha prodotto: ");
				corsa.print();
			}
			
			return corsa;
			
		} catch(invalidInput i) {
			throw new invalidInput("Input non valido");
		} catch(IOException i) {
			throw new IOException("input sbagliato");
		} 
		
		
	}
	
	public static Corsa ricercaCorse() throws IOException, invalidInput {
		System.out.println("Ricerca corsa: ");
		Corsa corsaTrovata=null;
		try {
			String orarioPartenza = askUser("Inserisci orario di partenza, nel formato XX:YY (XX ora e YY  minuti): ");
			if (!(isValid(orarioPartenza))) {
				throw new invalidInput("Input non valido");
			}
			String portoPartenza = askUser("Inserisci porto di partenza: ");
			String orarioArrivo = askUser("Inserisci orario di arrivo, nel formato XX:YY (XX ora e YY  minuti): ");
			if (!(isValid(orarioArrivo))) {
				throw new invalidInput("Input non valido");
			}
			String portoArrivo = askUser("Inserisci porto di arrivo: ");
			
			listaCorse = GestoreVenditaBiglietti.Instance().ricercaCorse(orarioPartenza, portoPartenza, orarioArrivo, portoArrivo);
			
			System.out.println("Le corse che soddisfano i tue criteri sono le seguenti: ");
			stampaCorse(listaCorse);
			
			System.out.println("Seleziona una corsa digitando il codice: ");
			
			Scanner tast = new Scanner(System.in);
			long codice = tast.nextLong();
			
			Iterator<Corsa> it = listaCorse.iterator();
			for(int i=0; i < listaCorse.size(); i++) {
				if(listaCorse.get(i).getCodice()==codice) {
					corsaTrovata = listaCorse.get(i);
				}
			}

			corsaTrovata.print();
			return corsaTrovata;
			
		} catch(invalidInput i) {
			System.out.println("Errore");
		} catch(IOException i) {
			System.out.println("Errore");
		}
		return corsaTrovata;
		
	}
	
	public static void emissioneBiglietto() {
		try {
			Corsa c = null;
			System.out.println("Emetto Biglietto...");
			System.out.println("Prima seleziona il tipo di ricerca di corsa");
			tastiera = new Scanner(System.in);
			int option=0;
			Biglietto b = null;
		
			do {
				System.out.println("Seleziona operazione: \n" +
						"\t1) Ricerca corsa per codice\n" +
						"\t2) Ricerca prima corsa che soddisfa i criteri\n" +
						"\t3) Seleziona la corsa tra tutte le corse che soddisfano i criteri\n" +
						"\t4) Esci");
				System.out.flush();
				
				
				try {
					option=Integer.parseInt(tastiera.nextLine());
				} catch (Exception e) {
					option = 0;
				}
				
				switch (option) {
					case 1: { c = ricercaCorsaConCodice(); break; }
					case 2: { c = ricercaCorsa(); break; }
					case 3: { c = ricercaCorse(); break; }
					case 4: { return; }
					default: {
						System.out.println("Carattere inserito non riconosciuto!\n");
					}
				}
				
				if (c!=null) {
					String tipoBiglietto = askUser("Inserisci il tipo del biglietto, digita 'Passeggero' o 'Autoveicolo': ");
					if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_PASSEGGERO)) {
						String targa = null;
						b = GestoreVenditaBiglietti.Instance().emissioneBiglietto(tipoBiglietto, targa);
					} else if (tipoBiglietto.equalsIgnoreCase(BIGLIETTO_AUTOVEICOLO)) {
						String targa = askUser("Inserisci la targa dell'autoveicolo: ");
						b = GestoreVenditaBiglietti.Instance().emissioneBiglietto(tipoBiglietto, targa);
					}
					
					System.out.print("Biglietto: ");
					b.print();
					
				} else {
					throw new corsaNonTrovataException();
				}
				
			} while (option != 4);
		} catch (corsaNonTrovataException e) {
			System.out.println("Errore");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Errore");
		} catch (Exception e) {
			System.out.println("Errore");
		}
		
	}
	
	public static void stampaBiglietto() throws IOException, invalidInput, SQLException {
		
		String codiceBiglietto = askUser("Inserisci il codice del biglietto: ");

		if(!(isNumeric(codiceBiglietto))){
			throw new invalidInput("Input non valido");
		}

		long codice = Long.parseLong(codiceBiglietto);

		GestoreVenditaBiglietti.stampaBiglietto(codice);
		
	}
	
	
	public static void main(String[] args) {
		try {
			GestoreVenditaBiglietti.Instance().getListaCorse();
			tastiera = new Scanner(System.in);
			int option=0;
			do {
				System.out.println("Seleziona operazione: \n" +
						"\t1) Ricerca Corsa\n" +
						"\t2) Stampa Biglietto\n" +
						"\t3) Emissione biglietto\n" +
						"\t4) Esci");
				System.out.flush();
			
				try {
					option=Integer.parseInt(tastiera.nextLine());
				} catch (Exception e) {
					option = 0;
				}
			
				switch (option) {
					case 1: { ricercaCorsa(); break; }
					case 2: { stampaBiglietto(); break; }
					case 3: { emissioneBiglietto(); break; }
					case 4: { System.out.println("Applicazione terminata"); return; }
					default: {
						System.out.println("Carattere inserito non riconosciuto!\n");
					}
				}
			} while (option != 4);
		} catch (IOException e1) {
			System.err.println("Si e' verificato un errore di I/O: " + e1.getMessage());
			e1.printStackTrace();
		}catch (Exception e3) {
			e3.printStackTrace();
		}
	}
	
}
