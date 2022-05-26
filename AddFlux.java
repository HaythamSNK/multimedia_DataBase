
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;


public class AddFlux {
	
	public static void ajoutFlux(Statement currentStatement, int idFichier, int natureFichier, Scanner keyboard) throws SQLException{
		String chaineFlux = "";
		System.out.print("Entrez le débit: ");
		int debit = keyboard.nextInt();
		String command, command_2;
		int indice = 1;
		switch (natureFichier) {
			case(1):
				System.out.print("Entrez le codec utilisé pour la video: ");
				keyboard.nextLine();
				String codecVideo = keyboard.nextLine();
				ResultSet findCodecVideo = currentStatement.executeQuery("SELECT * FROM CodecVideo\n"
						+"WHERE Codec='"+codecVideo+"'");
				chaineFlux += "SELECT * FROM CodecVideo\n"+"WHERE Codec='"+codecVideo+"'\n";
				if (!findCodecVideo.next()) {
					//command = "INSERT INTO Codec (Codec) Values ('"+codecVideo+"')";
					command_2 = "INSERT INTO CodecVideo (Codec) Values ('"+codecVideo+"')";
					//chaineFlux += command+"\n"+command_2+"\n";
					//currentStatement.executeQuery(command);
					currentStatement.executeQuery(command_2);
				}
				System.out.print("Entrez la hauteur de la video: ");
				int hauteur = keyboard.nextInt();
				System.out.print("Entrez la largeur de la video: ");
				int largeur = keyboard.nextInt();
				ResultSet lastID = currentStatement.executeQuery("SELECT max(idFlux) FROM Flux");
				if(lastID.next()) {
					indice = lastID.getInt(1)+1;
				}	
				currentStatement.executeQuery("INSERT INTO Flux (idFlux, Debit, idFichier) values ('"+indice+"','"+debit+"', '"+idFichier+"')");
				chaineFlux += "INSERT INTO Flux (Debit, idFichier) values ('"+debit+"', '"+idFichier+"')"+"\n";
				
				
				//lastID.next();					
				chaineFlux += "SELECT max(idFlux) FROM Flux"+"\n";
				command = "INSERT INTO FluxVideo (idFlux, Codec, Largeur, Hauteur) values ('"+indice+"', '"+codecVideo+"','"+largeur+"', '"+hauteur+"')";
				chaineFlux += command+"\n";
				currentStatement.executeQuery(command);
				
					 //Partie pour le flux Audio
				break;
			case(2):
				ResultSet lastID1 = currentStatement.executeQuery("SELECT max(idFlux) FROM Flux");
			   if(lastID1.next()) {
				indice = lastID1.getInt(1)+1;
			    }	
				keyboard.nextLine();
				System.out.print("Entrez le codec utilisé pour l'audio: ");
				String codecAudio = keyboard.nextLine();
				ResultSet findCodecAudio = currentStatement.executeQuery("SELECT * FROM CodecAudio\n"
						+"WHERE Codec='"+codecAudio+"'");
				if (!findCodecAudio.next()) {
					//command = "INSERT INTO Codec (Codec) Values ('"+codecAudio+"')"+"\n";
					command_2 = "INSERT INTO CodecAudio (Codec) Values ('"+codecAudio+"')"+"\n";
					//currentStatement.executeQuery(command);
					currentStatement.executeQuery(command_2);
				}
				System.out.print("Entrez la valeur d'echantillonage: ");
				int echantillonage = keyboard.nextInt();
				keyboard.nextLine();
				System.out.print("Entrez la langue d'audio: ");
				String langue = keyboard.nextLine();
				ResultSet findlangue = currentStatement.executeQuery("SELECT * FROM Langue\n"
						+"WHERE nomLangue='"+langue+"'");
				if(!findlangue.next()) {
					command = "INSERT INTO Langue Values ('"+langue+"')"+"\n";
					
					currentStatement.executeQuery(command);
				}
				currentStatement.executeQuery("INSERT INTO Flux (idFlux, Debit, idFichier) values ('"+indice+"', '"+debit+"', '"+idFichier+"')");
				lastID1 = currentStatement.executeQuery("SELECT max(idFlux) FROM Flux");
				lastID1.next();					
				command = "INSERT INTO FluxAudio (idFlux, Echantillonage, Langue, Codec) values ('"+lastID1.getInt(1)+"', '"+echantillonage+"','"+langue+"', '"+codecAudio+"')";
				currentStatement.executeQuery(command);
				
				break;
			case (3):
				
				ResultSet lastID11 = currentStatement.executeQuery("SELECT max(idFlux) FROM Flux");
			    if(lastID11.next()) {
				   indice = lastID11.getInt(1)+1;
			     }	
				int last = 1;
				keyboard.nextLine();
				System.out.print("Entrez le codec utilisé pour l'audio: ");
				keyboard.nextLine();
				String codecText = keyboard.nextLine();
				keyboard.nextLine();
				ResultSet findCodecText = currentStatement.executeQuery("SELECT * FROM CodecText\n"
						+"WHERE Codec='"+codecText+"'");
				chaineFlux += "SELECT * FROM CodecText\n"
						+"WHERE Codec='"+codecText+"'"+"\n";
				if (!findCodecText.next()) {
					//command = "INSERT INTO Codec (Codec) Values ('"+codecText+"')"+"\n";
					chaineFlux += "INSERT INTO Codec (Codec) Values ('"+codecText+"')";
					command_2 = "INSERT INTO CodecTexte (Codec) Values ('"+codecText+"')"+"\n";
					chaineFlux += "INSERT INTO CodecTexte (Codec) Values ('"+codecText+"')";
					//currentStatement.executeQuery(command);
					currentStatement.executeQuery(command_2);
				}
				System.out.print("Entrez la langue du texte: ");
				String langueTexte = keyboard.nextLine();
				
				
				
				ResultSet findlangue2 = currentStatement.executeQuery("SELECT * FROM Langue\n"
						+"WHERE nomLangue='"+langueTexte+"'");
				if(!findlangue2.next()) {
					command = "INSERT INTO Langue Values ('"+langueTexte+"')"+"\n";
					
					currentStatement.executeQuery(command);
				}
				currentStatement.executeQuery("INSERT INTO Flux (Debit, idFichier) values ('"+debit+"', '"+idFichier+"')");
				chaineFlux += "INSERT INTO Flux (idFlux, Debit, idFichier) values ('"+debit+"', '"+idFichier+"')"+"\n";
				lastID11 = currentStatement.executeQuery("SELECT max(idFlux) FROM Flux");
				chaineFlux += "SELECT max(idFlux) FROM Flux"+"\n";
				lastID11.next();					
				command = "INSERT INTO FluxTexte (idFlux, Langue, Codec) values ('"+lastID11.getInt(1)+"', '"+langueTexte+"', '"+codecText+"')";
				currentStatement.executeQuery(command);
				chaineFlux += "INSERT INTO FluxAudio (idFlux, LangueTxt, Codec) values ('"+lastID11.getInt(1)+"', '"+langueTexte+"', '"+codecText+"')"+"\n";
			default:
				break;
		}
						
		//return chaineFlux;
		
	}
}
