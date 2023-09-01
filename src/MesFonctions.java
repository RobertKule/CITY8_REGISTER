import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.mysql.cj.protocol.Resultset;

public class MesFonctions extends LesVilles{
    public static void MiseAJour() {

        // on efface puis on ajoute de nouveau les items
        cbListeVille.removeAllItems();
        for (String str : recupereBD()) {
            cbListeVille.addItem(str);
        }

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

        } catch (Exception e) {
            // erreur du look
        }
        LesVilles v = new LesVilles();
        v.setVisible(true);
    }

    static ArrayList<String> recupereBD() {
        // on crée un table(ensemble dont la taille est dynamique)
        ArrayList<String> db = new ArrayList<String>();
        String sql = "SELECT * FROM villes";
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();)

        {
            Resultset rs = (Resultset) stmt.executeQuery(sql);
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

    static void ajouteUneVille(String nomVille) {
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();) {
            String sql = "INSERT INTO villes(nom) VALUES('" + nomVille + "')";
            int rwaff = stmt.executeUpdate(sql);
            if (rwaff > 0) {
                System.out.println("rows effected successfull");
            } else {
                System.out.println("nothing inserted");
            }
            cbListeVille.addItem(nomVille);
        } catch (Exception e) {
            System.out.println("there is an error\n" + e.getMessage());
        }
    }

}
