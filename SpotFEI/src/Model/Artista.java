package Model;

public class Artista extends Pessoa {
    private String genero;
    
    public Artista(String nome, int id, String genero) {
        super(nome, id);
        this.genero = genero;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    @Override
    public String toString() {
        return "Artista: " + nome + " - Gênero: " + genero;
    }
}