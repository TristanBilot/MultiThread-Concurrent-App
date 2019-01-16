package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import bibliothèque.PasLibreException;

public class ServiceEmprunt extends Service {

	public ServiceEmprunt(Socket client) {
		super(client);
	}

	@Override
	public void run() {
		int numLivre, numAbo;
		boolean réponseCorrecte = false;
		StringBuilder sb = new StringBuilder();
		Serveur.afficherDansLaConsole("===> Nouvelle connexion : EMPRUNT\n");
		sb.append("Vous êtes maintenant connecté au service d'emprunt !\n");
		sb.append("Quel livre voulez-vous emprunter ?\n");
		for (int i = 1; i <= Bibliothèque.getNbLivres(); i++) 
			sb.append("   " + i + " ==> " + Bibliothèque.getLivre(i).getLibéllé() + "\n");
		
		super.getScanner().write(sb.toString()); // envoie de la liste de livres au client
		
		while (!réponseCorrecte) {
			try {
				numLivre = Integer.parseInt(super.getScanner().read()); // le livre choisi par le client
				if (numLivre >= 1 && numLivre <= Bibliothèque.getNbLivres()) {
					réponseCorrecte = true;
					//  check
					if (Bibliothèque.estDisponible(numLivre)) { // vérification de la disponiblité du livre
						super.getScanner().write("Veuillez taper votre numéro d'abonné : \n");
						numAbo = Integer.parseInt(super.getScanner().read()); // le numéro d'abonné
						if (numAbo >= 1 && numAbo <= Bibliothèque.getNbAbonnés()) {
							if (!Bibliothèque.getAbonné(numAbo).estInterdit()) {
								if (!Bibliothèque.getLivre(numLivre).estRéservé()) { // si le livre n'est pas réservé, on accepte l'emprunt directement								
									try {
										
										Bibliothèque.getLivre(numLivre).emprunter(Bibliothèque.getAbonné(numAbo)); // emprunt
									} 
									catch (PasLibreException e) { System.out.println(e.getMessage()); }
									super.getScanner().write("Votre livre : " + Bibliothèque.getLivre(numLivre).getLibéllé() + " a été emprunté avec succès.\n");
								
								}
							else { // Si l'emprunt est réservé, on vérifie que le client est bien le réservateur
								if (Bibliothèque.getAbonné(numAbo).equals(Bibliothèque.getLivre(numLivre).getRéservateur())) {
									try {
										Bibliothèque.getLivre(numLivre).emprunter(Bibliothèque.getAbonné(numAbo)); // emprunt
										Serveur.afficherDansLaConsole("L'abonné : " + Bibliothèque.getAbonné(numAbo).getNom() + " à récupéré son livre " + Bibliothèque.getLivre(numLivre).getLibéllé() + " après l'avoir réservé.");
										super.getScanner().write("Votre livre réservé : " + Bibliothèque.getLivre(numLivre).getLibéllé() + " vous attendait bien au chaud !\n");
									} 
									catch (PasLibreException e) { System.out.println(e.getMessage()); }
								}
								else
									super.getScanner().write(new PasLibreException("Ce livre est déjà réservé !").getMessage()); // numéro abonné erroné
								}		
							}
							else
								super.getScanner().write(new PasLibreException("Vous êtes interdit de livre, vous ne pouvez pas emprunter !").getMessage()); // numéro abonné erroné
						}
						else 
							super.getScanner().write(new PasLibreException("Votre numéro d'abonné n'est pas correct !").getMessage()); // numéro abonné erroné						
					}
					else 
						super.getScanner().write(new PasLibreException().getMessage()); // livre indisponible											
				}
				else
					super.getScanner().write(new PasLibreException("Le numéro entré est invalide, veuillez relancer l'application.\n").getMessage());
			}
			catch (IOException e) { e.printStackTrace(); }
		}
	}

}
