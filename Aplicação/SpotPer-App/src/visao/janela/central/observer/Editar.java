package visao.janela.central.observer;

import visao.janela.PainelInformacoes;
import visao.janela.central.janela.JanelaEditar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Editar implements ActionListener {
    private PainelInformacoes informacoes;
    private String nome;

    public Editar(PainelInformacoes informacoes, String nome){
        this.informacoes = informacoes;
        this.nome = nome;

    }

    public void actionPerformed(ActionEvent evento) {
        JanelaEditar janela = new JanelaEditar(informacoes, nome);
    }
}