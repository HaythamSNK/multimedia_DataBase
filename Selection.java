
import java.util.*;
import java.sql.*;
public class Selection {
	static Scanner scanner = new Scanner(System.in);
	public static void getInformation() {	
		
	}
	
	
	public static void selectionFilm(Connection connection, Statement statement, String email) throws SQLException {
		System.out.println("Cette section a pour but de vous proposer des filmes merci de  nous tapez la categorie :  ");
		String categorie = scanner.nextLine();
		//scanner.nextLine();
		ResultSet res = statement.executeQuery("SELECT Age, LangueUtilisateur from Utilisateur where Mail = '"+email+"'");
		res.next();
		int age = res.getInt(1);
		String userLangue = res.getString(2);
		//System.out.println("l'age est : "+age +" et la langue est "+ userLangue);
		String requete  = "SELECT  titre, anneeSortie, Resume, MinAge from (Film natural Join FilmAPourcategorie) where Categorie = '"+categorie+"'and '"+age+"'>=MinAge";
		ResultSet filmPropose = statement.executeQuery(requete);
		boolean bool = true; 
			int i = 1;
			while(filmPropose.next()) {
				System.out.println("le "+i+"eme film est : " +filmPropose.getString(1)+" sortie en "+filmPropose.getInt(2) + " ayant le r�sum� suivant :"+ filmPropose.getString(3)+". En plus le minimum d'age est"+filmPropose.getInt(4));
				i += 1;
				bool = false;
			}
			
			
			
			
		if(bool) {
			System.out.println("oops il n'y a pas de contenus qui vous convient ");
		}
		
		
	}
	
	public static void selectionPistes(Connection connection, Statement statement, String email) throws SQLException {
		System.out.println("Cette section a pour but de vous proposer des musiques. Merci de nous tapez la categorie :  ");
		String categorie = scanner.nextLine();
		//scanner.nextLine();
		String str = "Select P.numPiste, P.numAlbum, titre,duree from Piste P,Album A, PisteApourCategorie C where (P.numAlbum = A.numAlbum and C.numAlbum = A.numAlbum and P.numAlbum = C.numAlbum and categorie = '"+categorie+"')";
		//ResultSet pistesProposees = statement.executeQuery("SELECT Piste.NumPiste, Piste.NumAlbum, Pist.Titre, Duree from Piste P, JOIN  PisteApourCategorie where Categorie = '"+categorie+"'))");
		ResultSet pistesProposees = statement.executeQuery(str);
		
		boolean bool = true; 
		int i = 1;
		while(pistesProposees.next()) {
			System.out.println("la piste N�"+i+" a pour num�ro : " +pistesProposees.getInt(1)+", num�ro d'album "+pistesProposees.getInt(2) + " "
					+ "et de titre :"+ pistesProposees.getString(3)+", et elle a comme dur�e "+pistesProposees.getInt(4));
			i += 1;
			bool = false;
		}
	
		if (bool) {
			System.out.println("D�sol�! On a pas trouv� de pistes pour la cat�gorie demand�e");
		}
			
			
			
		}
		
		
	}
	
	
	


