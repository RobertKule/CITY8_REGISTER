import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainClassDuProjet {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

        } catch (Exception e) {
            // erreur du look
        }
        LesVilles v = new LesVilles();
        v.setVisible(true);

    }
}
