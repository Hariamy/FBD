package modelo;

import modelo.tabela.Album;
import modelo.tabela.Faixa;
import modelo.tabela.Gravadora;
import modelo.tabela.Playlist;
import sun.java2d.windows.GDIRenderer;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

    public ArrayList<Album> selectAlbuns(){
        ArrayList<Album> albuns = new ArrayList<>();
        String sel = "select * from album";
        try {
            abrirConexao();
            if (conectou()) {
                Statement ps = getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                while(rs.next()){
                    Album a = new Album();
                    a.setDescricao(rs.getString("descricao"));
                    a.setCodigo(rs.getInt("cod_album"));
                    a.setGravadora(rs.getInt("cod_gravadora"));
                    a.setPreco(rs.getFloat("preco"));

                    albuns.add(a);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return albuns;
    }

    public ArrayList<Album> selectAlbunsPesquisa(String pesquisa){
        ArrayList<Album> albuns = new ArrayList<>();
        String sel = "select * from album where Descricao like '%'+?+'%'";
        try {
            abrirConexao();
            if (conectou()) {
                PreparedStatement ps = getC().prepareStatement(sel);
                ps.setString(1, pesquisa);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Album a = new Album();
                    a.setDescricao(rs.getString("descricao"));
                    a.setCodigo(rs.getInt("cod_album"));
                    a.setGravadora(rs.getInt("cod_gravadora"));
                    a.setPreco(rs.getFloat("preco"));

                    albuns.add(a);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return albuns;
    }


    public ArrayList<Playlist> selectPlaylist(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        String sel = "select * from playlist";
        try {
            abrirConexao();
            if (conectou()) {
                Statement ps = getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                while(rs.next()){
                    Playlist a = new Playlist();
                    a.setNome(rs.getString("nome"));
                    a.setId(rs.getInt(("id_playlist")));

                    playlists.add(a);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return playlists;
    }


    public int contarPlaylist(){
        String sel = "select count(*) from playlist";
        int resposta = 0;
        try {
            abrirConexao();
            if (conectou()) {
                Statement ps = getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                while(rs.next()){
                    resposta = rs.getInt(1);

                }
                ps.close();
                fecharConexao();
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return resposta;
    }

    public ArrayList<Faixa> selectFaixas(int codigoAlbum){
        ArrayList<Faixa> faixas = new ArrayList<>();
        String sel = "select * from faixa as f inner join albumfaixa as af on af.cod_faixa = f.numero where af.Cod_album = ?";
        try {
            abrirConexao();
            if (conectou()) {
                PreparedStatement ps = getC().prepareStatement(sel);
                ps.setInt(1, codigoAlbum);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Faixa f = new Faixa();
                    f.setNumero(rs.getInt(1));
                    f.setDescricao(rs.getString(2));
                    f.setTipoComposicao(rs.getString(3));
                    f.setCompositor(rs.getInt(4));
                    f.setAlbum(rs.getInt(5));
                    f.setTempo(rs.getInt(6));
                    f.setTipoGravacao(rs.getString(7));

                    faixas.add(f);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return faixas;
    }

    public int contarGravadoras(){
        String sel = "select count(*) from Gravadora";
        int resposta = 0;
        try {
            abrirConexao();
            if (conectou()) {
                Statement ps = getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                while(rs.next()){
                    resposta = rs.getInt(1);

                }
                ps.close();
                fecharConexao();
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return resposta;
    }

    public ArrayList<Gravadora> selectGravadoras(){
        ArrayList<Gravadora> faixas = new ArrayList<>();
        String sel = "select * from Gravadora";
        try {
            abrirConexao();
            if (conectou()) {
                PreparedStatement ps = getC().prepareStatement(sel);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Gravadora f = new Gravadora();
                    f.setCodigo(rs.getInt("cod_gravadora"));
                    f.setNome(rs.getString("nome"));
                    f.setSite(rs.getString("site"));

                    faixas.add(f);
                }
                ps.close();
                fecharConexao();
                executou = true;
            }
            if (conectou()) fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return faixas;
    }

    public boolean updateGravadora(int id, String descrica, int grav, String preco, String data, String compra){
        executou = false;
        String up = "update Album set preco = ?, cod_gravadora = ?, dt_compra = \'"+ data + "\', tp_compra = ?, descricao = ? where cod_album = ?";
        try {
            abrirConexao();
            if (conectou()){
                PreparedStatement s = getC().prepareStatement(up);

                s.setDouble(1, Double.parseDouble(preco));
                s.setInt(2, grav);
                s.setString(3, compra);
                s.setString(4, descrica);
                s.setInt(5, id);


                s.executeUpdate();
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

}
