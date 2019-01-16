package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

public interface IDocument {
	int numero();
	void reserver(Abonné ab) throws PasLibreException ;
	void emprunter(Abonné ab) throws PasLibreException;
	void retour();
}
