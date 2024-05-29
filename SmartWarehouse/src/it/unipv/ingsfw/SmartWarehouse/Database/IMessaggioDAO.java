package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;
public interface IMessaggioDAO {

	public List<Messaggio> getMessaggi(User mittente, User destinatario);
	public void inserisciMessaggio(Messaggio messaggio);
	public List<Messaggio> getMessaggiNonLetti(User mittente, User destinatario);
	public void setMessaggioLetto(Messaggio messaggio);
	
}