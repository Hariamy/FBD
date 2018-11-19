package visao.janela;
import controlador.Controlador;
import visao.janela.central.observer.Editar;
import visao.janela.central.observer.Numericos;
import visao.janela.central.observer.Tabelas;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class PainelInformacoes extends JPanel{

    public Janela janela;
    private Dimension dimensaoCental;
    private Box boxCentral;
    private JPanel painelSuperior = new JPanel();
    private JPanel painelInferir = new JPanel();
    private JPanel painelLeste = new JPanel();
    private JPanel painelOeste = new JPanel();
    private JPanel painelCentro = new JPanel();

    PainelInformacoes(Janela janela){
        super();
        this.janela = janela;
        this.configuracoes();
        this.setVisible(true);
    }

    public Controlador getControlador(){
        return janela.getControlador();
    }

    private void configuracoes(){

        // super.configuracoesPadrao();
        // SEGUNDA JANELAAAAA
        Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        painelSuperior.setLayout(new BorderLayout());
        painelSuperior.setBorder(bordaVazia);

        painelInferir.setLayout(new BorderLayout());
        painelInferir.setBorder(bordaVazia);

        painelLeste.setLayout(new GridLayout(15, 1));
        painelLeste.setBorder(bordaVazia);

        painelOeste.setLayout(new GridLayout(15, 1));
        painelOeste.setBorder(bordaVazia);

        painelCentro.setLayout(new FlowLayout());
        painelCentro.setBorder(bordaVazia);

        this.setLayout(new BorderLayout());
        this.setBackground(Cores.rosaClaro);


        //   BARRA DE FERRAMENTAS - BOTÃO VOLTAR  //
        try {
            URL voltarIcone = ClassLoader.getSystemResource("imagens/volta.png");
            Icon botaoVoltar = new ImageIcon(voltarIcone);

            Botao voltar = new Botao(botaoVoltar);
            voltar.setContentAreaFilled(false);
            voltar.addActionListener(new BotaoVoltar());


            painelSuperior.add(voltar, BorderLayout.WEST);
            painelSuperior.setBackground(Cores.rosaClaro);

            this.add(painelSuperior, BorderLayout.NORTH);
        } catch (Exception e){
            //apagar
            Icon botaoVoltar = new ImageIcon(getClass().getResource("/imagens\\volta.png"));

            Botao voltar = new Botao(botaoVoltar);
            voltar.setContentAreaFilled(false);
            voltar.addActionListener(new BotaoVoltar());


            painelSuperior.add(voltar, BorderLayout.WEST);
            painelSuperior.setBackground(Cores.rosaClaro);

            this.add(painelSuperior, BorderLayout.NORTH);
        }


        //   BARRA DOS BOTÕES - //

        Botao media = new Botao("Média");
        media.setMargin(new Insets(0, 40, 0, 40));
        media.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        media.addActionListener(new Numericos(this, "media"));

        Botao moda = new Botao("Moda");
        moda.setMargin(new Insets(0, 40,0 , 40));
        moda.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        moda.addActionListener(new Numericos(this, "moda"));

        Botao variancia = new Botao("Variância");
        variancia.setMargin(new Insets(0, 40,0 , 40));
        variancia.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        variancia.addActionListener(new Numericos(this, "variancia"));

        Botao desvio = new Botao("Desvio Padrão");
        desvio.setMargin(new Insets(0, 40,0 , 40));
        desvio.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        desvio.addActionListener(new Numericos(this, "desvio"));


        Botao mediana = new Botao("Mediana");
        mediana.setMargin(new Insets(0, 40,0 , 40));
        mediana.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        mediana.addActionListener(new Numericos(this, "mediana"));


        Botao minimo = new Botao("Mínimo");
        minimo.setMargin(new Insets(0, 40,0 , 40));
        minimo.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        minimo.addActionListener(new Numericos(this, "minimo"));


        Botao maximo = new Botao("Máximo");
        maximo.setMargin(new Insets(0, 40,0 , 40));
        maximo.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        maximo.addActionListener(new Numericos(this, "maximo"));


        Botao skewness = new Botao("Skewness");
        skewness.setMargin(new Insets(0, 40,0 , 40));
        skewness.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        skewness.addActionListener(new Numericos(this, "skewness"));


        Botao kurtoses = new Botao("Kurtosis");
        kurtoses.setMargin(new Insets(0, 40,0 , 40));
        kurtoses.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        kurtoses.addActionListener(new Numericos(this, "kurtosis"));

        Botao editar = new Botao("EDITAR");
        editar.setMargin(new Insets(0, 40,0 , 40));
        editar.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MEDIA, Cores.corVermelha, Cores.rosaClaro2);
        editar.addActionListener(new Editar(this, "EDITAR"));


        JLabel tituloNumerico1 = new JLabel("VALORES", SwingConstants.CENTER);
        JLabel tituloNumerico2 = new JLabel("NUMÉRICOS", SwingConstants.CENTER);
        tituloNumerico1.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloNumerico1.setForeground(Cores.corBotaoAzulEscuro);
        tituloNumerico2.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloNumerico2.setForeground(Cores.corBotaoAzulEscuro);
        painelOeste.add(tituloNumerico1);
        painelOeste.add(tituloNumerico2);
        painelOeste.add(media);
        painelOeste.add(moda);
        painelOeste.add(mediana);
        painelOeste.add(desvio);
        painelOeste.add(variancia);
        painelOeste.add(minimo);
        painelOeste.add(maximo);
        painelOeste.add(skewness);
        painelOeste.add(kurtoses);
        painelOeste.add(new JLabel(""));
        painelOeste.add(new JLabel(""));
        painelOeste.add(editar);
        painelOeste.setBackground(Cores.rosaClaro2);

        this.add(painelOeste, BorderLayout.WEST);

        //   BARRA DOS GRÁFICOS - //
        ////////////////////////////////////  GRÁFICOS

        JLabel tituloGrafico = new JLabel("GRÁFICOS", SwingConstants.CENTER);
        tituloGrafico.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloGrafico.setForeground(Cores.corBotaoAzulEscuro);

        Botao histograma = new Botao("Histograma");
        histograma.setMargin(new Insets(0, 45,0 , 45));
        histograma.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        histograma.addActionListener(new Tabelas(this, "histograma"));

        Botao frequenciasG = new Botao("Frequências");
        frequenciasG.setMargin(new Insets(0, 45,0 , 45));
        frequenciasG.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        frequenciasG.addActionListener(new Tabelas(this, "frequencias"));

        Botao boxplot = new Botao("Box Plot");
        boxplot.setMargin(new Insets(0, 45,0 , 45));
        boxplot.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        boxplot.addActionListener(new Tabelas(this, "boxplot"));

        Botao scatterplot = new Botao("Scatter Plot");
        scatterplot.setMargin(new Insets(0, 45,0 , 45));
        scatterplot.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        scatterplot.addActionListener(new Tabelas(this, "scatterplot"));

        Botao contingenciaG = new Botao("Contingência");
        contingenciaG.setMargin(new Insets(0, 45,0 , 45));
        contingenciaG.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        contingenciaG.addActionListener(new Tabelas(this, "contingencia"));


        ///////////////////////////  TABELAS

        JLabel tituloTabela = new JLabel("TABELAS", SwingConstants.CENTER);
        tituloTabela.setFont(Fontes.ROBOTO_BOLD_MEDIA);
        tituloTabela.setForeground(Cores.corBotaoAzulEscuro);

        Botao frequenciasT = new Botao("Frequências");
        frequenciasT.setMargin(new Insets(0, 45,0 , 45));
        frequenciasT.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        frequenciasT.addActionListener(new Tabelas(this, "frequencias"));

        Botao contingencia = new Botao("Contingência");
        contingencia.setMargin(new Insets(0, 45,0 , 45));
        contingencia.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        contingencia.addActionListener(new Tabelas(this, "contingencia"));

        Botao covariancia = new Botao("Covariância");
        covariancia.setMargin(new Insets(0, 45,0 , 45));
        covariancia.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        covariancia.addActionListener(new Tabelas(this, "covariancia"));

        Botao correlacao = new Botao("Coeficiente");
        correlacao.setMargin(new Insets(0, 45,0 , 45));
        correlacao.configurarFonteCorFundo(Fontes.ROBOTO_MEDIA, Cores.azulEscuro2, Cores.rosaClaro2);
        correlacao.addActionListener(new Tabelas(this, "coeficiente"));


        painelLeste.add(tituloGrafico);
        painelLeste.add(histograma);
        painelLeste.add(frequenciasG);
        painelLeste.add(boxplot);
        painelLeste.add(scatterplot);
        painelLeste.add(contingenciaG);

        painelLeste.add(new JLabel(""));
        painelLeste.add(new JLabel(""));
        painelLeste.add(tituloTabela);
        painelLeste.add(frequenciasT);
        painelLeste.add(contingencia);
        painelLeste.add(covariancia);
        painelLeste.add(correlacao);

        painelLeste.setBackground(Cores.rosaClaro2);
        this.add(painelLeste, BorderLayout.EAST);

        dimensaoCental = new Dimension(800, 0);

        painelCentro.setPreferredSize(dimensaoCental);
        painelCentro.setBackground(Cores.azulEscuro);
        boxCentral = Box.createHorizontalBox();

        JScrollPane rolagem = new JScrollPane(painelCentro);
        rolagem.setPreferredSize(new Dimension( 800,0));
        rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        boxCentral.add(rolagem);
        boxCentral.setBorder(bordaVazia);
        this.add(boxCentral, BorderLayout.CENTER);

        // this.add(painelInferir, BorderLayout.SOUTH);

    }

    public class BotaoVoltar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            janela.getContentPane().removeAll();
            janela.conteudoJanela(new PainelInicial(janela));
            janela.revalidate();
            janela.repaint();
        }
    }

    public void adicionaPainelCentral(JPanel painel, int quanto){
        dimensaoCental.setSize(800, dimensaoCental.getHeight()+quanto);
        painelCentro.add(painel, 0);
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

}
