
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
public class AjouterPiste {
	static Scanner keyboard = new Scanner(System.in);
	
	public static void ajoutFichiersFilm (Statement currentStatement, String email) throws SQLException {
		System.out.println("Merci de bien donner les informations nécessaires pour votre piste.");
		System.out.print("Veuillez entrer le nom du piste:");
		String nomFilm = keyboard.nextLine();
}
}
