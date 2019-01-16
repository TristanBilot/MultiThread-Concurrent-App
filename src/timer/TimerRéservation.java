package timer;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.util.Timer;
import java.util.TimerTask;
import bibliothèque.Abonné;
import bibliothèque.Livre;
import serveur.Serveur;

public class TimerRéservation {
	
	private Abonné cible;  // le propriétaire OU le réservateur du livre qui va subir le Timer
	private Livre livre; // le livre concerné
	private final int DELAI = 10000;// 60 sec, 60min, 2h, 1000millisec = 2h
	
	/*
	 * Ce timer est lancé à chaque réservation, un chronomètre va alors se
	 * déclencher dans ce nouveau thread et patienter le temps désiré 
	 * avant de libérer le livre en question s'il n'a pas été récupéré avant
	 */
	
	public TimerRéservation(Abonné a, Livre l) {
		this.cible = a;
		this.livre = l;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				// si le réservateur n'est pas le propriétaire après les 2 heures, alors c'est qu'il n'est pas allé l'emprunté
				  if (!livre.getPropriétaire().equals(cible)) { 
						livre.setRéservé(false);
						livre.setRéservateur(null);
						livre.setDisponible(true);
						Serveur.afficherDansLaConsole("[+] L'abonné : " + cible.getNom() + " n'est pas venu chercher son livre à temps !");
						Serveur.afficherDansLaConsole("[+] Son livre "+ livre.getLibéllé() + " est à nouveau disponible.");	
				  }
			}
		}, DELAI);
	}
	
}
