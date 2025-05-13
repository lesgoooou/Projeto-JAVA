package spotfei;
import View.Login;
import View.Menu;
import View.Msc_Curtidas;
import View.Playlists;
import View.Historico;


public class Main {
    public static void main(String[] args) {
        Login aba_1 = new Login(); 
        Menu aba_2 = new Menu();
        Msc_Curtidas aba_3 = new Msc_Curtidas();
        Playlists aba_4 = new Playlists();
        Historico aba_5 = new Historico();
        
        aba_1.setMenu(aba_2);
        aba_2.setLogin(aba_1);
        aba_2.setMsc_curtidas(aba_3);
        aba_2.setPlaylists(aba_4);
        aba_2.setHistorico(aba_5);
        aba_5.setMenu(aba_2);
        
        aba_1.setVisible(true);
    }
    
}
