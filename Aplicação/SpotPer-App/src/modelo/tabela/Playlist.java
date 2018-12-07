package modelo.tabela;
import modelo.Conexao;
import modelo.Executar;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Playlist extends Conexao {
    private int id;
    private String nome;
    private String descricao;
    private Date date;

    public void setNome(String nome) { this.nome = nome; }
    public void setDate(Date date) { this.date = date; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public Date getDate() { return date; }
    public int getId() { return id; }

    public boolean inserir(){
        boolean executou = false;
        int valor = maxId()+1;
        String valores = "insert into Playlist values( ?, ?, getdate(), 0)";

        try {
            abrirConexao();
            if (conectou()){
                PreparedStatement s = getC().prepareStatement(valores);
                s.setInt(1, valor);
                s.setString(2, nome);
                s.executeUpdate();
                fecharConexao();
                s.close();
                executou = true;
            }
            if (conectou()) fecharConexao();


        } catch (Exception e) {
            System.out.println("Na inserção: "+e);
        }

        return executou;
    }

    public int maxId(){
        String consulta = "select max(Id_playlist) from Playlist";
        boolean executou = false;
        int resultado = 0;

        try {
            abrirConexao();
            if (conectou()) {
                PreparedStatement ps = getC().prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    resultado = rs.getInt(1);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println("pesquisar id: "+e);
        }

        return resultado;
    }

    public int tempoExecucao(){
        String consulta = "select sum(tempo_execucao) from Playlist where id_playlist = ? ";
        boolean executou = false;
        int resultado = 0;

        try {
            abrirConexao();
            if (conectou()) {
                PreparedStatement ps = getC().prepareStatement(consulta);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    resultado = rs.getInt(1);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println("pesquisar id: "+e);
        }

        return resultado;
    }
    public boolean addFaixa(Faixa f) {
        boolean executou = false;

        String valores = "insert into AlbumFaixaPlaylist values( ?, ?, ?, ?, ?)";

        try {
            abrirConexao();
            if (conectou()){
                PreparedStatement s = getC().prepareStatement(valores);
                s.setInt(1, 0);
                s.setDate(2, new Date(1994, 03, 16));
                s.setInt(3, id);
                s.setInt(4, f.getAlbum());
                s.setInt(5, f.getNumero());

                s.executeUpdate();
                fecharConexao();
                s.close();
                executou = true;
            }

            if (executou){
                valores = "update Playlist set tempo_execucao = ? where id_playlist = ?";
                try {
                    abrirConexao();
                    if (conectou()){
                        PreparedStatement s = getC().prepareStatement(valores);
                        s.setInt(1, tempoExecucao()+f.getTempo());
                        s.setInt(2, getId());

                        s.executeUpdate();

                        s.close();
                        executou = true;
                        fecharConexao();
                    }

                } catch (Exception e) {
                    System.out.println("Na inserção: "+e);
                }
            }

            if (conectou()) fecharConexao();


        } catch (Exception e) {
            System.out.println("Na inserção: "+e);
        }


        return executou;
    }
}
