package App;
import javax.swing.SwingUtilities;
import View.Janela;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Janela().setVisible(true);
        });
    }
}