package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import serveur.Serveur;
import timer.TimerInterdiction;
import timer.TimerInterdictionRetard;
import timer.TimerRéservation;

public class Livre implements IDocument {
	 
	private int numéro;
	private String libellé;
	private boolean disponible;
	private boolean réservé;
	private Abonné propriétaire;
	private Abonné réservateur;
	/* Certification 2 */
	private boolean attendu; // lorsqu'un abonné a reçu un mail pour pouvoir réserver le livre
	private Abonné abonnéEnAttente; // l'abonné a qui le mail a été envoyé, il attend
	
	public Livre() {
		this.disponible = true; // au départ, le livre est disponible
		this.réservé = false;
		this.propriétaire = new Abonné(); // abonné avec nom = null
		//this.réservateur = new Abonné();
		this.numéro = Bibliothèque.getNbLivres();
	}
	
	public Livre(String nom) {
		this();
		this.libellé = nom;
	}
	
	@Override
	public synchronized int numero() {
		return this.numéro;
	}
	
	@Override
	public synchronized void reserver(Abonné ab) throws PasLibreException {
		this.réservateur = ab;
		this.réservé = true;
		this.abonnéEnAttente = null; // l'abonné en attente n'a pas été assez rapide, il perd sa place
		this.attendu = false;
		new TimerRéservation(ab, this);
	}
	/* Les vérification de l'utilisation de la méthode emprunter sont
	 * présentes dans chaque service avant son utilisation pour
	 * pouvoir retourner une erreur au client.
	 */
	@Override
	public synchronized void emprunter(Abonné ab) throws PasLibreException {
		this.propriétaire = ab;
		this.disponible = false;
		this.réservé = false;
		this.réservateur = null;
		/* Certification 1 */
		// lancement d'un timer durant 2 semaines, au delà, le propriétaire est marqué "interdit"
		new TimerInterdictionRetard(ab);
	}
	@Override
	public void retour() {
		/* Certification 1 */ 
		// le but est ici de simuler l'aléatoire pour la dégradation d'un livre par un abonné
		Double pourcentageDeChanceDeDégradation =  Math.random() * 10;
		if (pourcentageDeChanceDeDégradation <= 9) { // 20% de chance de dégradation du livre
			propriétaire.setEstInterdit(true); // interdiction
			new TimerInterdiction(propriétaire); // timer de 1 mois déclenché
			// Attention à bien mettre le propriétaire et non le réservateur
			Serveur.afficherDansLaConsole("[+] L'abonné " + propriétaire.getNom() + " est privé d'emprunt pendant 1 mois après avoir dégradé son livre.");
		}
		this.propriétaire = null;
		this.disponible = true;
		
		/* Certification 2 */
		// envoi du mail si le livre est attendu
		if (this.attendu) {
			try {
				Bibliothèque.envoyerMail(abonnéEnAttente, this); //envoi du mail d'information à la personne en attente
			} catch (AddressException e) { e.printStackTrace(); } catch (MessagingException e) { e.printStackTrace(); }
		}
	}

	/* Certification 2 */
	public synchronized void setDésiré(boolean désiré, Abonné abonné) {
		this.attendu = désiré;
		this.abonnéEnAttente = abonné;
	}
	
	public synchronized String getLibéllé() {
		return libellé;
	}
	
	public synchronized boolean estDisponible() {
		return this.disponible;
	}

	public synchronized boolean estRéservé() {
		return réservé;
	}

	public synchronized Abonné getPropriétaire() {
		return propriétaire;
	}

	public synchronized void setRéservé(boolean réservé) {
		this.réservé = réservé;
	}

	public synchronized void setRéservateur(Abonné réservateur) {
		this.réservateur = réservateur;
	}

	public synchronized Abonné getRéservateur() {
		return this.réservateur;
	}
	
	public synchronized void setDisponible(boolean b) {
		this.disponible = b;
	}

	public synchronized void setPropriétaire(Abonné propriétaire) {
		this.propriétaire = propriétaire;
	}

	public synchronized int getNuméro() {
		return numéro;
	}
	
}
