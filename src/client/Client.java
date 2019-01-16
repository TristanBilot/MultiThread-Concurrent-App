package client;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;
import java.net.Socket;
import serveur.LecteurScanner;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket socket = null;
		LecteurScanner scanner = null;
		int nbLivres = 9; // a changer
		
		String HOST = args[0]; // IP du serveur hôte
		int PORT = Integer.parseInt(args[1]);  // le numéro de Port du service		
		
		//String HOST = "127.0.0.1";
		/* 
		 * 2500 => réservation
		 * 2600 => emprunt	
		 * 2700 => retour
		*/
		//int PORT = 2700;
		
		socket = new Socket(HOST, PORT);
		scanner = new LecteurScanner(socket);	
		System.out.println("[+] Vous êtes connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
		
		
		//if (PORT == 2700)  // Retour
		//	scanner.write(numéroAbonné); // envoie du numéro d'abonné au serveur car il n'est pas demandé au client
												
		/* affichage de tous les livres + (la réponse du succès de 
		connexion au service et la phrase de choix du livre) */
		//System.out.println(Bibliothèque.getNbLivres());
		for (int i = 0; i <= nbLivres + 2; i++)
			System.out.println(scanner.read()); // réponse du service
		scanner.envoyerClavierAuServeur(); // réponse du client (num de livre)	
		
		if (PORT == 2500 || PORT == 2600) { // On demande le numéro d'abonné uniquement pour ces deux ports
			System.out.println(scanner.read()); // affichage : "Entrez votre numéro d'abonné"
			scanner.envoyerClavierAuServeur(); // envoie du numéro d'abonné
		}
		
			System.out.println(scanner.read()); // affichage : "votre livre a été emprunté/réservé..."
			System.out.println(scanner.read()); // exception
		
		
		
			
		
		
	}

}
