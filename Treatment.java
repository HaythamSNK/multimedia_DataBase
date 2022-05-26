
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Treatment {
		public static void main(String[] args) {
			try {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				System.out.println("driver ok !");
				String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
				String user = "aitbaheh";
				String passwd = "Idfd7335";
				Connection connection = DriverManager.getConnection(url, user, passwd);
				System.out.println("connected successfully ! ");
				System.out.println("connected");
				Statement stmt = connection.createStatement ();
				ResultSet resultats = null;
				/*resultats = stmt.executeQuery("SELECT * FROM Utilisateur ");
				ResultSetMetaData rsmd = resultats.getMetaData();
				int nbCols = rsmd.getColumnCount();
			    while (resultats.next()) {
				      for (int i = 1; i <= nbCols; i++)
				         System.out.print(resultats.getString(i) + " ");
				      System.out.println();
			    }
			    resultats.close();*/
				
				Interactive.interactiveAjoutArtisteFilm(connection, "DooM", 2012);
			    connection.close();
		    } catch (SQLException e) {
				e.printStackTrace();
				System.out.println("request failed");
			}
		}
	}

