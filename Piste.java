import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.sql.*;
public class Piste {
	public static void ajouterCategorie(Connection connexion, String nomCategorie) throws SQLException{
		String strCategorie = ""+"INSERT into CategorieMusic (Categorie)"+"Values(?)";
		PreparedStatement pstmt = connexion.prepareStatement(strCategorie);
		pstmt.setString(1, nomCategorie);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
		
	}
	public static void ajouterMusicCategorie(Connection connexion, String nomCategorie,int numPiste,int numAlbum) throws SQLException {
		String strCategorie = ""+"INSERT into PisteApourCategorie (NumPiste, NumAlbum, Categorie)"+"Values(?,?,?)";
		PreparedStatement pstmt = connexion.prepareStatement(strCategorie);
		pstmt.setInt(1, numPiste);
		pstmt.setInt(2, numAlbum);
		pstmt.setString(3, nomCategorie);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
	}
	public static void ajouterAlbumCategorie(Connection connexion, String nomCategorie, int numAlbum) throws SQLException {
		String strCategorie = ""+"INSERT into AlbumApourCategorie (NumAlbum, Categorie)"+"Values(?,?)";
		PreparedStatement pstmt = connexion.prepareStatement(strCategorie);
		pstmt.setInt(1, numAlbum);
		pstmt.setString(2, nomCategorie);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
		
	}
	public static void ajouterAlbum(Connection connexion,int numAlbum, String titre,String nomGroupe,String anneeSortie, String URL ) throws SQLException {
		String albumstmt = ""+"INSERT into Album(NumAlbum, TitreAlbum,GroupeArtiste,DateSortie,urlPochette)"+" Values(?,?,?,?,?)";
		PreparedStatement pstmt = connexion.prepareStatement(albumstmt);
		pstmt.setInt(1,numAlbum);
		pstmt.setString(2, titre);
		pstmt.setString(3, nomGroupe);
		pstmt.setString(4, anneeSortie);
		pstmt.setString(5, URL);
		
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
	}
	public static void ajouterPiste(Connection connexion,int numPiste, int numAlbum,String titre, int duree) throws SQLException {
		String pisteStmt = ""+"INSERT into Piste(NumPiste, NumAlbum, Titre,Duree) "+"Values(?, ?, ?, ?)";
		PreparedStatement pstmt = connexion.prepareStatement(pisteStmt);
		pstmt.setInt(1, numPiste);
		pstmt.setInt(2, numAlbum);
		pstmt.setString(3, titre);
		pstmt.setInt(4, duree);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
		
	}
	
	public static void ajouteArtistePiste(Connection conn, int numArtiste, int numAlbum, int numPiste, String instrument) throws SQLException{
		
		Statement verifie = conn.createStatement();
		ResultSet set = verifie.executeQuery("SELECT * FROM Instrument WHERE NomInstrument='"+instrument+"'");
		if(!set.next()) {
			Statement addingInstrument = conn.createStatement();
			addingInstrument.executeUpdate("INSERT INTO Instrument VALUES('"+instrument+"')");
			addingInstrument.close();
		}
		set.close();
		verifie.close();
		
		String query = "INSERT INTO ArtistePisteInstrument VALUES('"+ numArtiste +"','"+ numAlbum +"','"+ numPiste +"','"+ instrument+"')";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	public static void supprimerAlbumVide(Connection connection) throws SQLException {
		PreparedStatement findEmpty = connection.prepareStatement("SELECT NumAlbum\n"
				+ "  FROM Album\n"
				+ "  MINUS (SELECT NumAlbum FROM Piste)");
		ResultSet findresult = findEmpty.executeQuery();
		while(findresult.next()) {
			int numAlbum = findresult.getInt(1);
			PreparedStatement delete = connection.prepareStatement("DELETE FROM Album\n"
					+"WHERE NumAlbum = ?");
			delete.setInt(1, numAlbum);
			delete.executeUpdate();
			delete.close();
		}
		findresult.close();
		findEmpty.close();
	}
}
