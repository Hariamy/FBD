package modelo;

import java.util.ArrayList;

public class Album {
    private String nome;
    private String descricao;
    private ArrayList<Faixa> faixas;
    private boolean exibicao = false;

    public Album(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.faixas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Faixa f = new Faixa("Faixa de musica da Hariamy " + i);
            faixas.add(f);
        }
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public void setFaixas(ArrayList<Faixa> faixas) { this.faixas = faixas; }
    public void setExibicao() {
        if (exibicao) exibicao = false;
        else exibicao = true;
    }

    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public ArrayList<Faixa> getFaixas() { return faixas; }
    public boolean getExibicao() { return exibicao; }
}
