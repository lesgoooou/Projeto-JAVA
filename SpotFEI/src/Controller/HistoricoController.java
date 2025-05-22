package Controller;

import View.Menu;
import View.Historico;

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
