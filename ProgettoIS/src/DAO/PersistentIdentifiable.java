package DAO;

public interface PersistentIdentifiable {
	public String getPersistantID();
	
	/*
	 * Il metodo setPersistanceID dovrebbe essere non raggiungibile dall'utente ma 
	 * accessibile dal package DAO.
	 * Tuttavia, per semplicita', lo abbiamo reso public.
	 * 
	 * PersistantID corrisponde alla chiave primaria ID utilizzata per la memorizzazione della
	 * entita' nello schema logico del RDBMS.
	 */
	public void setPersistantID(String s);
}
