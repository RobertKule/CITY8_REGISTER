import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.mysql.cj.protocol.Resultset;

public class LesVilles extends JFrame {
    protected static JTextField tfNomVille;
    protected static JLabel lbTitrePrincipal = new JLabel("GESTION DES NOMS DE VILLES"),
            lbNomVille = new JLabel("Entrez le nom de la ville :"), lbChoixVille = new JLabel("Choisir une ville");
    protected static JButton btEnregistre = new JButton("ENREGISTRER");
    protected static JComboBox cbListeVille = new JComboBox();
    protected static JLabel lbEtat = new JLabel("");

    static final String DU_URL = "jdbc:mysql://localhost:3306/ville",
            user = "root", pswd = "kule@2002";

    // le constructeur
    public LesVilles() {
        // information rélatives de la fenetre
        super("Enregistrement des Villes");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        // le conteneur
        JPanel pann = (JPanel) this.getContentPane();
        pann.setBackground(new Color(25, 25, 25));

        pann.setLayout(null);
        // le label titre
        lbTitrePrincipal.setForeground(new Color(255, 255, 255));
        lbTitrePrincipal.setBackground(new Color(214, 217, 223));
        lbTitrePrincipal.setFont(new Font("SansSerif", Font.BOLD, 28));
        lbTitrePrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitrePrincipal.setBounds(0, 10, 480, 90);
        pann.add(lbTitrePrincipal);
        // label pour identifier la case de noms de villes
        lbNomVille.setForeground(new Color(255, 255, 255));
        lbNomVille.setFont(new Font("SansSerif", Font.PLAIN, 25));
        lbNomVille.setHorizontalAlignment(SwingConstants.LEFT);
        lbNomVille.setBounds(15, 110, 400, 40);
        pann.add(lbNomVille);
        // la zone de texte pour écrire des villes
        tfNomVille = new JTextField();
        // évenement de ce textfield quand on y clique
        tfNomVille.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfNomVille.setText("");
                lbEtat.setText("Enregistrement en cours ...");
                lbEtat.setForeground(Color.white);
            }
        });
        tfNomVille.setForeground(new Color(25, 25, 112));
        tfNomVille.setBackground(new Color(224, 255, 255));
        tfNomVille.setToolTipText("Entrez ici le nom de votre ville");
        tfNomVille.setHorizontalAlignment(SwingConstants.CENTER);
        tfNomVille.setFont(new Font("SansSerif", Font.PLAIN, 25));
        tfNomVille.setBounds(25, 150, 400, 40);
        pann.add(tfNomVille);
        tfNomVille.setColumns(10);
        // le bouton d'éxecution et des confirmation des actions
        btEnregistre.setForeground(new Color(25, 25, 112));
        btEnregistre.setBackground(new Color(224, 255, 255));
        btEnregistre.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 25));
        btEnregistre.setBounds(115, 198, 221, 53);
        pann.add(btEnregistre);
        // label pour presenter le combobox qui affiche les villes
        lbChoixVille.setForeground(new Color(255, 255, 255));
        lbChoixVille.setHorizontalAlignment(SwingConstants.CENTER);
        lbChoixVille.setFont(new Font("SansSerif", Font.PLAIN, 25));
        lbChoixVille.setBounds(50, 325, 400, 40);
        pann.add(lbChoixVille);
        // combobox pour afficher/visualiser des villes crées
        cbListeVille.setForeground(new Color(25, 25, 112));
        cbListeVille.setBackground(new Color(224, 255, 255));
        cbListeVille.setBounds(50, 381, 400, 45);
        pann.add(cbListeVille);
        // Label pour afficher les concepteurs de ce code
        JLabel lbTravaillerPar = new JLabel("powered by groupe 5 Génie elec-Info/L2");
        lbTravaillerPar.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbTravaillerPar.setForeground(new Color(255, 255, 255));
        lbTravaillerPar.setHorizontalAlignment(SwingConstants.CENTER);
        lbTravaillerPar.setBounds(0, 438, 484, 23);
        pann.add(lbTravaillerPar);
        lbEtat.setBounds(84, 263, 290, 23);
        pann.add(lbEtat);
        // evenement qui nous explique ce qui se passe en cliquant au bouton d'éxécution
        btEnregistre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tfNomVille.getText().equals("")) {
                    ajouteUneVille(tfNomVille.getText());
                    lbEtat.setText("");
                    lbEtat.setForeground(pann.getBackground());

                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez d'abord remplir le champ de la ville",
                            "Message du champ vide", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        MiseAJour();
    }

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