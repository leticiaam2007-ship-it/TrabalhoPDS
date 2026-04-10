package View;



import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.CompraController;
import Controller.ProdutoController;
import Model.ItemCarrinho;
import Model.Produto;
import Model.Usuario;
import net.miginfocom.swing.MigLayout;


public class PainelCompra extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

    private JTable tabelaProdutos, tabelaCarrinho;
    private DefaultTableModel modeloProdutos, modeloCarrinho;
    private JTextField txtQuantidade;
    private JLabel lblTotal;
    private List<Produto> produtos;

    private ProdutoController produtoController = new ProdutoController();
    private CompraController compraController = new CompraController();

    public PainelCompra(Janela janela, Usuario usuario) {
        this.usuario = usuario;

       
        setLayout(new MigLayout("fill, insets 15", "[grow 60][grow 40]", "[][grow]"));

        JLabel titulo = new JLabel("Área de Compras - Cliente");
        add(titulo, "span, wrap");

        modeloProdutos = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "Preço", "Estoque"}, 0);
        tabelaProdutos = new JTable(modeloProdutos);

        modeloCarrinho = new DefaultTableModel(new String[]{"ID", "Produto", "Qtd", "Subtotal"}, 0);
        tabelaCarrinho = new JTable(modeloCarrinho);

        JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);
        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);

        JPanel lateral = new JPanel(new MigLayout("wrap 1, fillx, insets 20", "[grow]"));
        

        txtQuantidade = new JTextField("1");
       

        lblTotal = new JLabel("Total: R$ 0.00");
     

        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnRemover = new JButton("Remover do Carrinho");
        JButton btnDetalhes = new JButton("Ver Detalhes do Produto");
        JButton btnFinalizar = new JButton("Finalizar Compra");
        JButton btnLogout = new JButton("Logout");

  

        lateral.add(new JLabel("Quantidade:"));
        lateral.add(txtQuantidade, "growx, h 40!");
        lateral.add(btnAdicionar, "growx, h 40!");
        lateral.add(btnDetalhes, "growx, h 40!");
        lateral.add(scrollCarrinho, "grow, h 250!");
        lateral.add(btnRemover, "growx, h 40!");
        lateral.add(lblTotal, "center");
        lateral.add(btnFinalizar, "growx, h 45!");
        lateral.add(btnLogout, "growx, h 40!");

        add(scrollProdutos, "grow");
        add(lateral, "grow");

        carregarProdutos();

        btnAdicionar.addActionListener(e -> adicionar());
        btnRemover.addActionListener(e -> removerCarrinho());
        btnDetalhes.addActionListener(e -> verDetalhesProduto());
        btnFinalizar.addActionListener(e -> finalizarCompra());
        btnLogout.addActionListener(e -> janela.mostrarTela("login"));
    }

    private void carregarProdutos() {
        modeloProdutos.setRowCount(0);
        produtos = produtoController.listarProdutos();

        for (Produto p : produtos) {
            modeloProdutos.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getEstoque()});
        }
    }

    private void adicionar() {
        int linha = tabelaProdutos.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
            return;
        }

        try {
            int qtd = Integer.parseInt(txtQuantidade.getText());
            Produto produto = produtos.get(linha);

            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.");
                return;
            }

            if (qtd > produto.getEstoque()) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente.");
                return;
            }

            compraController.adicionarAoCarrinho(produto, qtd);
            atualizarCarrinho();
            JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Digite uma quantidade válida.");
        }
    }

    private void atualizarCarrinho() {
        modeloCarrinho.setRowCount(0);

        for (ItemCarrinho item : compraController.getCarrinho().getItens()) {
            modeloCarrinho.addRow(new Object[]{
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    String.format("%.2f", item.getSubtotal())
            });
        }

        lblTotal.setText("Total: R$ " + String.format("%.2f", compraController.getTotal()));
    }

    private void removerCarrinho() {
        int linha = tabelaCarrinho.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item do carrinho.");
            return;
        }

        int idProduto = Integer.parseInt(modeloCarrinho.getValueAt(linha, 0).toString());
        compraController.removerDoCarrinho(idProduto);
        atualizarCarrinho();
    }

    private void verDetalhesProduto() {
        int linha = tabelaProdutos.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
            return;
        }

        Produto p = produtos.get(linha);

        String detalhes = "===== DETALHES DO PRODUTO =====\n\n"
                + "Nome: " + p.getNome() + "\n"
                + "Descrição: " + p.getDescricao() + "\n"
                + "Preço: R$ " + String.format("%.2f", p.getPreco()) + "\n"
                + "Estoque: " + p.getEstoque();

        JOptionPane.showMessageDialog(this, detalhes);
    }

    private void finalizarCompra() {
        if (compraController.getCarrinho().getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio.");
            return;
        }

        String nota = compraController.gerarNotaFiscal(usuario);
        JOptionPane.showMessageDialog(this, nota);

        atualizarCarrinho();
        carregarProdutos();
    }
}