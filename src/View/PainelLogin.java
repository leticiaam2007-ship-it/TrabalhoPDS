package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import net.miginfocom.swing.MigLayout;


import Controller.UsuarioController;
import Model.Usuario;

public class PainelLogin extends JPanel {

    private static final long serialVersionUID = 1L;

    private Janela janela;
    private JTextField txtNome;
    private JTextField txtCpf;

    public PainelLogin(Janela janela) {
        this.janela = janela;

        
        setLayout(new MigLayout("fill, insets 30", "[grow]", "[grow]"));

        JPanel card = new JPanel(new MigLayout("wrap 1, fillx, insets 40", "[grow,fill]"));
       

        JLabel titulo = new JLabel("Login do Sistema");
     

        txtNome = new JTextField();
        txtCpf = new JTextField();

      

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar-se");


        card.add(titulo, "center, wrap 20");
        card.add(new JLabel("Nome:"));
        card.add(txtNome, "growx, h 40!");
        card.add(new JLabel("CPF:"));
        card.add(txtCpf, "growx, h 40!");
        card.add(btnEntrar, "growx, h 42!, wrap 10");
        card.add(btnCadastrar, "growx, h 42!");

        add(card, "align center");

        
        btnEntrar.addActionListener(e -> fazerLogin());
        btnCadastrar.addActionListener(e -> janela.mostrarTela("cadastroUsuario"));
    }

    private void fazerLogin() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();

        System.out.println("=== BOTÃO LOGIN CLICADO ===");
        System.out.println("Nome digitado: " + nome);
        System.out.println("CPF digitado: " + cpf);

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        UsuarioController controller = new UsuarioController();
        Usuario usuario = controller.login(nome, cpf);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

            if (usuario.isAdministrador()) {
                janela.abrirTelaProdutos(usuario);
            } else {
                janela.abrirTelaCompra(usuario);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
        }
    }
}