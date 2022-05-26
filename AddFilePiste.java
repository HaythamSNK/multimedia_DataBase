
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AddFilePiste {
	public static void ajoutFichier(Statement currentStatement, Scanner keyboard, int choix, String email, int numPiste, int numAlbum) throws SQLException, ParseException{
		//int choix = keyboard.nextInt();
		//keyboard.nextLine();
		//ResultSet checkmail = currentStatement.executeQuery("SELECT Mail FROM Utilisateur\n"
			//	+"WHERE Mail = '"+email+"'");
		//if (!(checkmail.next())) {
			//keyboard.close();
			//throw new SQLException("Mail Inexistant");
		//} 
	/*int idfi = 1;
	    int idfich = 1;
		System.out.print("Entrez la taille du fichier: ");
		int taille = keyboard.nextInt();
		
		
	    System.out.println("ceci pour debugguer donner un entier :");
	    int yy = keyboard.nextInt();
	    System.out.println("donner la date sous forme yyyy-MM-dd");
	    keyboard.nextLine();
	    String dateUpload = keyboard.nextLine();
	    
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		Date newdate = date.parse(dateUpload);
		//Date date = new Date();
		//String dateUpload = format.format(date).toString();
		ResultSet idFichier = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
		if(idFichier.next()) {
			idfi = idFichier.getInt(1)+1;
			
		}
		//ResultSet ;*/
		int cinque = 5; 
		System.out.print("Entrez la taille du fichier: ");
		int taille = keyboard.nextInt();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String dateUpload = format.format(date).toString();
		
		String command = "INSERT INTO Fichier ( IdFichier, Taille, DateDepot, MailUser, idPiste,idAlbum)\n"
				+"Values (IdFichier.NEXTVAL,'"+taille+"', '"+dateUpload+"', '"+email+"','"+numPiste+"','"+numAlbum+"')";
		
		currentStatement.executeQuery(command);
		
		/**
		String pisteStmt = "INSERT INTO Fichier ( IdFichier, Taille, DateDepot, MailUser, TitreFilm,AnneeSortie)\n"
				+"Values(idFichier.NEXTVAL, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = connexion.prepareStatement(pisteStmt);
		pstmt.setInt(5,taille)
		pstmt.setInt(2, dateDepot);
		pstmt.setString(3, mailUser);
		pstmt.setString(4, TitreFilm);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
		*/
		ResultSet idFichier = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
		idFichier.next();
		int idFichierCourrant = idFichier.getInt(1);
		System.out.println("l'id du fichier a ajouter"+idFichierCourrant);
		
		switch(choix) {
			case(1):
				System.out.println("On commence par le contenue Video.");
				AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 1, keyboard);
				System.out.println("Passons maintenant au contenue Audio.");
				AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 2, keyboard);
				break;
				/*
				int repTexte = keyboard.nextInt();
				if (repTexte ==1) {
					System.out.print("Passons maintenant au contenue Texte, Quel nombre de Flux Texte vous proposez pour ce film: ");
					int nombreFluxText = keyboard.nextInt();
					for (int i=0; i<nombreFluxText; i++) {
						System.out.println("Pour le flux Audio Numero "+(i+1));
						AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 3, keyboard);
					}
				}
				*/
			case(2):
				System.out.println("Ajout du contenu Audio.");
				AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 2, keyboard);
				break;
			case(3):
				System.out.println("Ajout du contenu texte.");
				AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 2, keyboard);
				break;
			default:
				break;
				
		}
		
	
	
}

}
