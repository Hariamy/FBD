package controlador;

import jdk.nashorn.internal.runtime.ECMAErrors;
import modelo.tabela.Album;
import modelo.tabela.Faixa;
import modelo.tabela.Gravadora;
import modelo.tabela.Playlist;
import modelo.Executar;

import java.util.ArrayList;

public class Controlador {
    ArrayList<Album> albuns = new ArrayList<Album>();
    public Controlador() {
        //chama o slq aqui e vou criar uma lista de paranaues


    }

    public ArrayList<Album> getAlbuns() {
        Executar e = new Executar();
        return e.selectAlbuns();
    }

    public ArrayList<Playlist> getPlaylist(){
        Executar e = new Executar();
        return e.selectPlaylist();
    }

    public int getQuantidadePlaylist(){
        Executar e = new Executar();
        return e.contarPlaylist();
    }
    public boolean criarPlayList(String nome){
        Playlist play = new Playlist();
        play.setNome(nome);
        return play.inserir();
    }

    public boolean addFaixaPlaylist(ArrayList<Faixa> faixas, Playlist play) {
        boolean certo = true;

        for (Faixa f : faixas){
            boolean c = play.addFaixa(f);
            if (!c) certo = false;
        }
        return certo;
    }


    public int getQuantidadeGravadora(){
        Executar e = new Executar();
        return e.contarGravadoras();
    }

    public ArrayList<Album> getAlbunsPesquisa(String pesquisa){
        Executar e = new Executar();
        return e.selectAlbunsPesquisa(pesquisa);
    }

    public ArrayList<Gravadora> getGravadoras(){
        Executar e = new Executar();
        return e.selectGravadoras();
    }

    public boolean alterarAlbum(int id, String descrica, int grav, String preco, String data, String compra){
        Executar e = new Executar();
        return e.updateGravadora(id, descrica, grav, preco, data, compra);
    }

}

