package Controller;

import View.Entrada;
import View.Menu;
import Model.Usuario;

import DAO.Conexao;
import DAO.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CadastroController {
    private Entrada tela_cadastro;
    private Menu tela_menu;

    public CadastroController(Entrada tela_cadastro) {
        this.tela_cadastro = tela_cadastro;
        this.tela_menu = new Menu();
    }
    public void irParaMenu() {
        tela_cadastro.getTxt_user_cadastro().setText("");
        tela_cadastro.getTxt_senha_cadastro().setText("");
        tela_cadastro.getTxt_conf_senha_cadastro().setText("");
        
        tela_cadastro.setVisible(false);
        tela_menu.setVisible(true);
    }
    
    public void salvarUsuario(){
        String user_id = tela_cadastro.getTxt_user_cadastro().getText();
        String senha = tela_cadastro.getTxt_senha_cadastro().getText();
        String conf_senha = tela_cadastro.getTxt_conf_senha_cadastro().getText();
        Usuario usuario = new Usuario(user_id,senha,conf_senha);
        
        if (!senha.equals(conf_senha)) {
        JOptionPane.showMessageDialog(tela_cadastro, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
        }
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            UserDAO dao = new UserDAO(conn);
            dao.inserir(usuario);
            JOptionPane.showMessageDialog(tela_cadastro,"Usuario Cadastrado!","Aviso",JOptionPane.INFORMATION_MESSAGE);
            irParaMenu();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(tela_cadastro, "Usuário não cadastrado!","Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
}