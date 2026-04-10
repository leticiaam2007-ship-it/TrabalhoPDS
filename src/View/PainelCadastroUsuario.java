package View;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class PainelCadastroUsuario extends JPanel {

    private static final long serialVersionUID = 1L;

    private Janela janela;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JCheckBox chkAdmin;

    public PainelCadastroUsuario(Janela janela) {
        this.janela = janela;

       

        JPanel card = new JPanel(new MigLayout("wrap 1, fillx, insets 40", "[grow,fill]"));
       

        JLabel titulo = new JLabel("Cadastro de Usuário");
        

        txtNome = new JTextField();
        txtCpf = new JTextField();
        chkAdmin = new JCheckBox("Cadastrar como administrador");

     

        JButton btnSalvar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

 

        card.add(titulo, "center, wrap 20");
        card.add(new JLabel("Nome:"));
        card.add(txtNome, "growx, h 40!");
        card.add(new JLabel("CPF:"));
        card.add(txtCpf, "growx, h 40!");
        card.add(chkAdmin, "wrap 20");
        card.add(btnSalvar, "growx, h 42!, wrap 10");
        card.add(btnVoltar, "growx, h 42!");

        add(card, "center, w 420!, h 420!");

        btnSalvar.addActionListener(e -> cadastrar());
        btnVoltar.addActionListener(e -> janela.mostrarTela("login"));
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        boolean admin = chkAdmin.isSelected();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        boolean sucesso = new Controller.UsuarioController().cadastrarUsuario(nome, cpf, admin);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            janela.mostrarTela("login");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
        }
    }
}