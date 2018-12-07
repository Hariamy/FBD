package visao.janela;

import controlador.Controlador;
import modelo.tabela.Album;
import modelo.tabela.Faixa;
import modelo.tabela.Gravadora;
import modelo.tabela.Playlist;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class PainelAdministrador extends JPanel{
    //MUSICAS A SEREM ADICIONADAS NA PLAYLSIT
    HashMap<JCheckBox, Faixa> adicionar = new HashMap<>();
    Playlist escolhida;
    Gravadora escolhidaGrav;
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

    public PainelAdministrador(Janela janela){
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

        JLabel tituloPlaylist = new JLabel("ALBUNS", SwingConstants.CENTER);
        tituloPlaylist.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloPlaylist.setForeground(Cores.corBotaoAzulEscuro);

        Botao criar = new Botao("Editar");
        criar.setMargin(new Insets(0, 40, 0, 40));
        criar.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        criar.addActionListener(new BotaoOpcoes(this));

        painelOeste.add(new JLabel(""));
        painelOeste.add(tituloPlaylist);
        painelOeste.add(criar);

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
        PainelAdministrador painelUsuario;
        BotaoOpcoes(PainelAdministrador painelUsuario){
            this.painelUsuario = painelUsuario;
        }
        public void actionPerformed(ActionEvent evento) {
            painelCentro.removeAll();
            janela.repaint();

            dimensaoCental.setSize(800, 0);

            JPanel t = new JPanel();
            t.setBackground(Cores.azulBonito);

            JLabel titulo = new JLabel("          Busque a descrição do album que deseja editar          ");
            titulo.setForeground(Cores.corBranca);
            titulo.setSize(700, 0);
            titulo.setFont(Fontes.ROBOTO_MEDIA);

            t.add(titulo);
            adicionaPainelCentral(t, 20);


            Border bordaTexto = BorderFactory.createEmptyBorder(7, 11, 7, 5);
            Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
            texto = new JTextField("Digite a descrição do Álbum", 38);

            texto.setCursor(cursor);
            texto.addFocusListener(new TextoOculto(texto.getText(), texto));
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


    // ----------------- // INÍCIO - AÇÃO AO CLICAR EM LISTAR TODAS AS PLAYLISTS // ----------------- //
    public class BotaoBuscar implements ActionListener {
       public void actionPerformed(ActionEvent evento) {

           ArrayList<Album> listaAlbuns = getControlador().getAlbunsPesquisa(texto.getText());
            if(listaAlbuns.size() != 0){

                for (Album a : listaAlbuns) {
                    Dimension d = new Dimension(775, 55);
                    JPanel p = new JPanel();
                    p.setBorder(bordaVazia);
                    p.setLayout(new BorderLayout());
                    p.setPreferredSize(d);
                    p.setBackground(Cores.azulEscuro);


                    Botao b = new Botao("Album: " + a.getDescricao());
                    b.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.corBranca, Cores.azulEsverdeado);

                    b.addActionListener(new BotaoAlbum(b, p, a, d));

                    p.add(b, BorderLayout.NORTH);
                    adicionaPainelCentral(p, 50);
                }


                Botao inserir = new Botao("INSERIR");
                inserir.configurarFonteCorFundo(Fontes.ROBOTO_MENOR, Cores.corBotaoAzulEscuro, Cores.rosaClaro);
                inserir.addActionListener(new InserirMusicas());

                ArrayList<Playlist> play = getControlador().getPlaylist();

                int quant = getControlador().getQuantidadePlaylist();
                String[] nomePlay = new String[quant];
                for (int i = 0; i < quant; i++){

                    nomePlay[i] = play.get(i).getNome();

                }
                JPanel inferiror = new JPanel();
                inferiror.setLayout(new GridLayout(0, 3));
                inferiror.setSize(800, 10);


                JComboBox escolha = new JComboBox(nomePlay);
                escolha.setMaximumRowCount(4);
                escolha.addItemListener(new EscolhaPlaylist(escolha, play));
                escolha.setFont(Fontes.ROBOTO_MEDIA);
                escolha.setMinimumSize(new Dimension(800, 0));

                JLabel insNome = new JLabel("Escolha a playlist   ");
                insNome.setFont(Fontes.ROBOTO_BOLD_MEDIA);

                inferiror.add(insNome);
                inferiror.add(escolha);
                inferiror.add(inserir);
            }
            else {
                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Nenhum álbum encontrado", "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }
        }

    }

    public class InserirMusicas implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Iterator myVeryOwnIterator = adicionar.keySet().iterator();
            while(myVeryOwnIterator.hasNext()) {
                JCheckBox key = (JCheckBox) myVeryOwnIterator.next();
                if (key.isSelected()){
                    Faixa f = (Faixa)adicionar.get(key);
                    inserirFaixas.add(f);
                }
            }

            getControlador().addFaixaPlaylist(inserirFaixas, escolhida);

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
            painelCentro.removeAll();
            janela.repaint();
            dimensaoCental.setSize(800, 0);

            JPanel t = new JPanel();
            t.setBackground(Cores.corBotaoAzulEscuro);

            JLabel titulo = new JLabel("                  Edite os valores e atualize no banco                  ");
            titulo.setForeground(Cores.corBranca);
            titulo.setSize(700, 0);
            titulo.setFont(Fontes.ROBOTO_MEDIA);

            t.add(titulo);
            adicionaPainelCentral(t, 20);

            // ---------- INÍCIO - PAINEL DO DESCRIÇÃO ----------- //
            JPanel painelDescricao = new JPanel();
            painelDescricao.setLayout(new FlowLayout());
            painelDescricao.setBackground(Cores.corBranca);
            painelDescricao.setBorder(bordaVazia);

            // ---------- FIM - PAINEL DO DESCRIÇÃO ----------- //
            JLabel descricao = new JLabel("DESCRIÇÃO: ");
            descricao.setFont(Fontes.ROBOTO_BOLD_MENOR);

            Border bordaTexto = BorderFactory.createEmptyBorder(7, 7, 7, 5);
            Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
            Border bordaDescricao = BorderFactory.createEmptyBorder(7, 11, 7, 5);
            JTextArea descricaoArea = new JTextArea(album.getDescricao(),  4, 32);
            descricaoArea.setCursor(cursor);
            descricaoArea.setLineWrap(true);
            descricaoArea.addFocusListener(new TextoOculto(descricaoArea.getText(), descricaoArea));
            descricaoArea.setBorder(bordaTexto);
            descricaoArea.setFont(Fontes.ROBOTO_MENOR);

            painelDescricao.add(descricao);
            painelDescricao.add(descricaoArea);

            //EXIBE AS OPÇOES DE GRAVADORA
            JLabel textGrav = new JLabel("   ESCOLHA GRAVADORA:                       ");
            textGrav.setFont(Fontes.ROBOTO_BOLD_MENOR);

            ArrayList<Gravadora> grav = getControlador().getGravadoras();

            int quant = getControlador().getQuantidadeGravadora();

            String[] nomePlay = new String[quant];

            for (int i = 0; i < quant; i++){
                nomePlay[i] = grav.get(i).getNome();
            }

            JPanel pGrav = new JPanel();
            pGrav.setBackground(Cores.corBranca);
            pGrav.setLayout(new GridLayout(0, 2));
            pGrav.setSize(800, 10);
            pGrav.setBorder(bordaTexto);

            JComboBox escolhaGrav = new JComboBox(nomePlay);
            escolhaGrav.setMaximumRowCount(4);
            escolhaGrav.addItemListener(new EscolhaGravadora(escolhaGrav, grav));
            escolhaGrav.setFont(Fontes.ROBOTO_MENOR);
            escolhaGrav.setBackground(Cores.corBranca);

            pGrav.add(textGrav);
            pGrav.add(escolhaGrav);


            JPanel tipo = new JPanel();
            tipo.setBackground(Cores.corBranca);
            tipo.setLayout(new GridLayout(0, 3));
            tipo.setBorder(bordaTexto);

            CheckboxGroup cbg = new CheckboxGroup();

            JLabel texTipo = new JLabel("  TIPO DE ARQUIVO:          ");
            texTipo.setFont(Fontes.ROBOTO_BOLD_MENOR);

            Checkbox cb1 = new Checkbox(" Fisico ", cbg, true);
            cb1.setFont(Fontes.ROBOTO_MENOR);
            cb1.setCursor(new Cursor(Cursor.HAND_CURSOR));

            Checkbox cb2 = new Checkbox(" Download ", cbg, false);
            cb2.setFont(Fontes.ROBOTO_MENOR);
            cb2.setCursor(new Cursor(Cursor.HAND_CURSOR));

            tipo.add(texTipo);
            tipo.add(cb1);
            tipo.add(cb2);


            JLabel nome = new JLabel("   DATA:   ");
            nome.setFont(Fontes.ROBOTO_BOLD_MENOR);

            JTextField texto = new JTextField("Insira a data de Compra ", 34);
            texto.setCursor(cursor);
            texto.addFocusListener(new TextoOculto(texto.getText(), texto));
            texto.setBorder(bordaTexto);
            texto.setFont(Fontes.ROBOTO_MENOR);

            JPanel painelTexto = new JPanel();
            painelTexto.setLayout(new FlowLayout());
            painelTexto.setBackground(Cores.corBranca);
            painelTexto.setBorder(bordaVazia);

            painelTexto.add(nome);
            painelTexto.add(texto);


            JLabel precoN = new JLabel("  PREÇO: ");
            precoN.setFont(Fontes.ROBOTO_BOLD_MENOR);

            JTextField preco = new JTextField("Insira o preço de compra", 34);
            preco.setCursor(cursor);
            preco.addFocusListener(new TextoOculto(preco.getText(), preco));
            preco.setBorder(bordaTexto);
            preco.setFont(Fontes.ROBOTO_MENOR);

            JPanel painelPreco = new JPanel();
            painelPreco.setLayout(new FlowLayout());
            painelPreco.setBackground(Cores.corBranca);
            painelPreco.setBorder(bordaVazia);

            painelPreco.add(precoN);
            painelPreco.add(preco);


            JPanel painelInserir = new JPanel();
            painelInserir.setLayout(new FlowLayout());
            painelInserir.setBackground(Cores.azulEscuro);
            painelInserir.setBorder(bordaVazia);

            Botao inserir = new Botao("   Alterar Dados  ");
            inserir.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MEDIA, Cores.corBotaoAzulEscuro, Cores.corVerde);
            inserir.addActionListener(new BotaoAlterarAlbum(album, cb1, cb2, texto, descricaoArea, preco, escolhaGrav, grav));
            inserir.setMargin(new Insets(0, 253,0 , 253));
            painelInserir.add(inserir);

            adicionaPainelCentral(painelDescricao, 60);
            adicionaPainelCentral(painelTexto, 60);
            adicionaPainelCentral(painelPreco, 60);
            adicionaPainelCentral(tipo, 60);
            adicionaPainelCentral(pGrav, 60);
            adicionaPainelCentral(painelInserir, 60);

        }

    }

    public class BotaoAlterarAlbum implements ActionListener {
        Checkbox fisico;
        Checkbox download;
        JTextField data;
        JTextArea descricao;
        JTextField preco;
        ArrayList<Gravadora> listGrav;
        JComboBox gravadora;
        Album album;

        BotaoAlterarAlbum(Album album, Checkbox fisico, Checkbox download, JTextField data, JTextArea descricao, JTextField preco, JComboBox gravadora, ArrayList<Gravadora> listGrav) {
            this.fisico = fisico;
            this.download = download;
            this.data = data;
            this.descricao = descricao;
            this.preco = preco;
            this.listGrav = listGrav;
            this.gravadora = gravadora;
            this.album = album;
        }

        public void actionPerformed(ActionEvent evento) {
            String escolha;
            if (fisico.getState()) escolha = "Fisico";
            else escolha = "Download";

            if(getControlador().alterarAlbum(album.getCodigo(),  descricao.getText(), escolhidaGrav.getCodigo(), preco.getText(), data.getText(), escolha)){

                URL erroIcone = ClassLoader.getSystemResource("imagens/ok.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Album alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }
            else {
                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), "Erro ao alterar Album!", "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);
            }

        }

    }

    public class EscolhaGravadora implements ItemListener{
        JComboBox<Playlist> escolha;
        ArrayList<Gravadora> playlist;
        EscolhaGravadora(JComboBox<Playlist> escolha, ArrayList<Gravadora> playlists){
            this.escolha = escolha;
            this.playlist = playlists;
        }
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                for(Gravadora p : playlist){
                    if (p.getNome().matches((String) escolha.getSelectedItem())){
                        escolhidaGrav = p;
                    }
                }
            }

        }
    }

    public class BotaoCriarPlaylist implements ActionListener {
        JTextField nome;
        JTextArea descricao;

        BotaoCriarPlaylist(JTextField nome, JTextArea descricao) {
            this.nome = nome;
            this.descricao = descricao;
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
