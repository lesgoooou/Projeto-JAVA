package Model;

public class Musica extends Pessoa{
    private String genero, artista;
    private Boolean curtida;
    
    public Musica(String nome, String genero, String artista, int id, Boolean curtida) {
        super(nome, id);
        this.genero = genero;
        this.artista = artista;
        this.curtida = curtida;
    }
    public String getGenero() {
        return genero;
    }
    
    public String getArtista() {
        return artista;
    }
    
    public Boolean getCurtida() {
        return curtida;
    }
    
    public void setCurtida(Boolean curtida) {
        this.curtida = curtida;
    }
    
    @Override
    public String toString() {
    String curtidaSymbol = this.curtida ? " - ♥" : "";
    return nome + " - " + artista + " - " + genero + curtidaSymbol;
    }
}

