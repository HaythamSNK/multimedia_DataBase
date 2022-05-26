
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

public class Connectivity {
	
	public static void main(String[] args) throws SQLException, ParseException{
		
			Scanner keyboard = new Scanner(System.in);
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("driver ok !");
			String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
			String user = "aitbaheh";
			String passwd = "Idfd7335";
			Connection connection = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connection ..");
			Statement stmt = connection.createStatement ();
			System.out.println("Connected successfully ! ");
			System.out.println("Bienvenue et Merci de choisir Klex.");
			//connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			
			
		try {
			int choix ;
				
			
			System.out.println("merci de nous indique votre email");
			
			
			String email = addUtilisateur.identificationUtilisateur(connection, stmt);
			System.out.println("Voulez vous ajouter des clients taper 1 si OUI 0 sinon  :");
			choix = keyboard.nextInt();
			keyboard.nextLine();
			if(choix==1) {
				Client.ajouteClient(connection, stmt);	
			}
			
			
			if (email != null) {
		    boolean possibleChoice = true;
			boolean finish = false;
			while(!finish) {
				System.out.println("Merci de taper [1 : inserer des fichiers ], [2 : selectionner des fichier] et  [3 : pour supprimer]");
				choix = keyboard.nextInt();
				keyboard.nextLine();
				
				if(choix == 1) {
					possibleChoice = true;
					
					while(possibleChoice) {
						System.out.println("Merci de preciser [1 : des fichiers en relation avec film ], [2 : des fichier en relation avec pist]");
						choix = keyboard.nextInt();
						keyboard.nextLine();
						if(choix==1) {
							possibleChoice = false;
							addFilmFiles.ajoutFichiersFilm(connection, stmt, email);
							connection.commit();
						}
						else if(choix==2){
							possibleChoice = false;
							AddPiste.ajoutFichiersPiste(connection, stmt, email);
							connection.commit();
						}
						
					}
				}
				else if ( choix == 2){
					System.out.println("Voulez-vous faire des selections des pistes ou des films ? [1 : pour films ], [2 : pour pistes]");
						int monChoix = keyboard.nextInt();
						if (monChoix == 1) {
							Selection.selectionFilm(connection , stmt, email);
						}
						else if (monChoix == 2) {
							Selection.selectionPistes(connection , stmt, email);
						}
				}
				else if (choix == 3) {
					System.out.println("Voulez-vous supprimer un film (Tapez 1) ou une piste (Tapez 2) ? ");
					int choice = keyboard.nextInt();
					keyboard.nextLine();
					if(choice == 1) {
						Suppression.interactiveSupprimerFilm(connection);
						connection.commit();
						
					}
					else if(choice == 2) {
						Suppression.interactiveSupprimerPiste(connection);
						connection.commit();
					}
				}
				System.out.println("pour continuer avec Klex, tapez 1 sinon 0");
				choix = keyboard.nextInt();
				keyboard.nextLine();
				if(choix==0) {
					finish = true;
				}
			}
			
			
		    connection.close();
		    System.out.println("Merci d'avoir Utiliser notre serveur et a Bientot ^ ^");
		    keyboard.close();
			}
	    } catch (SQLException e) {
	    	connection.rollback();
		    connection.close();
		    keyboard.close();
			e.printStackTrace();
			System.out.println("request failed");
		}
	}
}