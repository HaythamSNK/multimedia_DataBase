import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Utilisateur {
	
	public static void ajouteUtilisateur(Connection conn, String mail, String nom, String prenom, int age, String code, String langue) throws SQLException {
		PreparedStatement prstat = conn.prepareStatement("INSERT INTO Utilisateur VALUES('?','?','?',?,'?','?')");
		prstat.setString(1, mail);
		prstat.setString(2, nom);
		prstat.setString(3, prenom);
		prstat.setInt(4, age);
		prstat.setString(5, code);
		prstat.setString(6, langue);
		
		prstat.executeUpdate();
		prstat.close();
	}
	
	public static void ajouteClient(Connection conn, String marque, String modele, int resLargeurMax, int resLongueurMax) throws SQLException {
		PreparedStatement prstat = conn.prepareStatement("INSERT INTO Client VALUES('?','?',?,?)");
		prstat.setString(1, marque);
		prstat.setString(2, modele);
		prstat.setInt(3, resLargeurMax);
		prstat.setInt(4, resLongueurMax);

		prstat.executeUpdate();
		prstat.close();
	}
	
	public static boolean ajouteLireCodecVideo(Connection conn, String marque, String modele, String codec) throws SQLException {
		boolean existe = false;
		
		Statement verifie1 = conn.createStatement();
		Statement verifie2 = conn.createStatement();
		ResultSet set1 = verifie1.executeQuery("SELECT * FROM Client WHERE Marque='"+marque+"' AND Modele='"+modele+"'");
		ResultSet set2 = verifie2.executeQuery("SELECT * FROM CodecVideo WHERE Codec='"+codec+"'");
		while (set1.next()) {
			existe = true;
			set1.close();
			verifie1.close();
			break;
		}
		
		if (!set2.next()) {
			set2.close();
			verifie2.close();
			Statement addCodec = conn.createStatement();
			addCodec.executeUpdate("INSERT INTO CodecVideo VALUES ('" + codec +"')");
			addCodec.close();
		}
		
		if (existe) {
			String query = "INSERT INTO PeutLireCodecVideo VALUES('"+ marque +"','"+ modele +"','"+ codec +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		return existe;
	}
	
	public static boolean ajouteLireCodecAudio(Connection conn, String marque, String modele, String codec) throws SQLException {
		boolean existe = false;
		
		Statement verifie1 = conn.createStatement();
		Statement verifie2 = conn.createStatement();
		ResultSet set1 = verifie1.executeQuery("SELECT * FROM Client WHERE Marque='"+marque+"' AND Modele='"+modele+"'");
		ResultSet set2 = verifie2.executeQuery("SELECT * FROM CodecAudio WHERE Codec='"+codec+"'");
		while (set1.next()) {
			existe = true;
			set1.close();
			verifie1.close();
			break;
		}
		
		if (!set2.next()) {
			set2.close();
			verifie2.close();
			Statement addCodec = conn.createStatement();
			addCodec.executeUpdate("INSERT INTO CodecAudio VALUES ('" + codec +"')");
			addCodec.close();
		}
		
		if (existe) {
			String query = "INSERT INTO PeutLireCodecAudio VALUES('"+ marque +"','"+ modele +"','"+ codec +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		return existe;
	}
	
	public static boolean ajouteLireCodecTexte(Connection conn, String marque, String modele, String codec) throws SQLException {
		boolean existe = false;
		
		Statement verifie1 = conn.createStatement();
		Statement verifie2 = conn.createStatement();
		ResultSet set1 = verifie1.executeQuery("SELECT * FROM Client WHERE Marque='"+marque+"' AND 'Modele='"+modele+"'");
		ResultSet set2 = verifie2.executeQuery("SELECT * FROM CodecTexte WHERE Codec='"+codec+"'");
		while (set1.next()) {
			existe = true;
			set1.close();
			verifie1.close();
			break;
		}
		
		if (!set2.next()) {
			set2.close();
			verifie2.close();
			Statement addCodec = conn.createStatement();
			addCodec.executeUpdate("INSERT INTO CodecTexte VALUES ('" + codec +"')");
			addCodec.close();
		}
		
		if (existe) {
			String query = "INSERT INTO PeutLireCodecTexte VALUES('"+ marque +"','"+ modele +"','"+ codec +"')";
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}
		return existe;
	}
}
