package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;
import java.net.ServerSocket;


public class Serveur implements Runnable {
	
	private ServerSocket listen_socket;
	private boolean allumé;
	private int port;
	
	Serveur (int port) throws IOException {
		listen_socket = new ServerSocket(port);
		allumé = true;
		this.port = port;
	}

	@Override
	public void run() {
		while (allumé) {
			try {
				if (port == Appli.PORT_RETOUR)
					new ServiceRetour(listen_socket.accept()).lancer();
				else if (port == Appli.PORT_EMPRUNT)
					new ServiceEmprunt(listen_socket.accept()).lancer();
				else if (port == Appli.PORT_RESERVATION)
					new ServiceRéservation(listen_socket.accept()).lancer();
				
				
			} catch (IOException e) {e.printStackTrace();}
		}
		
	}
	
	public static void afficherDansLaConsole(String msg) {
		System.out.println(msg);
	}
	
	protected void finalize() throws Throwable {
		try {
			this.listen_socket.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
	

}
