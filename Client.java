
import java.util.*;
import java.sql.*;

public class Client {
	
	static Scanner scanner =  new Scanner(System.in);
	
	public static void ajouteClient(Connection connection, Statement statement) throws SQLException {
		
		System.out.println("Dans cette partie on va vous proposer de nous indiquer les clients voulus");
		
		int rajouteClient = 1;
		
		while(rajouteClient == 1){
			
			System.out.println("Merci de nous indiquer la marque de votre client");
			String marque = scanner.nextLine();
			System.out.println("Merci de nous indiquer le modele de votre client");
			String modele = scanner.nextLine();
			System.out.println("Merci de nous indiquer la largeur de r�solution maximal de votre client");
			int resLargeurMax = scanner.nextInt();
			System.out.println("Merci de nous indiquer la hauteur de r�solution maximal de votre client");
			int resHauteurMax = scanner.nextInt();
			
			ResultSet requeteCle = statement.executeQuery("SELECT marque, modele FROM Client \n"
					+ "WHERE marque='"+marque+"' and modele ='"+modele+"'");
			
			if (!requeteCle.next()) {
				String codec;
				String strClient = ""+"INSERT into Client (marque, modele, resLargeurMax, resHauteurMax)"+"Values(?,?,?,?)";
				PreparedStatement pstmt = connection.prepareStatement(strClient);
				pstmt.setString(1, marque);
				pstmt.setString(2, modele);
				pstmt.setInt(3, resLargeurMax);
				pstmt.setInt(4, resHauteurMax);
				ResultSet res = pstmt.executeQuery();
				
				System.out.println("Merci de nous indiquer les codecs qui peuvent etre interprete par ce Client ");
				System.out.println(" on comencera par les codecs video merci de nous entrer le nombre des codecs video ");
				int nbr = scanner.nextInt(); 
				scanner.nextLine();
				for(int i=0;i < nbr;i++) {
					System.out.println("veuiller entre le nom du codec ");
					codec = scanner.nextLine();
					String str = ""+"INSERT into peutLireCodecVideo (marque, modele, codec)"+"Values(?,?,?)";
					PreparedStatement pstmt1 = connection.prepareStatement(str);
					pstmt1.setString(1, marque);
					pstmt1.setString(2, modele);
					pstmt1.setString(3, codec);
					ResultSet res1 = pstmt1.executeQuery();
					res1.close();
	
				}
				System.out.println("Nous passons au codec audio merci de nous entrer le nombre des codecs audio : ");
				nbr = scanner.nextInt(); 
				scanner.nextLine();
				for(int i=0;i < nbr;i++) {
					System.out.println("veuiller entre le nom du codec ");
					codec = scanner.nextLine();
					String str = ""+"INSERT into peutLireCodecAudio (marque, modele, codec)"+"Values(?,?,?)";
					PreparedStatement pstmt2 = connection.prepareStatement(str);
					pstmt2.setString(1, marque);
					pstmt2.setString(2, modele);
					pstmt2.setString(3, codec);
					ResultSet res1 = pstmt2.executeQuery();
					res1.close();
	
				}
				System.out.println("finalement les codecs texte merci de nous entrer le nombre des codecs textes ");
				nbr = scanner.nextInt(); 
				scanner.nextLine();
				for(int i=0;i < nbr;i++) {
					System.out.println("veuiller entre le nom du codec ");
					codec = scanner.nextLine();
					String str = ""+"INSERT into peutLireCodecTexte (marque, modele, codec)"+"Values(?,?,?)";
					PreparedStatement pstmt3 = connection.prepareStatement(str);
					pstmt3.setString(1, marque);
					pstmt3.setString(2, modele);
					pstmt3.setString(3, codec);
					ResultSet res1 = pstmt3.executeQuery();
					res1.close();
	
				}
			}
			
			else {
				System.out.println("D�sol�! Ce client existe d�j�");	
			}
			
			System.out.println("Voulez vous rajouter d'autres client ? Si oui tapez 1 sinon tapez 0 ");
			rajouteClient = scanner.nextInt();
		
		}
		
	}

}
