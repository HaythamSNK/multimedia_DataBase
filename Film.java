import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class Film {
	public static void ajouteFilm(Connection conn, String titre, int anneeSortie, String resume, int minAge, String urlFilm) throws SQLException {
		Statement verifie = conn.createStatement();
		ResultSet set = verifie.executeQuery("SELECT * FROM Film WHERE Titre="+titre+" AND "+"AnneeSortie="+anneeSortie);
		if(!set.next()) {
			set.close();
			verifie.close();
			String query = "INSERT INTO Film VALUES('"+ titre +"','"+ anneeSortie +"','"+ resume +"','"+ minAge +"','"+ urlFilm +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		else {
			System.out.println("Ce film est déjà dans la base de données");
		}
	}
	
	public static void ajouteCategorieFilm(Connection conn, String categorie) throws SQLException {
		String query = "INSERT INTO CategorieFilm VALUES('"+ categorie +"')";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}
	
	public static boolean[] ajouteApourCategorieFilm(Connection conn, String titre, int anneeSortie, String categorie) throws SQLException {
		boolean[] existe = {false, false};
		
		Statement verifie1 = conn.createStatement();
		Statement verifie2 = conn.createStatement();
		ResultSet set1 = verifie1.executeQuery("SELECT * FROM CategorieFilm WHERE Categorie="+categorie);
		ResultSet set2 = verifie2.executeQuery("SELECT * FROM Film WHERE Titre="+titre+" AND "+"AnneeSortie="+anneeSortie);
		while (set1.next()) {
			existe[0] = true;
			set1.close();
			verifie1.close();
			break;
		}
		while (set2.next()) {
			existe[1] = true;
			set2.close();
			verifie2.close();
			break;
		}
		if (existe[0] && existe[1]) {
			String query = "INSERT INTO ApourCategorieFilm VALUES('"+ titre +"','"+ anneeSortie +"','"+ categorie +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		return existe;
	}
	
	public static boolean[] ajouteArtisteDansFilm(Connection conn, int numArtiste, int anneeSortie, String titre, String roleDansFilm) throws SQLException {
		boolean[] existe = {false, false};
		
		Statement verifie1 = conn.createStatement();
		Statement verifie2 = conn.createStatement();
		ResultSet set1 = verifie1.executeQuery("SELECT * FROM Artiste WHERE NumArtiste="+numArtiste);
		ResultSet set2 = verifie2.executeQuery("SELECT * FROM Film WHERE Titre='"+titre+"' AND AnneeSortie="+anneeSortie);
		while (set1.next()) {
			existe[0] = true;
			set1.close();
			verifie1.close();
			break;
		}
		
		while (set2.next()) {
			existe[1] = true;
			set2.close();
			verifie2.close();
			break;
		}
		
		if (existe[0] && existe[1]) {
			String query = "INSERT INTO ArtisteDansFilm VALUES('"+ numArtiste +"','"+ anneeSortie +"','"+ titre +"','"+ roleDansFilm +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		return existe;
	}
	
	public static void ajouterURLPhoto(Connection connection, String titre, int anneeSortie, String urlPhoto) throws SQLException {
		String query = "INSERT INTO FilmUrlPhoto VALUES('"+ titre +"','"+ anneeSortie +"','"+ urlPhoto +"')";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}
}
