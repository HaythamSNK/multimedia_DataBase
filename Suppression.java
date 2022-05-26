import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Suppression {
	static Scanner keyboard = new Scanner(System.in);
	
	public static void interactiveSupprimerFilm(Connection connection) throws SQLException{
		System.out.println("Veuillez entrer le titre du film que vous voulez supprimer :");
		String titre = keyboard.nextLine();
		System.out.println("L'année de sortie du film à supprimer svp");
		int anneeSortie = keyboard.nextInt();
		keyboard.nextLine();
		
		Statement existe = connection.createStatement();
		ResultSet res = existe.executeQuery("SELECT * FROM Film WHERE Titre='"+titre+"' AND AnneeSortie="+anneeSortie);
		if (!res.next()) {
			System.out.println("Ce film n'existe pas dans la base de données.");
		}
		else {
			supprimerFilm(connection, titre, anneeSortie);
		}
		res.close();
		existe.close();

	}
	
	public static void supprimerFilm(Connection connection, String titre, int anneeSortie) throws SQLException {
		
		/** Suppression des fichiers associees au film */
		/*PreparedStatement statement1 = connection.prepareStatement(
				"SELECT * FROM Fichier WHERE TitreFilm = '?' AND AnneeSortie = ?");
		statement1.setString(1, titre);
		statement1.setInt(2, anneeSortie);
		ResultSet resultat1 = statement1.executeQuery();
		while(resultat1.next()) {
			int idFichierSuppr = resultat1.getInt("IdFichier"); // avoir si l'attribut de la table de jointure est bien idFichier
			PreparedStatement statementSupprFichier = connection.prepareStatement(
					"DELETE FROM Fichier WHERE IdFichier = ?");
			statementSupprFichier.setInt(1, idFichierSuppr);
			statementSupprFichier.executeUpdate();
			statementSupprFichier.close();
		}
		resultat1.close();
		statement1.close();
		*/
		
		PreparedStatement statementSupprFichier = connection.prepareStatement(
				"DELETE FROM Fichier WHERE TitreFilm = ? AND AnneeSortie = ?");
		statementSupprFichier.setString(1, titre);
		statementSupprFichier.setInt(2, anneeSortie);
		statementSupprFichier.executeUpdate();
		statementSupprFichier.close();
		
		/** Suppression des roles associees au film */
		PreparedStatement statement2 = connection.prepareStatement(
				"SELECT * FROM ArtisteDansFilm WHERE Titre = ? AND AnneeSortie = ?");
		statement2.setString(1, titre);
		statement2.setInt(2, anneeSortie);
		ResultSet resultat2 = statement2.executeQuery();
		while(resultat2.next()) {
			int numArtisteSuppr = resultat2.getInt("NumArtiste"); 
			PreparedStatement statementSupprRole = connection.prepareStatement(
					"DELETE FROM ArtisteDansFilm WHERE NumArtiste = ? AND Titre = ? AND AnneeSortie = ?");
			statementSupprRole.setInt(1, numArtisteSuppr);
			statementSupprRole.setString(2, titre);
			statementSupprRole.setInt(3, anneeSortie);
			statementSupprRole.executeUpdate();
			statementSupprRole.close();
		}
		resultat2.close();
		statement2.close();
		
		/** Suppression du film */
		PreparedStatement statementSupprFilm = connection.prepareStatement(
				"DELETE FROM Film WHERE Titre = ? AND AnneeSortie = ?");
		statementSupprFilm.setString(1, titre);
		statementSupprFilm.setInt(2, anneeSortie);
		statementSupprFilm.executeUpdate();
		statementSupprFilm.close();
		
		/** Suppresion des artistes non references*/
		Artiste.supprimerNonReferences(connection);
		connection.commit(); //<<======= ici le commit 
	}
	
	public static void interactiveSupprimerPiste(Connection connection) throws SQLException{
		System.out.println("Veuillez entrer le numero de l'album qui contient la piste :");
		int numAlbum = keyboard.nextInt();
		keyboard.nextLine();
		System.out.println("Veuillez entrer le numero de la piste :");
		int numPiste = keyboard.nextInt();
		keyboard.nextLine();
		
		Statement existe = connection.createStatement();
		ResultSet res = existe.executeQuery("SELECT * FROM Piste WHERE NumAlbum="+numAlbum+" AND NumPiste="+numPiste);
		if (!res.next()) {
			System.out.println("Cette piste n'existe pas dans la base de données.");
		}
		else {
			supprimerPiste(connection, numAlbum, numPiste);
		}
		res.close();
		existe.close();

	}
	
public static void supprimerPiste(Connection connection, int numAlbum, int numPiste) throws SQLException {
		
		/** Suppression des fichiers associees a la piste */
		/*PreparedStatement statement1 = connection.prepareStatement(
				"SELECT * FROM Fichier f, ContenuMultimedia m where f.idFichier = m .idFichier and m.Titre like '?' and m.AnneeSortie = ?");
		statement1.setInt(1, numAlbum);
		statement1.setInt(2, numPiste);
		ResultSet resultat1 = statement1.executeQuery();
		while(resultat1.next()) {
			int idFichierSuppr = resultat1.getInt("idFichier"); // savoir si l'attribut de la table de jointure est bien idFichier
			PreparedStatement statementSupprFichier = connection.prepareStatement(
					"DELETE FROM Fichier WHERE idFichier = ?");
			statementSupprFichier.setInt(1, idFichierSuppr);
			statementSupprFichier.executeUpdate();
			statementSupprFichier.close();
		}
		resultat1.close();
		statement1.close();
		*/
		
		PreparedStatement statementSupprFichier = connection.prepareStatement(
				"DELETE FROM Fichier WHERE NumAlbum = ? AND NumPiste = ?");
		statementSupprFichier.setInt(1, numAlbum);
		statementSupprFichier.setInt(2, numPiste);
		statementSupprFichier.executeUpdate();
		statementSupprFichier.close();
	
		/** Suppression des musiciens associes a la piste */
		PreparedStatement statement2 = connection.prepareStatement(
				"SELECT * FROM ArtistePisteInstrument WHERE NumAlbum = ? and NumPiste = ?");
		statement2.setInt(1, numAlbum);
		statement2.setInt(2, numPiste);
		ResultSet resultat2 = statement2.executeQuery();
		while(resultat2.next()) {
			int numArtisteSuppr = resultat2.getInt("NumArtiste"); 
			PreparedStatement statementSupprRole = connection.prepareStatement(
					"DELETE FROM ArtistePisteInstrument WHERE NumArtiste = ? AND NumAlbum = ? and NumPiste = ?");
			statementSupprRole.setInt(1, numArtisteSuppr);
			statementSupprRole.setInt(2, numAlbum);
			statementSupprRole.setInt(3, numPiste);
			statementSupprRole.executeUpdate();
			statementSupprRole.close();
		}
		resultat2.close();
		statement2.close();
		
		/** Suppression de la piste */
		PreparedStatement statementSupprPiste = connection.prepareStatement(
				"DELETE FROM Piste WHERE NumAlbum = ? AND NumPiste = ?");
		statementSupprPiste.setInt(1, numAlbum);
		statementSupprPiste.setInt(2, numPiste);
		statementSupprPiste.executeUpdate();
		statementSupprPiste.close();
		
		/** Suppression des Artistes non references et des Albums vides */
		Artiste.supprimerNonReferences(connection);
		Piste.supprimerAlbumVide(connection);
		connection.commit(); //<<======= ici le commit 
	}
	
}
