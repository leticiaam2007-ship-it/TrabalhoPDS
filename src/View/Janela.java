package View;


import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Model.Usuario;


public class Janela extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
    private JPanel painelPrincipal;

    public Janela() {
        setTitle("Supermercado MVC");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
///        painelPrincipal.setBackground(Estilo.FUNDO);

        painelPrincipal.add(new PainelLogin(this), "login");
        painelPrincipal.add(new PainelCadastroUsuario(this), "cadastroUsuario");

        add(painelPrincipal);



    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(painelPrincipal, nomeTela);
    }

    public void abrirTelaProdutos(Usuario usuario) {
        painelPrincipal.add(new PainelProdutos(this, usuario), "produtos");
        mostrarTela("produtos");
    }

    public void abrirTelaCompra(Usuario usuario) {
        painelPrincipal.add(new PainelCompra(this, usuario), "compra");
        mostrarTela("compra");
    }
}