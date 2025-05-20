package Controller;

import DAO.Conexao;
import DAO.HistoricoDAO;

import Model.SessaoUsuario;
import Model.Usuario;
import View.Menu;
import View.Historico;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import java.sql.ResultSet;

public class HistoricoController {
    private Historico tela_historico;

    public HistoricoController(Historico tela_historico) {
        this.tela_historico = tela_historico;
    }
    
    public void VoltarMenu(){
        tela_historico.setVisible(false);
        new Menu().setVisible(true);
    }
}
