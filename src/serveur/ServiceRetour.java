package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.IOException;
import java.net.Socket;

import bibliothèque.Bibliothèque;
import bibliothèque.PasLibreException;

public class ServiceRetour extends Service {

	public ServiceRetour(Socket client) {
		super(client);
	}

	@Override
	public void run() {
		try {			
			//int numAbo = Integer.parseInt(super.getScanner().read()); 
			int numLivre;
			StringBuilder sb = new StringBuilder();
			Serveur.afficherDansLaConsole("===> Nouvelle connexion : RETOUR\n");
			sb.append("Vous êtes maintenant connecté au service de retour !\n");
			sb.append("Quel livre voulez-vous retourner ?\n");
			for (int i = 1; i <= Bibliothèque.getNbLivres(); i++) 
				sb.append("   " + i + " ==> " + Bibliothèque.getLivre(i).getLibéllé() + "\n");			
			super.getScanner().write(sb.toString());
			
			numLivre = super.getScanner().readInt();
			if (numLivre >= 1 && numLivre <= Bibliothèque.getNbLivres()) {
				if (!Bibliothèque.getLivre(numLivre).estDisponible()) {
					if (!Bibliothèque.getLivre(numLivre).estRéservé()) {
						Bibliothèque.getLivre(numLivre).retour(); // méthode de retour
						super.getScanner().write("Votre livre : " + Bibliothèque.getLivre(numLivre).getLibéllé() + " a été retourné avec succès.\n");
					}
					else
						super.getScanner().write(new PasLibreException("Vous ne pouvez pas retourner ce livre car il est réservé.\n").getMessage());
				}
				else
					super.getScanner().write(new PasLibreException("Vous ne pouvez pas retourner ce livre car il est disponible.\n").getMessage());
			}
			else
				super.getScanner().write(new PasLibreException("Le numéro entré est invalide, veuillez relancer l'application.\n").getMessage());
		
		} catch (IOException e) { e.printStackTrace(); }
	}
	
}
