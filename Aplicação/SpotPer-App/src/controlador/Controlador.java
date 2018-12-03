package controlador;

import modelo.Album;

import java.util.ArrayList;

public class Controlador {
    ArrayList<Album> albuns = new ArrayList<Album>();
    public Controlador() {
        //chama o slq aqui e vou criar uma lista de paranaues
        for (int i = 0; i < 10; i++) {
            Album a = new Album("Album da Hariamy " + i, "Besta");
            albuns.add(a);
        }

    }

    public ArrayList<Album> getAlbuns() { return albuns; }
}

