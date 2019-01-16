package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

public class Abonné {
	private int numéro;
	private String nom;
	private boolean estInterdit; // false si autorisé sinon true pendant 1 mois: ne peux pas emprunter
	
	public Abonné(String nom) {
		this.nom = nom;
		this.numéro = Bibliothèque.getNbAbonnés();
		this.estInterdit = false;
	}
	
	/* cet abonné est réservé à la première instance de
	 *  propriétaire de chaque livre lors de l'emprunt 
	 */
	public Abonné() { 
		this.nom = null;
	}

	public int getNuméro() {
		return numéro;
	}

	public String getNom() {
		return nom;
	}

	public boolean estInterdit() {
		return estInterdit;
	}

	public void setEstInterdit(boolean estInterdit) {
		this.estInterdit = estInterdit;
	}
		
	
}
