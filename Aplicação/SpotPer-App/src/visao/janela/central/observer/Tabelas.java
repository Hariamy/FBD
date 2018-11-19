package visao.janela.central.observer;

import visao.janela.PainelInformacoes;
import visao.janela.central.janela.JanelaTabelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Tabelas implements ActionListener{
    private PainelInformacoes informacoes;
    private String nome;


    public Tabelas(PainelInformacoes informacoes, String nome){
        this.informacoes = informacoes;
        this.nome = nome;
    }

    public void actionPerformed(ActionEvent evento){
        JanelaTabelas janela = new JanelaTabelas(informacoes, nome);
    }
}
