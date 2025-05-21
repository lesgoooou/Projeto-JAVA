package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
        
public class PlayList {
    private String nome, userId;
    private int id;
    private java.sql.Timestamp data_criacao;

    SimpleDateFormat dia_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    public PlayList(String nome, String userId, int id, Timestamp data_criacao) {
        this.nome = nome;
        this.userId = userId;
        this.id = id;
        this.data_criacao = data_criacao;
    }
    
    @Override
    public String toString() {
    String diaHora = dia_hora.format(data_criacao);
    return "Nome: "+nome + " - Usuario: " + userId + " - Data de Criação: "+ diaHora;
    }
}
