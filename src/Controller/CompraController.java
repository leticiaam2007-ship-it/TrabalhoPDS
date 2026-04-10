package Controller;


import Model.Carrinho;
import Model.ItemCarrinho;
import Model.Produto;
import Model.Usuario;

public class CompraController {
    private Carrinho carrinho = new Carrinho();
    private ProdutoController produtoController = new ProdutoController();

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        carrinho.adicionarProduto(produto, quantidade);
    }

    public void removerDoCarrinho(int produtoId) {
        carrinho.removerProduto(produtoId);
    }

    public double getTotal() {
        return carrinho.calcularTotal();
    }

    public String gerarNotaFiscal(Usuario usuario) {
        StringBuilder nota = new StringBuilder();
        nota.append("========= NOTA FISCAL =========\n");
        nota.append("Cliente: ").append(usuario.getNome()).append("\n");
        nota.append("CPF: ").append(usuario.getCpf()).append("\n\n");

        for (ItemCarrinho item : carrinho.getItens()) {
            Produto p = item.getProduto();
            int novoEstoque = p.getEstoque() - item.getQuantidade();
            produtoController.atualizarEstoque(p.getId(), novoEstoque);

            nota.append("- ").append(p.getNome())
                .append(" | Qtd: ").append(item.getQuantidade())
                .append(" | Subtotal: R$ ").append(String.format("%.2f", item.getSubtotal()))
                .append("\n");
        }

        nota.append("\nTOTAL PAGO: R$ ").append(String.format("%.2f", getTotal()));
        carrinho.limpar();

        return nota.toString();
    }
}