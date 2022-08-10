package Entity;

public class BPasseggero extends Biglietto{
	/**COSTRUTTORI*/
	public BPasseggero() {
		super();
	}
	
	public BPasseggero(String data, String ora) {
		super(data, ora);
	}
	
	public BPasseggero(String data, String ora, Corsa c, Impiegato i) {
		super(data, ora, c, i);
	}
	
	public BPasseggero(BPasseggero b) {
		super(b);
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof BPasseggero)) {
			return false;
		} else {
			BPasseggero b = (BPasseggero)altroOggetto;
			return (super.equals(b));
		}
	}

}
