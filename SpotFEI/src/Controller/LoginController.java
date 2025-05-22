package Controller;

import View.Entrada;
import View.Menu;

import Model.Usuario;
import Model.SessaoUsuario;

import DAO.Conexao;
import DAO.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class LoginController {
    private Entrada tela_login;
    private Menu tela_menu;
    
    public LoginController(Entrada tela_login){
        this.tela_login = tela_login;
        this.tela_menu = new Menu();
    }
    
    public void loginUser(){
        Usuario usuario = new Usuario( 
                tela_login.getTxt_user_login().getText(),
                tela_login.getTxt_senha_login().getText(),
                null);
        Conexao conexao = new Conexao();
        try{ 
            Connection conn = conexao.getConnection();
            UserDAO dao = new UserDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                Usuario usuarioLogado = new Usuario(
                res.getString("user_id"),  
                res.getString("senha"),
                null
                );
                SessaoUsuario.setUsuario(usuarioLogado);
                JOptionPane.showMessageDialog(tela_login, 
                                              "Login realizado!",
                                              "Aviso",
                                              JOptionPane.INFORMATION_MESSAGE);
                irParaMenu();
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
    public void encerrar(){
        JButton btnEncerrar = tela_login.getBotao_Encerrar();
        btnEncerrar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int confirmar = JOptionPane.showConfirmDialog(
                null,
                "Obrigado pela visita, mas para confirmar!\nTem certeza que deseja sair?",
                "Sair",
                JOptionPane.YES_NO_OPTION
            );
            if (confirmar == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    });
    }
    public void irParaMenu() {
        tela_login.getTxt_user_login().setText("");
        tela_login.getTxt_senha_login().setText("");
        
        tela_login.setVisible(false);
        tela_menu.setVisible(true);
    }
    
}
