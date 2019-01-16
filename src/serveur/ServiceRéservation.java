package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import bibliothèque.PasLibreException;

public class ServiceRéservation extends Service {

	public ServiceRéservation(Socket client) {
		super(client);
	}
	
	@Override
	public void run() {
		int numAbo = 0, numLivre;
		StringBuilder sb = new StringBuilder();
		Serveur.afficherDansLaConsole("===> Nouvelle connexion : RESERVATION\n");
		sb.append("Vous êtes maintenant connecté au service de réservation !\n");
		sb.append("Quel livre voulez-vous réserver ?\n");
		for (int i = 1; i <= Bibliothèque.getNbLivres(); i++) 
			sb.append("   " + i + " ==> " + Bibliothèque.getLivre(i).getLibéllé() + "\n");
		
		super.getScanner().write(sb.toString());
		
		try {
			numLivre = Integer.parseInt(super.getScanner().read()); // le livre choisi par le client
			if (numLivre >= 1 && numLivre <= Bibliothèque.getNbLivres()) {
	
				if (Bibliothèque.estDisponible(numLivre)) { // vérification de la disponiblité du livre
					super.getScanner().write("Veuillez taper votre numéro d'abonné : \n");
					numAbo = Integer.parseInt(super.getScanner().read()); // le numéro d'abonné
					if (numAbo >= 1 && numAbo <= Bibliothèque.getNbAbonnés()) { 
						if (!Bibliothèque.getLivre(numLivre).estRéservé()) { // si le livre n'est pas réservé, on réserve
							try {
								
								Bibliothèque.getLivre(numLivre).reserver(Bibliothèque.getAbonné(numAbo)); // réservation
							} 
							catch (PasLibreException e) { System.out.println(e.getMessage()); }
							super.getScanner().write("Votre livre : " + Bibliothèque.getLivre(numLivre).getLibéllé() + " a été réservé avec succès. Vous avez 2 heures pour venir le chercher à l'accueil.\n");
						}
						else
							super.getScanner().write(new PasLibreException("Ce livre est déjà réservé !").getMessage()); // déja réservé
					}
					else 
						super.getScanner().write(new PasLibreException("Votre numéro d'abonné n'est pas correct !").getMessage()); // numéro abonné erroné					
				}
				else {
					super.getScanner().write(new PasLibreException("Ce livre n'est pas disponible, tapez votre numéro d'abonné si vous voulez recevoir un mail d'alerte.").getMessage()); // livre indisponible
					/* Certification 2 */
					/* Lancement de la méthode responsable de la disponibilité du livre + avertissement à l'utilisateur*/
					super.getScanner().write("Veuillez taper votre numéro d'abonné : \n");
					numAbo = Integer.parseInt(super.getScanner().read());
					Bibliothèque.getLivre(numLivre).setDésiré(true, Bibliothèque.getAbonné(numAbo)); // met le livre comme désiré par l'abonné
					Serveur.afficherDansLaConsole("[+] L'abonné "+ Bibliothèque.getAbonné(numAbo).getNom() + " recevra un mail lorsque le livre qu'il voulait sera à nouveau disponible.");
				}
			}
			else
				super.getScanner().write(new PasLibreException("Le numéro entré est invalide, veuillez relancer l'application.\n").getMessage());
		}
		catch (IOException e) { e.printStackTrace(); }
	}
	
}
