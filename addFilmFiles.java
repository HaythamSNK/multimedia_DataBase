
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class addFilmFiles {
	
	static Scanner keyboard = new Scanner(System.in);
	
	public static void ajoutFichiersFilm (Connection connection, Statement currentStatement, String email) throws SQLException, ParseException {
		
		System.out.println("Merci de bien donner les informations nécessaires pour votre film.");
		System.out.print("Veuillez entrer le nom du film:");
		String nomFilm = keyboard.nextLine();
		System.out.print("Veuillez Entrez l'année de sortie du film: ");
		int anneeDeSortie = keyboard.nextInt();
		keyboard.nextLine();
		
		ResultSet requeteFilm = currentStatement.executeQuery("SELECT * FROM FILM \n"
				+ "WHERE Titre='"+nomFilm+"' and anneeSortie = '"+anneeDeSortie+"'");
		
		if(!requeteFilm.next()) {
			System.out.println("Ooops, le film n'existe pas sur notre base. On a besoin de plus d'infos pour l'ajouter.");
			System.out.print("Veuillez entrer un résumé du film (sans dépasser 255 caractère et sans retour à la ligne): ");
			String resume = keyboard.nextLine();
			System.out.print("Veuillez entrer l'age minimum pour regarder ce film: ");
			String ageMin = keyboard.nextLine();
			System.out.print("Veuillez entrer un lien de l'affiche ce film: ");
			String urlAffiche = keyboard.nextLine();
			currentStatement.executeQuery("INSERT INTO Film (Titre, anneeSortie, Resume, Minage, URLfilm)\n"
					+"VALUES ('"+nomFilm+"', '"+anneeDeSortie+"', '"+resume+"', '"+ageMin+"', '"+urlAffiche+"')");
			System.out.print("Quel est le nombre de catégories pour lesquelles ce film appertient ?: ");
			int nbrCat = keyboard.nextInt();
			keyboard.nextLine();
			String Categorie = null;
			for (int i=1; i<=nbrCat; i++) {
				System.out.print("Veuillez saisir la catégorie "+i+": ");
				Categorie = keyboard.nextLine();
				ResultSet requeteCategorie = currentStatement.executeQuery("SELECT * FROM CategorieFilm \n"
						+ "WHERE Categorie='"+Categorie+"'");
				System.out.println("requete finished categorie");
				if (!requeteCategorie.next()) {
					String command = "INSERT INTO CategorieFilm (Categorie) Values ('"+Categorie+"')";
					String command_2 = "INSERT INTO FilmApourCategorie (TitreFilm, anneeSortie, Categorie) Values ('"+nomFilm+"', '"+anneeDeSortie+"', '"+Categorie+"')";
					currentStatement.executeQuery(command);
					currentStatement.executeQuery(command_2);
				}
			}
			System.out.println("Voulez-vous ajouter les URL de quelques photo extraites du film ? (Tapez 1 pour OUI, 0 pour NON)");
			int ajoutphoto = keyboard.nextInt();
			keyboard.nextLine();
			while(ajoutphoto == 1) {
				System.out.println("Entrez l'url de la photo : ");
				String urlPhoto = keyboard.nextLine();
				Film.ajouterURLPhoto(connection, nomFilm, anneeDeSortie, urlPhoto);
				System.out.println("Pour continuer a ajouter des photos, tapez 1, pour arreter tapez 0 :");
				ajoutphoto = keyboard.nextInt();
				keyboard.nextLine();
			}
		}
		System.out.println("Voulez vous ajouter des artistes a ce film tapez 1 si OUI et 0 si NON! ");
		int choice = keyboard.nextInt();
		keyboard.nextLine();
		if(choice == 1) {
		System.out.println("nous demandant des informations sur les artistes dans ce film .");
		
		Interactive.interactiveAjoutArtisteFilm(connection, nomFilm, anneeDeSortie);
		}
		/**System.out.println("Merci de taper le nombre des artistes dans ce film ");
		
		int nbrArtiste = keyboard.nextInt();
		keyboard.nextLine();
		*/
		
		System.out.println("Cre�ation des fichies Audio-Video.");
		System.out.print("Combien de Ficher (Audio-Video) voulez-vous créer pour ce film ?: ");
		int nombreFluxVideo = keyboard.nextInt();
		keyboard.nextLine();
		for (int i=0; i<nombreFluxVideo; i++) {
			System.out.println("Pour le ficher Video-Audio Numero "+(i+1));
			AddFile.ajoutFichier(currentStatement, keyboard, 1, email,nomFilm, anneeDeSortie);
		}
		System.out.print("Voulez vous ajouter des sous-titres? [Tapez 0 pour NON ou 1 pour OUI]: ");
		int choixSubtitles = keyboard.nextInt();
		keyboard.nextLine();	
		if (choixSubtitles ==1) {
			System.out.print("Quel nombre de sous-titres voulez-vous proposez pour ce film: ");
			int nombreFluxText = keyboard.nextInt();
			for (int i=0; i<nombreFluxText; i++) {
				System.out.println("Pour le flux Audio Numero "+(i+1));
				AddFile.ajoutFichier(currentStatement, keyboard, 3, email,nomFilm,anneeDeSortie);
			}
		}
	}
}
