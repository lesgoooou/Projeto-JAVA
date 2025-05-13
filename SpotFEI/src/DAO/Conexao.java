package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public Connection getConnection() throws SQLException{
    Connection conexao = DriverManager.getConnection(
            // AJEITAR
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres", "gabriel21");
    return conexao;
    }
}