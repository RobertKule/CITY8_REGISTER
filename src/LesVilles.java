
// les imports des éléments
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.cj.protocol.Resultset;

public class LesVilles extends JFrame {
    // Déclaration et initialisation des éléments
    private static JTextField tfNomVille;
    private static JLabel lbTitrePrincipal = new JLabel("GESTION DES VILLES"),
            lbNomVille = new JLabel("Entrez le nom de la ville :"), lbChoixVille = new JLabel("Choisir une ville");
    private static JButton btEnregistre = new JButton("ENREGISTRER");
    private static JComboBox cbListeVille = new JComboBox();
    private static JLabel lbEtat = new JLabel("");

    static final String DU_URL = "jdbc:mysql://localhost:3306/ville",
            user = "root", pswd = "kule@2002";
    static Color fondclair = new Color(255, 255, 255), fondElementClair = new Color(65, 105, 225),
            fondsombres = new Color(31, 41, 57), fondelementsombre = new Color(173, 216, 230);
    static JComboBox CbActions = new JComboBox();

    // le constructeur
    public LesVilles() {

        // information rélatives de la fenetre
        super("Enregistrement des Villes");
        // Icone de la fenetre
        ImageIcon icone = new ImageIcon("city.png");
        this.setIconImage(icone.getImage());
        // autres informations de modification de la fenetre principale
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // le conteneur
        JPanel pann = (JPanel) this.getContentPane();
        pann.setBackground(fondclair);

        pann.setLayout(null);
        // le label titre
        lbTitrePrincipal.setForeground(fondElementClair);
        lbTitrePrincipal.setFont(new Font("Segoe UI Black", Font.BOLD, 40));
        lbTitrePrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitrePrincipal.setBounds(0, 0, 584, 265);
        pann.add(lbTitrePrincipal);
        // Le champ de texte
        tfNomVille = new JTextField();
        // évenement de ce textfield quand on y clique
        tfNomVille.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfNomVille.setText("");
            lbEtat.setText("Enregistrement en cours ...");
                lbEtat.setForeground(fondElementClair);
            }
        });
        // mis en forme du champ de texte pour enregistrer les villes
        tfNomVille.setForeground(fondclair);
        tfNomVille.setBackground(fondElementClair);
        tfNomVille.setToolTipText("Entrez ici le nom de la ville à enregistrer");
        tfNomVille.setHorizontalAlignment(SwingConstants.CENTER);
        tfNomVille.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tfNomVille.setText("Entrez ici le nom de la ville à enregistrer");
        tfNomVille.setBounds(10, 276, 429, 62);
        pann.add(tfNomVille);
        tfNomVille.setColumns(10);

        // le bouton d'éxecution et des confirmation des actions
        btEnregistre.setBackground(fondElementClair);
        btEnregistre.setForeground(fondclair);
        btEnregistre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btEnregistre.setBounds(449, 278, 125, 62);
        pann.add(btEnregistre);
        // Le selecteur de mode clair ou sombrere
        JCheckBox chMode = new JCheckBox("Mode sombre");
        chMode.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        chMode.setBackground(fondElementClair);
        // chMode.setForeground(new Color(65, 105, 225));
        chMode.setBounds(420, 6, 150, 50);
        pann.add(chMode);
        
        // le combobox des actions
        CbActions.setForeground(new Color(255, 255, 255));
        CbActions.setBackground(new Color(65, 105, 225));
        CbActions.setBounds(449, 373, 125, 50);
        CbActions.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        CbActions.addItem("Actions");
        CbActions.addItem("Mis à jour");
        CbActions.addItem("Effacer");
        CbActions.addItem("Dupliquer");
        pann.add(CbActions);

        lbEtat.setBounds(200, 450, 200, 50);
        lbEtat.setForeground(fondElementClair);
        pann.add(lbEtat);
        
        
        // evenement sur les items du combobox des actions
        CbActions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (CbActions.getSelectedIndex() == 1) {
                    MiseAJour();
                    CbActions.setSelectedIndex(0);

                } else if (CbActions.getSelectedIndex() == 2) {
                    EffaceUneVille((String) cbListeVille.getSelectedItem());
                    MiseAJour();
                    CbActions.setSelectedIndex(0);
                } else if (CbActions.getSelectedIndex() == 3) {
                    ajouteUneVille((String) cbListeVille.getSelectedItem());
                    MiseAJour();
                    CbActions.setSelectedIndex(0);
                }

            }
        });

        // Le combobox des noms de villes
        cbListeVille.setForeground(fondclair);
        cbListeVille.setBackground(fondElementClair);
        cbListeVille.setBounds(10, 373, 429, 50);
        cbListeVille.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        pann.add(cbListeVille);
        // Label pour afficher les concepteurs de ce code
        JLabel lbTravaillerPar = new JLabel("powered by groupe 5 Génie elec-Info/L2");
        lbTravaillerPar.setFont(new Font("Segoe Print", Font.PLAIN, 20));
        lbTravaillerPar.setForeground(fondElementClair);
        lbTravaillerPar.setHorizontalAlignment(SwingConstants.CENTER);
        lbTravaillerPar.setBounds(0, 511, 584, 50);
        pann.add(lbTravaillerPar);
        // evenement qui nous explique ce qui se passe en cliquant au bouton d'éxécution
        btEnregistre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tfNomVille.getText().equals("")) {
                    ajouteUneVille(tfNomVille.getText());
                    lbEtat.setText("");
                    lbEtat.setForeground(pann.getBackground());
                    tfNomVille.setText("Entrez ici le nom de la ville à enregistrez svp : ");
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez d'abord remplir le champ de la ville",
                            "Message du champ vide", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        // evenement lorsque l'état du selecteur de mode est modifié
        chMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chMode.isSelected()) {
                    lbTitrePrincipal.setForeground(fondclair);
                    lbTravaillerPar.setForeground(fondclair);
                    pann.setBackground(fondsombres);
                    tfNomVille.setBackground(fondelementsombre);
                    cbListeVille.setBackground(fondelementsombre);
                    CbActions.setBackground(fondelementsombre);
                    chMode.setForeground(fondclair);
                    chMode.setText("Mode clair");
                    btEnregistre.setBackground(fondelementsombre);
                    btEnregistre.setForeground(fondsombres);
                    cbListeVille.setForeground(fondsombres);
                    lbEtat.setForeground(fondclair);
        
                } else {
                    lbTitrePrincipal.setForeground(fondElementClair);
                    lbTravaillerPar.setForeground(fondElementClair);
                    pann.setBackground(fondclair);
                    tfNomVille.setBackground(fondElementClair);
                    cbListeVille.setBackground(fondElementClair);
                    CbActions.setBackground(fondElementClair);
                    chMode.setForeground(fondElementClair);
                    chMode.setText("Mode sombre");
                    btEnregistre.setBackground(fondElementClair);
                    btEnregistre.setForeground(fondclair);
                    cbListeVille.setForeground(fondclair);
                    lbEtat.setForeground(fondElementClair);
        
                }
            }
        });
        // La connection à la base de donnée
        connectionDB();
    }

    // fonction pour connecter la base de donnée
    private void connectionDB() {
        String DB_URL = "jdbc:mysql://localhost:3306/";
        try (
                Connection ccc = DriverManager.getConnection(DB_URL, user, pswd);
                Statement stmt = ccc.createStatement();) {
            String sql = "CREATE DATABASE IF NOT EXISTS ville ";
            int rwaff = stmt.executeUpdate(sql);
            if (rwaff > 0) {
                System.out.println("Connexion réussie");
            } else {
                JOptionPane.showMessageDialog(null, "Erreur de connectiuon à la base de donnée",
                        "Connection à la base de donnée", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("there is an error\n" + e.getMessage());
        }

    }

    public static void MiseAJour() {

        // on efface puis on ajoute de nouveau les items
        cbListeVille.removeAllItems();
        for (String str : recupereBD()) {
            cbListeVille.addItem(str);
        }

    }

    static ArrayList<String> recupereBD() {
        // on crée un tableau(ensemble dont la taille est dynamique)
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

    // La fonction pour ajouter des villes
    static void ajouteUneVille(String nomVille) {
        LaRequeteSql("INSERT INTO villes(nom) VALUES('" + nomVille + "')");
        cbListeVille.addItem(nomVille);
    }

    // la fonction pour effacer une ville
    static void EffaceUneVille(String nomVille) {
        LaRequeteSql("DELETE FROM villes WHERE nom=('" + nomVille + "')");
    }

    // la fonction des requetes sql
    public static void LaRequeteSql(String sql) {
        try (
                Connection ccc = DriverManager.getConnection(DU_URL, user, pswd);
                Statement stmt = ccc.createStatement();) {

            int rwaff = stmt.executeUpdate(sql);
            if (rwaff > 0) {
                JOptionPane.showMessageDialog(null, "succès", "Actions sur des villes",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue", "Actions sur des villes",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("there is an error\n" + e.getMessage());
        }
    }

}