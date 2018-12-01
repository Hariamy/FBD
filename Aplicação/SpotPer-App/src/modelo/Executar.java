package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Executar extends Conexao{
    private boolean executou;

    public boolean update(String up){
        executou = false;
        try {
            abrirConexao();
            if (conectou()){
                Statement s = getC().createStatement();
                s.executeUpdate(up);
                fecharConexao();
                s.close();
                executou = true;
            }
            if (conectou()) fecharConexao();


        } catch (Exception e) {
            System.out.println(e);
        }
        return executou;
    }

    public boolean select(String sel){
        executou = false;
        try {
            abrirConexao();
            if (conectou()) {
                Statement ps = getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                while(rs.next()){
                    System.out.println("Codigooooo: "+rs.getInt("cod")+ " Nomeeee: " + rs.getString("nome"));
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return executou;
    }

    /*
    public String inseriTabela(String tabela) {

        String criar = "create table " + tabela + "(" +
                "cod int not null," +
                "nome varchar(50)" +
                ")";
        update(criar);
        return "Vê lá se deu bom";
    }

    public String inserirTupla(String tabela, int codigo, String nome){
        String criar = "insert into "+ tabela + " values(" +
                codigo + ", '" + nome + "')";

        update(criar);
        return "ve la se deu bom";
    }

    public String consulta(String tabela){
        String criar = "select * from " + tabela;
        return select(criar);
    }
    */
}
