package visao.janela.usuario;
import controlador.Controlador;
//import visao.janela.central.observer.Editar;
//import visao.janela.central.observer.Numericos;
//import visao.janela.central.observer.Tabelas;
import modelo.Album;
import modelo.Faixa;
import visao.janela.Janela;
import visao.janela.PainelInicial;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.ArrayList;

public class PainelUsuario extends JPanel{
    boolean focoInicial = true;
    JTextField texto;
    Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);


    private Janela janela;
    private Dimension dimensaoCental;
    private Box boxCentral;
    private JPanel painelSuperior = new JPanel();
    private JPanel painelInferior = new JPanel();
    private JPanel painelLeste = new JPanel();
    private JPanel painelOeste = new JPanel();
    private JPanel painelCentro = new JPanel();

    public PainelUsuario(Janela janela){
        super();
        this.janela = janela;
        this.configuracoes();
        this.setVisible(true);
    }

    public Controlador getControlador(){
        return janela.getControlador();
    }

    private void configuracoes(){

        //-----------------\\ LAYOUT DA PÁGINA INICIAL //-----------------\\
        

        painelSuperior.setBorder(bordaVazia);
        painelInferior.setBorder(bordaVazia);
        painelLeste.setBorder(bordaVazia);
        painelOeste.setBorder(bordaVazia);
        painelCentro.setBorder(bordaVazia);

        this.setLayout(new BorderLayout());
        painelSuperior.setLayout(new BorderLayout());
        painelInferior.setLayout(new BorderLayout());
        painelLeste.setLayout(new GridLayout(15, 1));
        painelOeste.setLayout(new GridLayout(15, 1));
        painelCentro.setLayout(new FlowLayout());

        this.setBackground(Cores.rosaClaro);
        painelSuperior.setBackground(Cores.rosaClaro);
        painelLeste.setBackground(Cores.rosaClaro2);
        painelCentro.setBackground(Cores.azulEscuro);
        painelOeste.setBackground(Cores.rosaClaro2);

        //-----------------\\ INÍCIO - CARREGA IMAGEM BOTAO VOLTAR //-----------------\\
        try {
            URL voltarIcone = ClassLoader.getSystemResource("imagens/volta.png");
            Icon botaoVoltar = new ImageIcon(voltarIcone);

            Botao voltar = new Botao(botaoVoltar);
            voltar.setContentAreaFilled(false);
            voltar.addActionListener(new BotaoVoltar());

            painelSuperior.add(voltar, BorderLayout.WEST);
            this.add(painelSuperior, BorderLayout.NORTH);

        } catch (Exception e){
            Icon botaoVoltar = new ImageIcon(getClass().getResource("/imagens\\volta.png"));

            Botao voltar = new Botao(botaoVoltar);
            voltar.setContentAreaFilled(false);
            voltar.addActionListener(new BotaoVoltar());

            painelSuperior.add(voltar, BorderLayout.WEST);
            this.add(painelSuperior, BorderLayout.NORTH);
        }
        //-----------------\\ FIM - CARREGA IMAGEM BOTAO VOLTAR //-----------------\\


        //-----------------\\ INÍCIO - PAINEL OESTE - INSERIR E EDITAR PLAYLISTS //-----------------\\

        Botao criar = new Botao("Criar");
        criar.setMargin(new Insets(0, 40, 0, 40));
        criar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        criar.addActionListener(new BotaoOpcoes(this));

        Botao inserir = new Botao("Inserir");
        inserir.setMargin(new Insets(0, 40,0 , 40));
        inserir.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        inserir.addActionListener(new BotaoOpcoes(this));

        JLabel tituloPlaylist = new JLabel("PLAYLIST", SwingConstants.CENTER);
        tituloPlaylist.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloPlaylist.setForeground(Cores.corBotaoAzulEscuro);
        
        
        painelOeste.add(tituloPlaylist);
        painelOeste.add(criar);
        painelOeste.add(inserir);

        this.add(painelOeste, BorderLayout.WEST);

        //-----------------\\ INÍCIO - PAINEL OESTE - INSERIR E EDITAR PLAYLISTS //-----------------\\


        JLabel tituloAlbuns = new JLabel("ÁLBUNS", SwingConstants.CENTER);
        tituloAlbuns.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloAlbuns.setForeground(Cores.corBotaoAzulEscuro);

        Botao buscar = new Botao("Buscar");
        buscar.setMargin(new Insets(0, 45,0 , 45));
        buscar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        buscar.addActionListener(new BotaoOpcoes(this));

        painelLeste.add(tituloAlbuns);
        painelLeste.add(buscar);

        this.add(painelLeste, BorderLayout.EAST);

        dimensaoCental = new Dimension(800, 0);

        painelCentro.setPreferredSize(dimensaoCental);
        boxCentral = Box.createHorizontalBox();

        JScrollPane rolagem = new JScrollPane(painelCentro);
        rolagem.setPreferredSize(new Dimension( 800,0));
        rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        boxCentral.add(rolagem);
        boxCentral.setBorder(bordaVazia);
        this.add(boxCentral, BorderLayout.CENTER);

        // this.add(painelInferior, BorderLayout.SOUTH);

    }

    public void adicionaPainelCentral(JPanel painel, int quanto){
        dimensaoCental.setSize(800, dimensaoCental.getHeight()+quanto);
        painelCentro.add(painel);
        painelCentro.revalidate();
        boxCentral.revalidate();
    }

    public void eliminaPainelCentral(JPanel painel, int quanto){
        painelCentro.remove(painel);
        painel = null;
        dimensaoCental.setSize(800, dimensaoCental.getHeight()-quanto);
        painelCentro.revalidate();
        boxCentral.revalidate();
        janela.repaint();
    }

    // É ISSO MESMO PRODUÇÃO - CLASSES DENTRO DE CLASSES
    public class BotaoVoltar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            janela.getContentPane().removeAll();
            janela.conteudoJanela(new PainelInicial(janela));
            janela.revalidate();
            janela.repaint();
        }
    }

    public class BotaoOpcoes implements ActionListener {
        PainelUsuario painelUsuario;
        BotaoOpcoes(PainelUsuario painelUsuario){
            this.painelUsuario = painelUsuario;
        }
        public void actionPerformed(ActionEvent evento) {
            painelCentro.removeAll();
            dimensaoCental.setSize(800, 0);


            //Buscar pesquisa = new Buscar(painelUsuario);

            Border bordaTexto = BorderFactory.createEmptyBorder(7, 11, 7, 5);
            Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
            texto = new JTextField("Digite a descrição do Álbum", 43);

            texto.setCursor(cursor);
            texto.addFocusListener(new TextoOculto());
            texto.setBorder(bordaTexto);
            texto.setFont(Fontes.ROBOTO_MENOR);


            JPanel painelTexto = new JPanel();
            painelTexto.add(texto, BorderLayout.CENTER);
            painelTexto.setBackground(Cores.corBranca);
            painelTexto.setBorder(bordaVazia);

            //-----------------\\ INÍCIO - CARREGA IMAGEM BOTAO BUSCAR //-----------------\\
            try {
                URL voltarIcone = ClassLoader.getSystemResource("imagens/buscar.png");
                Icon botaoVoltar = new ImageIcon(voltarIcone);

                Botao buscar = new Botao(botaoVoltar);
                buscar.setContentAreaFilled(false);
                buscar.addActionListener(new BotaoBuscar());

                painelTexto.add(buscar, BorderLayout.WEST);

            } catch (Exception e){
                Icon botaoVoltar = new ImageIcon(getClass().getResource("/imagens\\buscar.png"));

                Botao buscar = new Botao(botaoVoltar);
                buscar.setContentAreaFilled(false);
                buscar.addActionListener(new BotaoBuscar());

                painelTexto.add(buscar, BorderLayout.WEST);
            }
            //-----------------\\ FIM - CARREGA IMAGEM BOTAO BUSCAR //-----------------\\

            adicionaPainelCentral(painelTexto, 60);

        }
    }

    public class BotaoBuscar implements ActionListener {
       public void actionPerformed(ActionEvent evento) {
           ///Vai realizar a gusca no banco de dados utilizand o controlador;
           ArrayList<Album> listaAlbuns = getControlador().getAlbuns();

           for (Album a : listaAlbuns) {
               Dimension d = new Dimension(785, 60);
               JPanel p = new JPanel();
               p.setBorder(bordaVazia);
               p.setLayout(new BorderLayout());
               p.setPreferredSize(d);
               p.setBackground(Cores.azulEsverdeado);


               Botao b = new Botao(a.getNome());
               b.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.corBranca, Cores.azulEsverdeado);
               b.addActionListener(new BotaoAlbum(b, p, a, d));

               p.add(b, BorderLayout.NORTH);
               adicionaPainelCentral(p, 100);
           }
        }

    }

    public class BotaoAlbum implements ActionListener {
        JPanel painelDoBotao;
        Album album;
        Dimension dimensao;
        Botao botao;
        BotaoAlbum(Botao botao, JPanel painelDoBotao, Album album, Dimension dimensao) {
            this.painelDoBotao = painelDoBotao;
            this.album = album;
            this.dimensao = dimensao;
            this.botao = botao;
        }
        public void actionPerformed(ActionEvent evento) {
            ArrayList<Faixa> faixas = album.getFaixas();
            int quantidadeAdicionada = 0;

            if (!album.getExibicao()){
                JPanel p = new JPanel();
                p.setBorder(bordaVazia);
                p.setLayout(new FlowLayout());
                p.setBackground(Cores.corBranca);

                for (Faixa f : faixas) {
                    JLabel l = new JLabel(f.getNome());
                    l.setSize(120, 10);
                    l.setFont(Fontes.ROBOTO_MEDIA);

                    p.add(l);
                    quantidadeAdicionada += 50;
                }
                painelDoBotao.add(p, BorderLayout.CENTER);
                painelDoBotao.revalidate();
                dimensao.setSize(785, dimensao.getHeight() + quantidadeAdicionada);
                dimensaoCental.setSize(800, dimensaoCental.getHeight()+quantidadeAdicionada);
            }
            else {
                painelDoBotao.removeAll();
                painelDoBotao.add(botao, BorderLayout.NORTH);
                painelDoBotao.revalidate();
                dimensaoCental.setSize(800, dimensaoCental.getHeight() - dimensao.getHeight() + 70);
                dimensao.setSize(785, 60);
            }
            album.setExibicao();
            painelCentro.revalidate();
            boxCentral.revalidate();
            janela.repaint();

        }

    }
    public class TextoOculto implements FocusListener {
        public void focusGained ( FocusEvent e){
            if (focoInicial) {
                focoInicial = false;
                // buscar.requestFocus();

            } else if (texto.getText().matches("Digite a descrição do Álbum")) texto.setText("");

        }
        public void focusLost ( FocusEvent  e){
            if (texto.getText().matches("")) texto.setText("Digite a descrição do Álbum");

        }
    }
}
