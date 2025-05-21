package Model;

public abstract class Pessoa {
    protected String nome;
    protected int id;
    
    public Pessoa(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
    
    // Getters e setters básicos
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // Método abstrato que cada subclasse deve implementar
    public abstract String toString();
}