package visao.janela.usuario;

import controlador.Controlador;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Buscar {
    private PainelUsuario painelUsuario;
    private String nome;
    private JTable valor;

    private JPanel painel;
    private JPanel centro;

    Buscar(PainelUsuario painelUsuario){
        this.painelUsuario = painelUsuario;
        configuracoesPadrao();
    }

    private void configuracoesPadrao() {
        painel = new JPanel();
        painel.setPreferredSize(new Dimension(780, 680));
        painel.setLayout(new BorderLayout());

        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(Cores.corVerde);

        try {
            URL fecharIcone = ClassLoader.getSystemResource("fechar.png");
            Icon fechar = new ImageIcon(fecharIcone);
            Botao botaoFechar = new Botao(fechar);
            botaoFechar.setContentAreaFilled(false);
            botaoFechar.addActionListener(new Fechar(painel));
            cabecalho.add(botaoFechar, BorderLayout.EAST);
            painel.add(cabecalho, BorderLayout.NORTH);

        } catch (Exception e){
            //apagar
            Icon fechar = new ImageIcon(getClass().getResource("/imagens\\fechar.png"));
            Botao botaoFechar = new Botao(fechar);
            botaoFechar.setContentAreaFilled(false);
            botaoFechar.addActionListener(new Fechar(painel));
            cabecalho.add(botaoFechar, BorderLayout.EAST);
            painel.add(cabecalho, BorderLayout.NORTH);
        }


        painel.add(centro, BorderLayout.CENTER);
        painelUsuario.adicionaPainelCentral(painel, 690);

    }

    public class Fechar implements ActionListener {
        JPanel painel;
        Fechar(JPanel painel){
            this.painel = painel;
        }
        public void actionPerformed(ActionEvent evento) {
            painel.removeAll();
            painelUsuario.eliminaPainelCentral(this.painel, 690);
        }
    }
}