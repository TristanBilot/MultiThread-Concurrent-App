package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBibliothèque {

	/*
	 * Nous ne pouvons pas simuler la méthodes de réservation car elle utilise des conditions
	 * qui sont das le code du runnable du service et il faut interagir via la console du client
	 * pour parler au serveur. J'ai donc testé ces fonctions par la console client directement.
	 */
	
	@Test
	void test() throws PasLibreException {
		
		Bibliothèque.initBibliothèque(); // initialise une hashmap d'abonné(e)s et de livres
		Livre livre = Bibliothèque.getLivre(1);
		Abonné abonné = Bibliothèque.getAbonné(1);
		
		assertTrue(livre.estDisponible());
		assertFalse(livre.estRéservé());
		assertTrue(livre.getRéservateur() == null);
		assertTrue(livre.getPropriétaire() instanceof Abonné); // car le premier propriétaire est un abonné anonyme
		
		livre.reserver(abonné);
		assertFalse(livre.getPropriétaire().equals(abonné)); // car il n'y a pas encore de réservation
		assertTrue(livre.getRéservateur().equals(abonné));
		
		livre.emprunter(abonné);
		assertTrue(livre.getPropriétaire().equals(abonné));
		assertFalse(livre.estRéservé());
		assertFalse(livre.estDisponible());
		
		livre.retour();
		assertTrue(livre.getPropriétaire() == null);
		assertTrue(livre.estDisponible());
		assertFalse(livre.estRéservé());
				
	}

}
