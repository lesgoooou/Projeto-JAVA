package Model;

public class Musica {
    private String nome, genero, artista;
    private int id;
    private Boolean curtida;
    
    public Musica(String nome, String genero, String artista, int id, Boolean curtida) {
        this.nome = nome;
        this.genero = genero;
        this.artista = artista;
        this.id = id;
        this.curtida = curtida;
    }
    
    @Override
    public String toString() {
    String curtidaSymbol = this.curtida ? " - ♥" : "";
    return nome + " - " + artista + " - " + genero + curtidaSymbol;
    }
}

