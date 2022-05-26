
import java.sql.*;
import java.util.Scanner;

public class addUtilisateur {
static Scanner keyboard = new Scanner(System.in);
	
	public static String identificationUtilisateur(Connection connection,Statement currentStatement) throws SQLException {
		int choix;
		int dejaInscrit = 1;
		System.out.println("vous avez d�j� un compte si oui taper 1 sinon taper 0");
		choix = keyboard.nextInt();
		if (choix == 1) {
			System.out.println("Merci d'indiquer votre E-mail : ");
			keyboard.nextLine();
			String mail = keyboard.nextLine();
			ResultSet requeteMail = currentStatement.executeQuery("SELECT * FROM Utilisateur \n"
					+ "WHERE mail='"+mail+"'");
			
			if(requeteMail.next()) {
				System.out.println("Veuillez ins�rer votre code : ");
				String code = keyboard.nextLine();
				ResultSet requeteCode = currentStatement.executeQuery("SELECT * FROM Utilisateur \n"
						+ "WHERE CodeAcces='"+code+"'");
				int nbreEssaieRestee = 5;
				while (!requeteCode.next() && nbreEssaieRestee != 0) {
					System.out.println("Votre code est invalide! il vous reste " + nbreEssaieRestee+ " Essaies");
					System.out.println("Merci de r�ins�rer votre code: ");
					code = keyboard.nextLine();
					requeteCode = currentStatement.executeQuery("SELECT * FROM Utilisateur \n"
							+ "WHERE CodeAcces='"+code+"'");
					nbreEssaieRestee -= 1;
				}
				
				if(nbreEssaieRestee != 0) {
					System.out.println("Vous �tes bien inscrit dans notre base de donn�e");
					return mail;
				}
				else {
					System.out.println("D�sol�! Vous devez r�inscrire avec un nouveau E-mail :)");
					choix = 0;
					dejaInscrit = 0;
				}
			}		
			else {
				System.out.println("Votre E-mail n'est pas enregistr� dans notre base! Veuillez s'inscrire :)");
				choix = 0;
				dejaInscrit = 0;
			}
			
		}
		if(choix == 0) {
			System.out.println("Veuillez entrer votre E-mail : ");
			
			if (dejaInscrit == 1) {
				keyboard.nextLine();
			}
			
			String mail = keyboard.nextLine();
			ResultSet requeteMail = currentStatement.executeQuery("SELECT * FROM Utilisateur \n"
					+ "WHERE mail='"+mail+"'");
			
			while(requeteMail.next()) {
				System.out.println("Cet E-mail existe d�j�! Veuillez ins�rer un nouveau Email");
				mail = keyboard.nextLine();
				requeteMail = currentStatement.executeQuery("SELECT * FROM Utilisateur \n"
						+ "WHERE mail='"+mail+"'");
			}
			
			
			System.out.println("F�licitations, On vient d'ajouter votre mail � notre base");
			System.out.println("Veuillez entrer votre nom : ");
			String nom = keyboard.nextLine();
			System.out.println("Veuillez entrer votre pr�nom : ");
			String prenom = keyboard.nextLine();
			System.out.println("Veuillez entrer votre age (Merci d'indiquer juste le nombre; Exemple : 18) : ");
			int age = keyboard.nextInt();
			System.out.println("Veuillez entrer un code d'acces � votre compte (le code est compos� de quatres chiffres) : ");
			keyboard.nextLine();
			String codeAcces = keyboard.nextLine();
			
			while (codeAcces.length() != 4) {
				System.out.println("Attention! Le code doit �tre compos� de 4 chiffres : ");
				codeAcces = keyboard.nextLine();
				}
			
				System.out.println("Veuillez entrer votre langue pr�f�r�e : ");
				String langueUtilisateur = keyboard.nextLine();	
				ajouterUtilisateur(connection, currentStatement, mail, nom, prenom, age, codeAcces, langueUtilisateur);
				return mail;
		}
		return null;	
		
		
	}

	public static void ajouterUtilisateur(Connection connexion, Statement currentStatement, String mail, String nom, String prenom, int age, String codeAcces, String langueUtilisateur) throws SQLException {
		String strUtilisateur = ""+"INSERT into Utilisateur (mail, nom, prenom, age, codeAcces, langueUtilisateur)"+"Values(?,?,?,?,?,?)";
		PreparedStatement pstmt = connexion.prepareStatement(strUtilisateur);
		pstmt.setString(1, mail);
		pstmt.setString(2, nom);
		pstmt.setString(3, prenom);
		pstmt.setInt(4, age);
		pstmt.setString(5, codeAcces);
		ResultSet requeteLangue = currentStatement.executeQuery("SELECT * FROM Langue \n"
			+ "WHERE NomLangue='"+langueUtilisateur+"'");
		if (!requeteLangue.next()) {
			ajouterLangue(connexion, langueUtilisateur);
		}
		pstmt.setString(6, langueUtilisateur);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();

	}
	
	public static void ajouterLangue(Connection connexion, String langueUtilisateur) throws SQLException {
		String strLangue = ""+"INSERT into Langue (NomLangue)"+"Values(?)";
		PreparedStatement pstmt = connexion.prepareStatement(strLangue);
		pstmt.setString(1, langueUtilisateur);
		ResultSet res = pstmt.executeQuery();
		res.close();
		pstmt.close();
	}

}
