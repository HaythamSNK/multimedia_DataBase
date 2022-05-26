
import java.util.Scanner;
import java.sql.*;
import java.text.ParseException;
public class AddPiste {
	static Scanner keyboard = new Scanner(System.in);

	public static void ajoutFichiersPiste(Connection connection,Statement currentStatement ,String email) throws SQLException, ParseException {
		int numAlbum = 1;
		int numPiste = 1;
		System.out.println("l'ajout des fichiers multimedia en relation avec les pistes musicales ");
		System.out.print("Veuillez entrer le titre d'album contenat la piste  : ");
		String titreAlbum = keyboard.nextLine();
		ResultSet requetealbum = currentStatement.executeQuery("SELECT * FROM Album \n"
				+ "WHERE TitreAlbum='"+titreAlbum+"'");
		
		if(!requetealbum.next()) {
			System.out.println("cette album n'est pas enregistre on va l'enregistrer d'abord dans notre base .");
			//on recupere d'abord le dernier numero d'album
			ResultSet lastID = currentStatement.executeQuery("SELECT max(NumAlbum) FROM Album");
			if(lastID.next()) {
				numAlbum = lastID.getInt(1)+1;}
			
			System.out.print("Veuillez entrer le nom de groupe d'artiste de cet album : ");
			String nomGrp = keyboard.nextLine();
			System.out.print("Veuillez entrer un URL de l'amage de sa pochette: ");
			String url = keyboard.nextLine();
			System.out.println("Veuillez preciser le Date de sortie de cet album d'abord l'ann�e : ");
			int annee = keyboard.nextInt();
			System.out.println("le numero du mois(janvier:01 ...) s'il vous plait : ");
			int mois = keyboard.nextInt();
			System.out.println("et finalement le jour : ");
			int jour = keyboard.nextInt();
			String date = ""+annee+"-"+mois+"-"+jour;
			Piste.ajouterAlbum(connection, numAlbum, titreAlbum, nomGrp,date, url);
			System.out.print("Combien de catégories contient cette album  ?: ");
			int nbrCat = keyboard.nextInt();
			keyboard.nextLine();
			String Categorie = null;
			for (int i=1; i<=nbrCat; i++) {
				System.out.print("Veuillez saisir la catégorie "+i+": ");
				Categorie = keyboard.nextLine();
				ResultSet requeteCategorie = currentStatement.executeQuery("SELECT * FROM CategorieMusic \n"
						+ "WHERE Categorie='"+Categorie+"'");
				if (!requeteCategorie.next()) {
					Piste.ajouterCategorie(connection, Categorie);
				}
				Piste.ajouterAlbumCategorie(connection,Categorie, numAlbum);
			}	
		}else {
			numAlbum = requetealbum.getInt("NumAlbum");
		}
		
		System.out.println("Maintenat apres qu on a bien verifier l'existence de l'album on passse au piste");
		ResultSet lastID = currentStatement.executeQuery("SELECT max(NumPiste) FROM Piste");
		if(lastID.next()) {
			numPiste = lastID.getInt(1)+1;
			}
		
		System.out.print("Veuillez entrer le titre du piste");
		String nomPiste = keyboard.nextLine();
		System.out.println("Veullier preciser la duree(en minutes)  : ");
		int duree = keyboard.nextInt();
		keyboard.nextLine();
		Piste.ajouterPiste(connection, numPiste, numAlbum, nomPiste, duree);
		System.out.print("Combien de catégories contient cette piste  ?: ");
		int nbrCat = keyboard.nextInt();
		keyboard.nextLine();
		String Categorie = null;
		for (int i=1; i<=nbrCat; i++) {
			System.out.print("Veuillez saisir la catégorie "+i+": ");
			Categorie = keyboard.nextLine();
			ResultSet requeteCategorie = currentStatement.executeQuery("SELECT * FROM CategorieMusic \n"
					+ "WHERE Categorie='"+Categorie+"'");
			if (!requeteCategorie.next()) {
				Piste.ajouterCategorie(connection, Categorie);
			}
			Piste.ajouterMusicCategorie(connection,Categorie,numPiste,numAlbum);
			}
		
		System.out.println("on passe aux artistes de la piste");
		System.out.println("voulez vous ajouter des artistes a cette piste tapez 1 si oui 0 si non ");
		int choice = keyboard.nextInt();
		if(choice==1) {
			Interactive.interactiveAjoutArtistePiste(connection, numPiste, numAlbum);
		}
		System.out.println("on passe au flux en relation avec ce ficier");
		System.out.print("Combien de Ficher Audio voulez-vous creer pour ce film ?: ");
		int nombreFluxVideo = keyboard.nextInt();
		keyboard.nextLine();
		for (int i=0; i<nombreFluxVideo; i++) {
			System.out.println("Pour le ficher audio Numero "+(i+1));
			AddFilePiste.ajoutFichier(currentStatement, keyboard, 2, email,numPiste, numAlbum);
		}
		
		
	}
	

}
