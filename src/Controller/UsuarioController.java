package Controller;

import Dao.UsuarioDAO;
import Model.Usuario;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean cadastrarUsuario(String nome, String cpf, boolean administrador) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome.trim());
        usuario.setCpf(cpf.trim());
        usuario.setAdministrador(administrador);

        return usuarioDAO.cadastrar(usuario);
    }

    public Usuario login(String nome, String cpf) {
        return usuarioDAO.login(nome.trim(), cpf.trim());
    }
}