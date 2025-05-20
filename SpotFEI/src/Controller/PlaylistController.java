package Controller;

import View.Menu;
import View.Playlists;

public class PlaylistController {
    private Playlists tela_playlist;

    public PlaylistController(Playlists tela_playlist) {
        this.tela_playlist = tela_playlist;
    }
    
    public void VoltarMenu(){
        tela_playlist.setVisible(false);
        new Menu().setVisible(true);
    }
}
