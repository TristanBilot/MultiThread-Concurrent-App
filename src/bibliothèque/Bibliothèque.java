package bibliothèque;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import serveur.Serveur;

public class Bibliothèque {

	private static HashMap<Integer, Livre> livres;
	private static HashMap<Integer, Abonné> abonnés;
	

	private static int NB_LIVRES = 0; // sert de compteur pour attribuer les numéros de livre
	private static int NB_ABONNES = 0; // sert de compteur pour attribuer les numéros d'abonnés
	
	public Bibliothèque() {
		livres = new HashMap<Integer, Livre>();
		abonnés = new HashMap<Integer, Abonné>();
	}
	
	public static void initBibliothèque() {
		livres = new HashMap<>(); // on initialise aussi dans la fonction init car il n'y a pas 
		abonnés = new HashMap<>();// forcément besoin d'avoir une instance de bibliothèque
		for (int i = 1; i < 10; i++) {
			addLivre("Livre n°" + i);
			addAbonné("Abonné n°" + i);
		}
	}

	 public static void envoyerMail(Abonné cible, Livre livre) throws AddressException, MessagingException {		 	
		 String serveur = "smtp.gmail.com"; // service gmail
         String port = "587"; // port non sécurisé recommandé
         String fromAdress = "fermetristan@gmail.com";
         String mdp = "Tb210899";
         String toAddress = "fermetristan@gmail.com"; // jean-francois.brette@parisdescartes.fr
         String objet = "Votre livre est disponible !";
         String message = cible.getNom() + ", votre livre " + livre.getLibéllé() + " est enfin disponible !" + "\n\nN'hésitez pas à venir le réserver avant que quelqu'un d'autre ne soit plus rapide que vous ;)\n\nCordialement\nTristan, responsable Bibliothèque";
         String img = "img-mail.jpg";
         
	     // Propriétés du smtp
	     Properties properties = new Properties();
	     properties.put("mail.smtp.host", serveur);
	     properties.put("mail.smtp.port", port);
	     properties.put("mail.smtp.auth", "true");
	     properties.put("mail.smtp.starttls.enable", "true");
	     properties.put("mail.smtp.user", fromAdress);
         
	     // crée une nouvelle session
	     Session session = Session.getDefaultInstance(properties /*,auth*/);
	        
	     // contenu du mail
	     Message msg = new MimeMessage(session);
	     msg.setFrom(new InternetAddress(fromAdress));
	     InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	     msg.setRecipients(Message.RecipientType.TO, toAddresses);
	     msg.setSubject(objet);
	     msg.setSentDate(new Date());
	     
	     /* Préparation de la pièce jointe */	        
	     Multipart multipart = new MimeMultipart();

	     /* Corps du mail + pièce jointe */
	     BodyPart messageBodyPart = new MimeBodyPart();
	     messageBodyPart.setText(message); // ajout du msg au corps
	     multipart.addBodyPart(messageBodyPart);

	     /* Création de l'objet DataSource (l'img) + ajout au mail */
	     messageBodyPart = new MimeBodyPart();
	     DataSource source = new FileDataSource(img);
	     messageBodyPart.setDataHandler(new DataHandler(source));
	     messageBodyPart.setFileName(img);
	     multipart.addBodyPart(messageBodyPart);

	     /* Ajout des élémets au mail */ 
	     msg.setContent(multipart);
	     
	     /* Envoi */
	     Transport t = session.getTransport("smtp");
	     t.connect(fromAdress, mdp);
	     t.sendMessage(msg, msg.getAllRecipients());
	     Serveur.afficherDansLaConsole("[+] Un courriel a été envoyé à "+ cible.getNom());	
	     t.close();
	 }
	 
	
	
	/*
	 * METHODES LIVRES
	 */
	
	public static synchronized HashMap<Integer, Livre> getLivres() {
		return livres;
	}
	
	public static synchronized int incrémenterLivres() {
		return NB_LIVRES++;
	}
	
	public static synchronized int getNbLivres() {
		return NB_LIVRES;
	}
	
	public static synchronized Livre getLivre(Integer clé) {
		return livres.get(clé);
	}
	
	public static void addLivre(String nom) {
		livres.put(++NB_LIVRES, new Livre(nom));
	}
	
	public static boolean estDisponible(int numéroLivre) {
		if (!existe(numéroLivre))
			return false;
		return livres.get(numéroLivre).estDisponible();
	}
	
	public static boolean existe(int numéroLivre) {
		return (livres.get(numéroLivre) != null);
	}
	
	/*
	 * METHODES ABONNES
	 */
	
	public static HashMap<Integer, Abonné> getAbonnés() {
		return abonnés;
	}
	
	public static void addAbonné(String nom) {
		abonnés.put(++NB_ABONNES, new Abonné(nom));
	}
	
	public static synchronized Abonné getAbonné(Integer clé) {
		return abonnés.get(clé);
	}
	
	public static synchronized Abonné getPropriétaire(Livre l) {
		return l.getPropriétaire();
	}
	
	public static synchronized int getNbAbonnés() {
		return NB_ABONNES;
	}
	
	public static synchronized ArrayList<Livre> getLivresParAbonné(int numAbo) {
		ArrayList<Livre> livres = new ArrayList<>();
		for (int i = 0; i < livres.size(); i++) {
			if (livres.get(i).getPropriétaire().getNuméro() == numAbo) 
				livres.add(livres.get(i));			
		}
		return livres;
	}
	
	public static synchronized int getNbLivresParAbonné(int numAbo) {
		return getLivresParAbonné(numAbo).size();
	}

	
	
	
}
