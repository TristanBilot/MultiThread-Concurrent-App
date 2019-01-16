package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;

/*import javax.mail.MessagingException;
import javax.mail.internet.AddressException;*/
import bibliothèque.Bibliothèque;

public class Appli {

	protected static final int PORT_RESERVATION = 2500, PORT_EMPRUNT = 2600, PORT_RETOUR = 2700; 
	
	public static void main(String[] args) {
		
		try {
			
			Bibliothèque.initBibliothèque();
			
			// Décommenter pour tester l'envoi de mail
			
			/*try {
				Bibliothèque.envoyerMail(Bibliothèque.getAbonné(1), Bibliothèque.getLivre(1));
			} catch (AddressException e) {e.printStackTrace();} catch (MessagingException e) {e.printStackTrace();}
			*/
			
			new Thread(new Serveur(PORT_RESERVATION)).start();
			System.out.println("[+] Serveur lancé sur le port " + PORT_RESERVATION);
			
			new Thread(new Serveur(PORT_EMPRUNT)).start();
			System.out.println("[+] Serveur lancé sur le port " + PORT_EMPRUNT);
			
			new Thread(new Serveur(PORT_RETOUR)).start();
			System.out.println("[+] Serveur lancé sur le port " + PORT_RETOUR + "\n");
						
			
		} catch (IOException e) {
			System.err.println("Erreur lors du lancement d'un serveur : " +  e);
		}
	}

}
