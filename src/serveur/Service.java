package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.net.Socket;

public abstract class Service implements Runnable {
	
	private Socket client;
	private LecteurScanner scanner;
	
	public Service(Socket client) {
		this.client = client;
		this.scanner = new LecteurScanner(client);
	}
	
	public void lancer() {
		new Thread(this).start();
	}

	public Socket getClient() {
		return client;
	}

	public LecteurScanner getScanner() {
		return scanner;
	}
	
}
