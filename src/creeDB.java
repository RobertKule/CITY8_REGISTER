import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// import com.mysql.cj.protocol.Resultset;

public class creeDB {
    static final String DU_URL = "jdbc:mysql://localhost:3306/ville",
            user = "root", pswd = "kule@2002";

    public static void main(String[] args) {
        // connectionBD();
        // for (String str : recupereBD()) {
        //     System.out.println(str);
        // }
        ajouteUneVille("Bella");
        for (String str : recupereBD()) {          
            System.out.println(str);
        }
    }

    static void ajouteUneVille(String nomVille) {
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();)
{
    String sql ="INSERT INTO villes(nom) VALUES('"+nomVille+"')";
    int rwaff=stmt.executeUpdate(sql);
    if (rwaff>0) {
        System.out.println("rows effected successfull");
    } else {
        System.out.println("nothing inserted");
    }
            
        } catch (Exception e) {
            System.out.println("there is an error\n"+e.getMessage());
        }
    }
    static ArrayList<String> recupereBD() {
        // on crée un table(ensemble dont la taille est dynamique)
        ArrayList<String> db = new ArrayList<String>();
        String sql = "SELECT * FROM villes";
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();)

        {
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            int i = 0;
            while (((ResultSet) rs).next()) {
                db.add(((ResultSet) rs).getString(2));
            }
            ccc.close();
            
        }

        catch (Exception e) {

            e.printStackTrace();
        }
        return db;
    }

    private static void uneRequeteSQL(String sql, String message) {
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();)

        {
            ResultSet rs=(ResultSet)stmt.executeQuery(sql);
            ccc.close();
            System.out.println(message);
        }

        catch (Exception e) {

            e.printStackTrace();
        }

    }

    static void connectionBD() {

        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();)

        {
            ccc.close();
            System.out.println("Database connection succeful");
        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }

}
