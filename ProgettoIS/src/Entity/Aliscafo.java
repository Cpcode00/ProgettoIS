package Entity;

public class Aliscafo extends Nave {

	/**COSTRUTTORI*/
	public Aliscafo() {
		super();
	}
	
	public Aliscafo(long codice, String s, int capienza) {
		super(codice, s, capienza);
	}
	
	public Aliscafo(long codice, String s, int capienza, Corsa ca, Corsa cr) {
		super(codice, s, capienza, ca, cr);
	}
	
	public Aliscafo(Aliscafo a) {
		super(a);
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Nave)) {
			return false;
		} else {
			Nave n = (Nave)altroOggetto;
			return (super.equals(n));
		}
	}
	
}
