package visao.janela.central.observer;

import visao.janela.PainelInformacoes;
import visao.janela.central.janela.JanelaNumericos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Numericos implements ActionListener{
    private PainelInformacoes informacoes;
    private String nome;

    public Numericos(PainelInformacoes informacoes, String nome){
        this.informacoes = informacoes;
        this.nome = nome;

    }

    public void actionPerformed(ActionEvent evento) {
        JanelaNumericos janela = new JanelaNumericos(informacoes, nome);

    }

}