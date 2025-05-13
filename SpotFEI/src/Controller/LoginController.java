package Controller;

import View.Login;
import Model.User;

import DAO.Conexao;
import DAO.UserDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LoginController {
    private Login tela_login;
    
    public LoginController(Login tela_login){
        this.tela_login = tela_login;
    }
    
    public void loginUser(){
        User usuario = new User( 
                tela_login.getTxt_user_login().getText(),
                tela_login.getTxt_senha_login().getText(),
                null);
        Conexao conexao = new Conexao();
        try{ 
            Connection conn = conexao.getConnection();
            UserDAO dao = new UserDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                JOptionPane.showMessageDialog(tela_login, 
                                              "Login realizado!",
                                              "Aviso",
                                              JOptionPane.INFORMATION_MESSAGE);
                irParaMenu();
                /*User usuario2 = new User(res.getString('usuario'),
                                         res.getString('senha'));
                                         */
            } else {
                JOptionPane.showMessageDialog(tela_login,
                                              "Login não efetuado!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(tela_login, 
                                              "Erro de conexão com base SQL!", 
                                              "Traceback: ",
                                              JOptionPane.ERROR_MESSAGE);
            
        }
    }
    public void irParaMenu() {
        tela_login.setVisible(false);
        //tela_menu.setVisible(true);
    }
    
}
