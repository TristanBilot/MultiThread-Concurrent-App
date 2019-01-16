package serveur;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LecteurScanner {
	
	private BufferedReader reader;
	private PrintWriter writer;
	private BufferedReader clavier;
	
	public LecteurScanner(Socket client) {
		
		try {
			
			this.reader = new BufferedReader (new InputStreamReader(client.getInputStream()));
			this.writer = new PrintWriter (client.getOutputStream(), true);
			this.clavier = new BufferedReader(new InputStreamReader(System.in));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String read() throws IOException {
		return reader.readLine();
	}
	
	public int readInt() throws NumberFormatException, IOException {
		return Integer.parseInt(reader.readLine());
	}
	
	public void write(String msg) {
		writer.println(msg);
	}
	
	public void write(int msg) {
		writer.println(msg);
	}
	
	// retourne la saisie clavier client
	public String getClavier() throws IOException {
		return clavier.readLine();
	}
	
	// Lit le msg tapé par le user et l'envoie au serveur
	public void envoyerClavierAuServeur() throws IOException {
		write(getClavier());
	}
}
