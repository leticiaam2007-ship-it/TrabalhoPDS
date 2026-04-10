package Controller;


import Dao.UsuarioDAO;
import Model.Usuario;

public class LoginController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario fazerLogin(String nome, String cpf) {
        return usuarioDAO.login(nome, cpf);
    }
}