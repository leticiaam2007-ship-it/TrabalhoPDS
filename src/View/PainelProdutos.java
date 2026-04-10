package View;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.ProdutoController;
import Model.Produto;
import Model.Usuario;
import net.miginfocom.swing.MigLayout;


public class PainelProdutos extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtId, txtNome, txtDescricao, txtPreco, txtEstoque;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ProdutoController controller = new ProdutoController();

    public PainelProdutos(Janela janela, Usuario usuario) {
     
        setLayout(new MigLayout("", "[221px][7px][192px]", "[28px][290px]"));

        JLabel titulo = new JLabel("Painel do Administrador");
      
        add(titulo, "cell 0 0 3 1,alignx left,aligny top");

        JPanel form = new JPanel(new MigLayout("wrap 2, fillx, insets 25", "[right][grow,fill]"));
      
     

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtDescricao = new JTextField();
        txtPreco = new JTextField();
        txtEstoque = new JTextField();



        JButton btnSalvar = new JButton("Cadastrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnLogout = new JButton("Logout");

      

        form.add(new JLabel("ID:")); form.add(txtId, "growx");
        form.add(new JLabel("Nome:")); form.add(txtNome, "growx");
        form.add(new JLabel("Descrição:")); form.add(txtDescricao, "growx");
        form.add(new JLabel("Preço:")); form.add(txtPreco, "growx");
        form.add(new JLabel("Estoque:")); form.add(txtEstoque, "growx");

        form.add(btnSalvar, "span, split 2, growx");
        form.add(btnEditar, "growx");
        form.add(btnRemover, "span, split 2, growx");
        form.add(btnLimpar, "growx");
        form.add(btnLogout, "span, growx");

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "Preço", "Estoque"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        add(form, "cell 0 1,grow");
        add(scroll, "cell 2 1,grow");

        carregarTabela();

        btnSalvar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnRemover.addActionListener(e -> remover());
        btnLimpar.addActionListener(e -> limpar());
        btnLogout.addActionListener(e -> janela.mostrarTela("login"));

        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Produto> lista = controller.listarProdutos();

        for (Produto p : lista) {
            modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getEstoque()});
        }
    }

    private void cadastrar() {
        try {
            boolean sucesso = controller.cadastrarProduto(
                    txtNome.getText(),
                    txtDescricao.getText(),
                    Double.parseDouble(txtPreco.getText()),
                    Integer.parseInt(txtEstoque.getText())
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                carregarTabela();
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Preencha os campos corretamente.");
        }
    }

    private void editar() {
        try {
            boolean sucesso = controller.atualizarProduto(
                    Integer.parseInt(txtId.getText()),
                    txtNome.getText(),
                    txtDescricao.getText(),
                    Double.parseDouble(txtPreco.getText()),
                    Integer.parseInt(txtEstoque.getText())
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                carregarTabela();
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }

    private void remover() {
        try {
            int id = Integer.parseInt(txtId.getText());

            if (controller.removerProduto(id)) {
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
                carregarTabela();
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtDescricao.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtPreco.setText(modeloTabela.getValueAt(linha, 3).toString());
            txtEstoque.setText(modeloTabela.getValueAt(linha, 4).toString());
        }
    }

    private void limpar() {
        txtId.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
    }
}