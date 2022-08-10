package Entity;

public class Nave implements Cloneable {
	protected long codice;
	protected String nome;
	protected int capienzaPersone;
	protected int postiRimanentiPersone;
	protected Corsa corsaAndata;
	protected Corsa corsaRitorno;
	protected String persistentID;
	
	/**COSTRUTTORI*/
	public Nave() {
		super();
		this.codice=0;
		this.nome="";
		this.capienzaPersone=0;
		this.postiRimanentiPersone=0;
		this.corsaAndata = null;
		this.corsaRitorno = null;
	}
	
	public Nave(long codice, String s, int capienza) {
		super();
		this.codice=codice;
		this.nome = s;
		this.capienzaPersone = capienza;
		this.postiRimanentiPersone = capienzaPersone;
		this.persistentID=String.valueOf(codice);
	}
	
	public Nave(long codice, String s, int capienza, Corsa ca, Corsa cr) {
		this(codice, s, capienza);
		this.corsaAndata = ca;
		this.corsaRitorno = cr;
	}
	
	public Nave(Nave n) {
		super();
		this.codice = n.codice;
		this.nome = n.nome;
		this.capienzaPersone = n.capienzaPersone;
		this.postiRimanentiPersone = capienzaPersone;
		this.corsaAndata = n.corsaAndata;
		this.corsaRitorno = n.corsaRitorno;
		
	}
	
	/***FUNZIONI GET E SET**/
	public long getCodice() {
		return this.codice;
	}
	
	public void setCodice(long codice) {
		this.codice=codice;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCapienzaPersone() {
		return this.capienzaPersone;
	}
	
	public void setCapienzaPersone(int capienzaPersone) {
		this.capienzaPersone = capienzaPersone;
	}
	
	public int getPostiRimanentiPersone() {
		return this.postiRimanentiPersone;
	}
	
	public void setPostiRimanentiPersone(int postiRimanentiPersone) {
		this.postiRimanentiPersone = postiRimanentiPersone;
	}
	
	public Corsa getCorsaAndata() {
		return this.corsaAndata;
	}
	
	public void setCorsaAndata(Corsa ca) {
		this.corsaAndata = ca;
	}
	
	public Corsa getCorsaRitorno() {
		return this.corsaRitorno;
	}
	
	public void SetCorsaRitorno(Corsa cr) {
		this.corsaRitorno = cr;
	}
	
	public String getPersistentID() {
		return this.persistentID;
	}
	
	public void setPersistentID(String persistentID) {
		this.codice = Long.parseLong(persistentID);
		this.persistentID=persistentID;
	}
	
	public int contaPosti() {
		return getPostiRimanentiPersone();
	}
	
	public String toString() {
		return ("ID = " + persistentID + ", nome = " + nome +
				", capienza persone = " + capienzaPersone);
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public boolean equals(Object altroOggetto) {
		if (altroOggetto == null) {
			return false;
		} else if (!(altroOggetto instanceof Nave)) {
			return false;
		} else {
			Nave n = (Nave)altroOggetto;
			return (persistentID.equals(n.persistentID));
		}
	}


}
