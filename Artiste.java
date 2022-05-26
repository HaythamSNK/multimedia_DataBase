import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class Artiste {
	public static void ajouteArtiste(Connection conn, int numArtiste, String nomArtiste, String urlPhoto, String specialite, String dateNaissance, String biographie) throws SQLException {
		
		//String query = "INSERT INTO Artiste VALUES(artiste_seq,'"+nomArtiste+"','"+urlPhoto+"','"+specialite+"')";
		
		//on verifie que c'est un nouveau artiste
		PreparedStatement existe = conn.prepareStatement("SELECT * FROM Artiste WHERE NomArtiste = ? AND URLPhotoArtiste = ? AND SpecialitePrincipale = ?");
		existe.setString(1, nomArtiste);
		existe.setString(2, urlPhoto);
		existe.setString(3, specialite);
		ResultSet resultat = existe.executeQuery();
		if(resultat.next()) {
			System.out.println("Cet artiste existe déjà dans la base");
		}
		else {
		String query = "INSERT INTO Artiste VALUES('"+numArtiste+"','"+nomArtiste+"','"+urlPhoto+"','"+specialite+"')";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		statement.close();
		resultat.close();
		existe.close();
		}
		if (biographie != null) {
			ajouteBiographieArtiste(conn, numArtiste, biographie);
		}
		
		if (dateNaissance != null) {
			ajouteDateNaissanceArtiste(conn, numArtiste, dateNaissance);
		}
	}
	
	public static void ajouteDateNaissanceArtiste(Connection conn, int numArtiste, String dateNaissance) throws SQLException {
		
		PreparedStatement statement = conn.prepareStatement("INSERT INTO DateNaissanceArtiste VALUES(to_date(? ,'DD-MM-YYYY'), ?)");
		statement.setString(1, dateNaissance);
		statement.setInt(2, numArtiste);
		
		statement.executeUpdate();
		statement.close();
		
	}
	
	public static void ajouteBiographieArtiste(Connection conn, int numArtiste, String biographie) throws SQLException {
		
		String query = "INSERT INTO BiographieArtiste VALUES('"+biographie+"','"+numArtiste+"')";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		statement.close();
		
	}
	
	public static void supprimerNonReferences(Connection connection) throws SQLException {
		PreparedStatement findNonreferenced = connection.prepareStatement("SELECT NumArtiste\n"
				+ "  FROM Artiste\n"
				+ "  MINUS ((SELECT NumArtiste FROM ArtisteDansFilm) UNION (SELECT NumArtiste FROM ArtistePisteInstrument))");
		ResultSet findresult = findNonreferenced.executeQuery();
		while(findresult.next()) {
			int numArtiste = findresult.getInt(1);
			PreparedStatement delete = connection.prepareStatement("DELETE FROM Artiste\n"
					+"WHERE NumArtiste = ?");
			delete.setInt(1, numArtiste);
			delete.executeUpdate();
			/*
			PreparedStatement deleteDateNaissance = connection.prepareStatement("DELETE FROM DateNaissanceArtiste\n"
					+"WHERE NumArtiste = ?");
			deleteDateNaissance.setInt(1, numArtiste);
			deleteDateNaissance.executeUpdate();
			
			PreparedStatement deleteBiographie = connection.prepareStatement("DELETE FROM BiographieArtiste\n"
					+"WHERE NumArtiste = ?");
			deleteBiographie.setInt(1, numArtiste);
			deleteBiographie.executeUpdate();
			
			deleteBiographie.close();
			deleteDateNaissance.close();*/
			delete.close();
		}
		findresult.close();
		findNonreferenced.close();
	}
}
