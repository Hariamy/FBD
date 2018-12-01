package modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection c;
    private boolean conectado;

    public Connection getC() { return c; }

    public boolean conectou(){ return conectado; }

    public void abrirConexao() {
        conectado = false;
        try {
            String url = "jdbc:sqlserver://HARIAMY-PC:1433;databaseName=teste";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, "sa", "admin123");
            conectado = true;

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void fecharConexao() {
        try {
            c.close();
            conectado = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
