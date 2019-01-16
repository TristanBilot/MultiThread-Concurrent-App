package timer;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.util.Timer;
import java.util.TimerTask;
import bibliothèque.Abonné;
import serveur.Serveur;

public class TimerInterdiction {
	
	private Abonné cible;  // le propriétaire OU le réservateur du livre qui va subir le Timer
	private final int délai = 10000; // 60 sec, 60min, 24h, 30j, 1000ms = 30 jours
	
	/*
	 * Ce timer est lancé lorsqu'un livre est rendu en retard ou en mauvais état (nombre aléatoire).
	 * L'abonné sera alors supendu d'emprunt pendant 1mois
	 */
	
	
	public TimerInterdiction(Abonné a) {
		this.cible = a;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				// Après un mois de longue attente, l'abonné peut à nouveau réserver
					cible.setEstInterdit(false);
					Serveur.afficherDansLaConsole("[+] L'abonné: " + cible.getNom() + " n'est plus interdit d'emprunt après " + (délai / 1000) + " secondes d'interdiction.");
			  }
		}, délai);
	}

}
