package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

public class PasLibreException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasLibreException() {
		super("Ce livre n'est pas actuellement disponible !");
	}
	
	public PasLibreException(String msg) {
		super(msg);
	}
}
