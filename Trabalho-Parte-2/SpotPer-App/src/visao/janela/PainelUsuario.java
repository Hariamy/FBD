package visao.janela;
import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;
import controlador.Controlador;
//import visao.janela.central.observer.Editar;
//import visao.janela.central.observer.Numericos;
//import visao.janela.central.observer.Tabelas;
import modelo.tabela.Album;
import modelo.tabela.Faixa;
import modelo.tabela.Playlist;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PainelUsuario extends JPanel{
    //MUSICAS A SEREM ADICIONADAS NA PLAYLSIT
    HashMap<JCheckBox, Faixa> adicionar = new HashMap<>();
    Playlist escolhida;
    ArrayList<Faixa> inserirFaixas = new ArrayList<>();

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

        JLabel tituloPlaylist = new JLabel("PLAYLIST", SwingConstants.CENTER);
        tituloPlaylist.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloPlaylist.setForeground(Cores.corBotaoAzulEscuro);

        Botao criar = new Botao("Criar");
        criar.setMargin(new Insets(0, 40, 0, 40));
        criar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        criar.addActionListener(new BotaoCriar(this));

        Botao buscar = new Botao("Inserir");
        buscar.setMargin(new Insets(0, 45,0 , 45));
        buscar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        buscar.addActionListener(new BotaoListar());
        
        painelOeste.add(new Label(""));
        painelOeste.add(tituloPlaylist);
        painelOeste.add(criar);
        painelOeste.add(buscar);


        this.add(painelOeste, BorderLayout.WEST);

        //-----------------\\ FIM - PAINEL OESTE - INSERIR E EDITAR PLAYLISTS //-----------------\\

        dimensaoCental = new Dimension(800, 0);

        painelCentro.setPreferredSize(dimensaoCental);
        boxCentral = Box.createHorizontalBox();

        JScrollPane rolagem = new JScrollPane(painelCentro);
        rolagem.setPreferredSize(new Dimension( 800,0));
        rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        boxCentral.add(rolagem);
        boxCentral.setBorder(bordaVazia);
        this.add(boxCentral, BorderLayout.CENTER);

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

            //Border bordaTexto = BorderFactory.createEmptyBorder(7, 11, 7, 5);
            //Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
            //texto = new JTextField("Digite a descrição do Álbum", 40);

            //texto.setCursor(cursor);
            //texto.addFocusListener(new TextoOculto(texto.getText(), texto));
            //texto.setBorder(bordaTexto);
            //texto.setFont(Fontes.ROBOTO_MENOR);


            JPanel painelTexto = new JPanel();
            painelTexto.setLayout(new FlowLayout());
            painelTexto.setBackground(Cores.corBranca);
            painelTexto.setBorder(bordaVazia);
            //painelTexto.add(texto, FlowLayout.TRAILING);
/*
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
*/
        }
    }

    public class BotaoCriar implements ActionListener {
        PainelUsuario painelUsuario;
        BotaoCriar(PainelUsuario painelUsuario){
            this.painelUsuario = painelUsuario;
        }
        public void actionPerformed(ActionEvent evento) {
            painelCentro.removeAll();
            dimensaoCental.setSize(800, 0);

            // ---------- INÍCIO - PAINEL DO TEXTO ----------- //
            JPanel painelTexto = new JPanel();
            painelTexto.setLayout(new FlowLayout());
            painelTexto.setBackground(Cores.corVerde);
            painelTexto.setBorder(bordaVazia);

            // ---------- FIM - PAINEL DO TEXTO ----------- //

            // ---------- INÍCIO - PAINEL DO DESCRIÇÃO ----------- //
            JPanel painelDescricao = new JPanel();
            painelDescricao.setLayout(new FlowLayout());
            painelDescricao.setBackground(Cores.corVerde);
            painelDescricao.setBorder(bordaVazia);

            // ---------- FIM - PAINEL DO DESCRIÇÃO ----------- //

            // ---------- INÍCIO - PAINEL DO BOTÃO ----------- //
            JPanel painelBotao = new JPanel();
            painelBotao.setLayout(new FlowLayout());
            painelBotao.setBackground(Cores.azulEscuro);
            painelBotao.setBorder(bordaVazia);

            // ---------- FIM - PAINEL DO BOTÃO ----------- //

            JLabel nome = new JLabel("NOME: ");
            nome.setFont(Fontes.ROBOTO_BOLD_MENOR);

            Border bordaTexto = BorderFactory.createEmptyBorder(7, 7, 7, 5);
            Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
            JTextField texto = new JTextField("Insira o nome da Playlist", 35);
            texto.setCursor(cursor);
            texto.addFocusListener(new TextoOculto(texto.getText(), texto));
            texto.setBorder(bordaTexto);
            texto.setFont(Fontes.ROBOTO_MENOR);

            JLabel descricao = new JLabel("DESCRIÇÃO: ");
            descricao.setFont(Fontes.ROBOTO_BOLD_MENOR);

            Border bordaDescricao = BorderFactory.createEmptyBorder(7, 11, 7, 5);
            JTextArea descricaoArea = new JTextArea("Insira a descrição da Playlist",  4, 32);
            descricaoArea.setCursor(cursor);
            descricaoArea.setLineWrap(true);
            descricaoArea.addFocusListener(new TextoOculto(descricaoArea.getText(), descricaoArea));
            descricaoArea.setBorder(bordaTexto);
            descricaoArea.setFont(Fontes.ROBOTO_MENOR);


            Botao criar = new Botao("CRIAR");
            criar.setMargin(new Insets(0, 320,0 , 320));
            criar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.corBranca, Cores.corVerde);
            criar.addActionListener(new BotaoCriarPlaylist(texto));


            painelTexto.add(nome);
            painelTexto.add(texto);
            painelDescricao.add(descricao);
            painelDescricao.add(descricaoArea);
            painelBotao.add(criar);


            adicionaPainelCentral(painelTexto, 60);
            //adicionaPainelCentral(painelDescricao, 60);
            adicionaPainelCentral(painelBotao, 60);

        }
    }

    // ----------------- // INÍCIO - AÇÃO AO CLICAR EM LISTAR TODAS AS PLAYLISTS // ----------------- //
    public class BotaoListar implements ActionListener {
       public void actionPerformed(ActionEvent evento) {
           painelCentro.removeAll();
           dimensaoCental.setSize(800, 0);

           JPanel t = new JPanel();
           t.setBackground(Cores.corBranca);

           JLabel titulo = new JLabel("              Selecione as faixas para adicionar na playlist              ");
           titulo.setForeground(Cores.corPreta);
           titulo.setSize(700, 0);
           titulo.setFont(Fontes.ROBOTO_BOLD_MEDIA);

           t.add(titulo);
           adicionaPainelCentral(t, 20);

           ArrayList<Album> listaAlbuns = getControlador().getAlbuns();

           for (Album a : listaAlbuns) {
               Dimension d = new Dimension(785, 55);
               JPanel p = new JPanel();
               p.setBorder(bordaVazia);
               p.setLayout(new BorderLayout());
               p.setPreferredSize(d);
               p.setBackground(Cores.azulEscuro);


               Botao b = new Botao("Album: " + a.getDescricao());
               b.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.corBranca, Cores.azulEsverdeado);
               b.addActionListener(new BotaoAlbum(b, p, a, d));

               p.add(b, BorderLayout.NORTH);
               adicionaPainelCentral(p, 80);
           }


           Botao inserir = new Botao("INSERIR");
           inserir.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MEDIA, Cores.corBotaoAzulEscuro, Cores.corVerde);
           inserir.addActionListener(new InserirMusicas());
           inserir.setMargin(new Insets(0, 340 ,0 , 340));

           ArrayList<Playlist> play = getControlador().getPlaylist();

           int quant = getControlador().getQuantidadePlaylist();
           String[] nomePlay = new String[quant];
           for (int i = 0; i < quant; i++){
               nomePlay[i] = play.get(i).getNome();

           }
           JPanel inferiror = new JPanel();
           inferiror.setLayout(new GridLayout(0, 2));
           inferiror.setSize(800, 10);
           Border bordaTexto = BorderFactory.createEmptyBorder(7, 11, 7, 5);
           inferiror.setBorder(bordaTexto);
           inferiror.setBackground(Cores.corBranca);


           JComboBox escolha = new JComboBox(nomePlay);
           escolha.setMaximumRowCount(4);
           escolha.addItemListener(new EscolhaPlaylist(escolha, play));
           escolha.setFont(Fontes.ROBOTO_MENOR);
           escolha.setMinimumSize(new Dimension(800, 0));
            escolha.setBackground(Cores.corBranca);

           JLabel insNome = new JLabel("         Selecione a playlist:         ");
           insNome.setFont(Fontes.ROBOTO_BOLD_MEDIA);

           inferiror.add(insNome);
           inferiror.add(escolha);

           JPanel pBotao = new JPanel();
           pBotao.add(inserir);
           pBotao.setBackground(Cores.azulEscuro);
           pBotao.setBorder(bordaVazia);


           adicionaPainelCentral(inferiror, 50);
           adicionaPainelCentral(pBotao, 50);
        }

    }

    public class InserirMusicas implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            boolean erro = false;
            Iterator myVeryOwnIterator = adicionar.keySet().iterator();
            String log = "Erro ao inserir: ";
            while(myVeryOwnIterator.hasNext()) {
                JCheckBox key = (JCheckBox) myVeryOwnIterator.next();
                if (key.isSelected()){
                    Faixa f = (Faixa)adicionar.get(key);
                    inserirFaixas.add(f);
                }
            }


            if (!getControlador().addFaixaPlaylist(inserirFaixas, escolhida)){
                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), log, "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);

            }
            else {

                URL erroIcone = ClassLoader.getSystemResource("imagens/ok.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Musicas inseridas com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }

        }
    }

    public class EscolhaPlaylist implements ItemListener{
        JComboBox<Playlist> escolha;
        ArrayList<Playlist> playlist;
        EscolhaPlaylist(JComboBox<Playlist> escolha, ArrayList<Playlist> playlists){
            this.escolha = escolha;
            this.playlist = playlists;
        }
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                for(Playlist p : playlist){
                    if (p.getNome().matches((String) escolha.getSelectedItem())){
                        escolhida = p;
                    }
                }
            }

        }
    }

    // ----------------- // INÍCIO - AÇÃO AO CLICAR EM LISTAR TODAS AS PLAYLISTS // ----------------- //

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
                    JCheckBox cb = new JCheckBox("Faixa Numero " + f.getNumero() + " - " + f.getDescricao());
                    cb.setSize(120, 10);
                    cb.setFont(Fontes.ROBOTO_MENOR);
                    cb.setBorderPaintedFlat(false);

                    cb.setBackground(Cores.corBranca);
                    cb.setForeground(Cores.corPreta);
                    cb.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    p.add(cb);
                    quantidadeAdicionada += 50;

                    adicionar.put(cb, f);
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

    public class BotaoCriarPlaylist implements ActionListener {
        JTextField nome;

        BotaoCriarPlaylist(JTextField nome) {
            this.nome = nome;

        }

        public void actionPerformed(ActionEvent evento) {

            if(getControlador().criarPlayList(nome.getText())) {

                URL erroIcone = ClassLoader.getSystemResource("imagens/ok.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Playlisti inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }
            else {
                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Erro ao inserir Playlist!", "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }

        }

    }


    public class TextoOculto implements FocusListener {
        String frase;
        JTextField texto;
        JTextArea textoA;

        boolean tipoTexto;

        TextoOculto(String frase, JTextField texto){
            this.texto = texto;
            this.frase = frase;
            tipoTexto = true;
        }

        TextoOculto(String frase, JTextArea texto){
            this.textoA = texto;
            this.frase = frase;
            tipoTexto = false;
        }
        public void focusGained ( FocusEvent e){
            if (tipoTexto) {
                if (texto.getText().matches(frase)) texto.setText("");
                else if (texto.getText().matches("")) texto.setText(frase);
            }
            else {
                if (textoA.getText().matches(frase)) textoA.setText("");
                else if (textoA.getText().matches("")) textoA.setText(frase);
            }

        }
        public void focusLost ( FocusEvent  e){
            if (tipoTexto) {
                if (texto.getText().matches(frase)) texto.setText("");
                else if (texto.getText().matches("")) texto.setText(frase);
            }
            else {
                if (textoA.getText().matches(frase)) textoA.setText("");
                else if (textoA.getText().matches("")) textoA.setText(frase);
            }

        }
    }
}
