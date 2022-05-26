import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Interactive {
	static Scanner keyboard = new Scanner(System.in);
	
	public static void interactiveAjoutArtisteFilm(Connection connection, String titre, int anneeSortie) throws SQLException{
		System.out.println("Ajout des artistes");
		int stopCondition = 1;
		while(stopCondition != 0) {
			System.out.println("Veuillez entrez le nom de l'artiste :");
			String nomArtiste = keyboard.nextLine();
			
			System.out.println("Veuillez entrez l'URL d'une photo de l'artiste :");
			String urlPhoto = keyboard.nextLine();
			System.out.println("Veuillez indiquer la spécialté principale de l'artiste :");
			String specialite = keyboard.nextLine();
			
			String dateNaissance = null;
			System.out.println("Voulez-vous ajouter une date de naissance pour cet artiste ?");
			System.out.println("Tapez 1 si oui, 0 sinon. Attention, format dd-MM-yyyy a respecter !!!");
			int ouiNainssance = keyboard.nextInt();
			keyboard.nextLine();
			if(ouiNainssance == 1) {
				System.out.println("Veuillez entrer la date de naissance : ");
				dateNaissance = keyboard.nextLine();
			}
			
			String biographie = null;
			System.out.println("Voulez-vous ajouter une courte biographie pour cet artiste ?");
			System.out.println("Tapez 1 si oui, 0 sinon.");
			int ouiBiographie = keyboard.nextInt();
			keyboard.nextLine();
			if(ouiBiographie == 1) {
				System.out.println("Veuillez entrer la biographie de l'artiste : ");
				biographie = keyboard.nextLine();
			}
			
			System.out.println("Est-ce que c'est un nouveau artiste, tapez 1 si oui et 0 sinon");
			int nouvelArtiste = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.println("Attendez un peu s'il vous plaît");
			
			if (nouvelArtiste == 1) {
				// on genere le numero unique de l'artiste
				Statement sequenceMax = connection.createStatement();
				ResultSet maxSequence = sequenceMax.executeQuery("SELECT NumArtiste.NEXTVAL FROM dual");
				
				int numArtiste = 1;
				if(maxSequence.next()) {
					numArtiste = maxSequence.getInt(1);
				}
				System.out.println("num artiste est "+numArtiste);
				Artiste.ajouteArtiste(connection, numArtiste, nomArtiste, urlPhoto, specialite, dateNaissance, biographie);
				maxSequence.close();
				sequenceMax.close();
			}
			
			//on cherche le numero de cet artiste
			int numArtiste = 1;
			PreparedStatement existe = connection.prepareStatement("SELECT * FROM Artiste WHERE NomArtiste = ? AND URLPhotoArtiste = ? AND SpecialitePrincipale = ?");
			existe.setString(1, nomArtiste);
			existe.setString(2, urlPhoto);
			existe.setString(3, specialite);
			ResultSet resultat = existe.executeQuery();
			if(resultat.next()) {
				numArtiste = resultat.getInt("NumArtiste");
			}
			resultat.close();
			existe.close();
			
			System.out.println("Veuillez indiquer le role de cet artiste dans le film: ");
			String role = keyboard.nextLine();
			Film.ajouteArtisteDansFilm(connection, numArtiste, anneeSortie, titre, role);
			
			
			System.out.println("Voulez-vous continuer ?");
			System.out.println("Tapez 1 si oui, 0 sinon.");
			stopCondition = keyboard.nextInt();
			String vide = keyboard.nextLine();
		}
	}
	
	public static void interactiveAjoutArtistePiste(Connection connection, int numAlbum, int numPiste) throws SQLException{
		System.out.println("Ajout des artistes");
		int stopCondition = 1;
		while(stopCondition != 0) {
			
			System.out.println("Veuillez entrez le nom de l'artiste :");
			String nomArtiste = keyboard.nextLine();
			System.out.println("Veuillez entrez l'URL d'une photo de l'artiste :");
			String urlPhoto = keyboard.nextLine();
			System.out.println("Veuillez indiquer la spécialté principale de l'artiste :");
			String specialite = keyboard.nextLine();
			
			String dateNaissance = null;
			System.out.println("Voulez-vous ajouter une date de naissance pour cet artiste ?");
			System.out.println("Tapez 1 si oui, 0 sinon. Attention, format dd-MM-yyyy a respecter !!!");
			int ouiNainssance = keyboard.nextInt();
			keyboard.nextLine();
			if(ouiNainssance == 1) {
				dateNaissance = keyboard.nextLine();
			}
			
			String biographie = null;
			System.out.println("Voulez-vous ajouter une courte biographie pour cet artiste ?");
			System.out.println("Tapez 1 si oui, 0 sinon.");
			int ouiBiographie = keyboard.nextInt();
			keyboard.nextLine();
			if(ouiBiographie == 1) {
				biographie = keyboard.nextLine();
			}
			
			System.out.println("Est-ce que c'est un nouveau artiste, tapez 1 si oui et 0 sinon");
			int existeDeja = keyboard.nextInt();
			keyboard.nextLine();
			
			System.out.println("Attendez un peu s'il vous plaît");
			if (existeDeja == 1) {
				// on genere le numero unique de l'artiste
				Statement sequenceMax = connection.createStatement();
				ResultSet maxSequence = sequenceMax.executeQuery("SELECT artiste_seq.NEXTVAL FROM dual");
				
				int numArtiste = 1;
				if(maxSequence.next()) {
					numArtiste = maxSequence.getInt(1);
				}
				
				Artiste.ajouteArtiste(connection, numArtiste, nomArtiste, urlPhoto, specialite, dateNaissance, biographie);
				maxSequence.close();
				sequenceMax.close();
			}
			
			//on cherche le numero de cet artiste
			int numArtiste = 1;
			PreparedStatement existe = connection.prepareStatement("SELECT * FROM Artiste WHERE NomArtiste = ? AND URLPhotoArtiste = ? AND SpecialitePrincipale = ?");
			existe.setString(1, nomArtiste);
			existe.setString(2, urlPhoto);
			existe.setString(3, specialite);
			ResultSet resultat = existe.executeQuery();
			if(resultat.next()) {
				numArtiste = resultat.getInt("NumArtiste");
			}
			resultat.close();
			existe.close();
			
			System.out.println("Veuillez indiquer l'instrument utilisé par l'artiste, vous pouvez également indiquez <chanteur> comme instrument: ");
			String instrument = keyboard.nextLine();
			
						
			Piste.ajouteArtistePiste(connection, numArtiste, numAlbum, numPiste, instrument);
			
			
			System.out.println("Voulez-vous continuer ?");
			System.out.println("Tapez 1 si oui, 0 sinon.");
			stopCondition = keyboard.nextInt();
			String vide = keyboard.nextLine();
		}
	}
	
	public static int getMaxIndexArtistes(Connection conn) throws SQLException {
		int index = 1;
		Statement stat = conn.createStatement();
		ResultSet set = stat.executeQuery("SELECT MAX(NumArtiste) FROM Artiste");
		if(set.next()) {
			index = set.getInt(1) + 1;
			set.close();
			stat.close();
		}
		return index;
	}
}
