package visao.janela;

import controlador.Controlador;
import visao.layout.Cores;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Janela extends JFrame{
    private Controlador controlador;

    public Janela(){
        super("SpotPer - Suas músicas onde e quando quiser!");
        this.controlador = new Controlador();
        configuracoesPadrao();
    }

    void setControlador(Controlador controlador){
        this.controlador = controlador;
    }

    public Controlador getControlador(){
        return this.controlador;
    }

    private void configuracoesPadrao(){
        //     CONFIGURAÇÕES PADRÃO      //
        //SET BORDER LAYOUT
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        //TAMANHO DA TELA
        this.setSize(900, 700);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Cores.rosaClaro2);

        //BOTÃO FECHAR FUNCIONANDO
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            //ICONE BONITINHO
            URL icone = ClassLoader.getSystemResource("imagens/icone.png");
            Image imagemIcone = Toolkit.getDefaultToolkit().getImage(icone);
            this.setIconImage(imagemIcone);

        } catch (Exception e) {
            //apagar
            URL url = ClassLoader.getSystemResource("imagens\\icone.png");
            Image imagemIcone = Toolkit.getDefaultToolkit().getImage(url);
            this.setIconImage(imagemIcone);
            System.out.println("Erro ao carregar o ícone\n"+e);
        }

        //   FIM DAS CONFIGS PADRAO   //

    }

    public void conteudoJanela(JPanel painel){
        // FAZ A ROLAGEM
        Box box = Box.createHorizontalBox();
        box.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JScrollPane rolagem = new JScrollPane(painel);
        rolagem.setComponentZOrder(rolagem.getVerticalScrollBar(), 0);
        rolagem.setComponentZOrder(rolagem.getViewport(), 1);
        rolagem.getVerticalScrollBar().setOpaque(false);
        rolagem.setViewportBorder(null);
        box.add(rolagem);

        this.add(box);
    }

    public void exibirJanela(){
        this.setVisible(true);
    }



}
