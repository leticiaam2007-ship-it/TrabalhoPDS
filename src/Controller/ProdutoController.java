package Controller;


import java.util.List;

import Dao.ProdutoDAO;
import Model.Produto;

public class ProdutoController {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public boolean cadastrarProduto(String nome, String descricao, double preco, int estoque) {
        return produtoDAO.cadastrar(new Produto(nome, descricao, preco, estoque));
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listar();
    }

    public boolean atualizarProduto(int id, String nome, String descricao, double preco, int estoque) {
        return produtoDAO.atualizar(new Produto(id, nome, descricao, preco, estoque));
    }

    public boolean removerProduto(int id) {
        return produtoDAO.remover(id);
    }

    public boolean atualizarEstoque(int idProduto, int novoEstoque) {
        return produtoDAO.atualizarEstoque(idProduto, novoEstoque);
    }
}