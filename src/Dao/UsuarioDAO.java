package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Usuario;
import Util.Conexao;

public class UsuarioDAO {

    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, cpf, administrador) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("=== CADASTRANDO USUÁRIO ===");
            System.out.println("Nome: [" + usuario.getNome().trim() + "]");
            System.out.println("CPF: [" + usuario.getCpf().trim() + "]");
            System.out.println("Admin: [" + usuario.isAdministrador() + "]");

            stmt.setString(1, usuario.getNome().trim());
            stmt.setString(2, usuario.getCpf().trim());
            stmt.setBoolean(3, usuario.isAdministrador());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Linhas inseridas: " + linhasAfetadas);

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String nome, String cpf) {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("=== TENTANDO LOGIN ===");
            System.out.println("Nome digitado: [" + nome.trim() + "]");
            System.out.println("CPF digitado: [" + cpf.trim() + "]");

            stmt.setString(1, cpf.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeBanco = rs.getString("nome").trim();
                String cpfBanco = rs.getString("cpf").trim();
                boolean adminBanco = rs.getBoolean("administrador");

                System.out.println("=== USUÁRIO ENCONTRADO NO BANCO ===");
                System.out.println("Nome banco: [" + nomeBanco + "]");
                System.out.println("CPF banco: [" + cpfBanco + "]");
                System.out.println("Admin banco: [" + adminBanco + "]");

                // compara nome ignorando maiúsculas/minúsculas e espaços
                if (nomeBanco.equalsIgnoreCase(nome.trim())) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(nomeBanco);
                    usuario.setCpf(cpfBanco);
                    usuario.setAdministrador(adminBanco);
                    return usuario;
                } else {
                    System.out.println("Nome não confere.");
                }
            } else {
                System.out.println("Nenhum usuário encontrado com esse CPF.");
            }

        } catch (SQLException e) {
            System.out.println("Erro no login: " + e.getMessage());
        }

        return null;
    }
}