package Controller;

import View.Menu;
import View.Entrada;
import View.Playlists;
import View.Msc_Curtidas;
import View.Historico;

public class MenuController {
    private Menu tela_menu;
    //private Entrada tela_inicial;

    public MenuController(Menu tela_menu) {
        this.tela_menu = tela_menu;
        //this.tela_inicial = new Entrada();
    }
    
    public void irParaInicio(){
        tela_menu.setVisible(false);
        //tela_inicial.setVisible(true);
    }
}

