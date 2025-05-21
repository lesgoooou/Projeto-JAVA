package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PlayList extends Pessoa {
    private String userId;
    private java.sql.Timestamp data_criacao;
    
    SimpleDateFormat dia_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    public PlayList(String nome, String userId, int id, Timestamp data_criacao) {
        super(nome, id);
        this.userId = userId;
        this.data_criacao = data_criacao;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Timestamp getData_criacao() {
        return data_criacao;
    }
    
    public void setData_criacao(Timestamp data_criacao) {
        this.data_criacao = data_criacao;
    }
    
    @Override
    public String toString() {
        String diaHora = dia_hora.format(data_criacao);
        return "Nome: " + nome + " - Usuario: " + userId + " - Data de Criação: " + diaHora;
    }
}
