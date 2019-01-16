package timer;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.util.Timer;
import java.util.TimerTask;
import bibliothèque.Abonné;
import serveur.Serveur;

public class TimerInterdictionRetard {

	private Abonné cible;  // le propriétaire OU le réservateur du livre qui va subir le Timer
	private final int DELAI = 10000; // 60 * 60 * 24 * 14 * 1000; // 60 sec, 60min, 24h, 14j, 1000ms = 14 jours	
	
	/*
	 * Ce timer est lancé lorsqu'un livre est rendu en retard ou en mauvais état (nombre aléatoire).
	 * L'abonné sera alors supendu d'emprunt pendant 1mois
	 */

	public TimerInterdictionRetard(Abonné a) {
		this.cible = a;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  cible.setEstInterdit(true);
					// Après un 2 semaines on marque le propriétaire "interdit" et on lance le timer d'interdiction d'1 mois
					Serveur.afficherDansLaConsole("[+] L'abonné: " + cible.getNom() + " n'a pas rendu son livre après 2 semaines, il est donc interdit d'emprunt pendant 1 mois.");
					new TimerInterdiction(cible); // 1 mois d'interdiction
			}
		}, DELAI);
	}

}
