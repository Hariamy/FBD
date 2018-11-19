package visao.janela.central.janela;

import controlador.Controlador;
import visao.janela.PainelInformacoes;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;
import java.net.URL;


public class JanelaNumericos {

    private Controlador controlador;
    private PainelInformacoes informacoes;
    private String nome;
    boolean escolha = false;
    private String colunaFixa = "Nenhum";
    private String elementoFixo;
    private String colunaCalculavel;
    private int colunaMedia;

    private JLabel valor;

    private JComboBox setColunaFixa;
    private JComboBox setElementoFixo;
    private JComboBox setColunaCalculavel;


    private String[] nenhum = {"Nenhum"};

    public JanelaNumericos(PainelInformacoes informacoes, String nome){
        this.controlador = informacoes.getControlador();
        this.informacoes = informacoes;
        this.nome = nome;
        this.valor = new JLabel();
        configuracaoPadrao();
    }

    public void configuracaoPadrao() {

        JPanel painel = new JPanel();
        JPanel painelBotao = new JPanel();
        painel.setPreferredSize(new Dimension(780, 220));
        painel.setLayout(new BorderLayout());

        painelBotao.setLayout(new GridBagLayout());
        painelBotao.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel escolhas = new JPanel();
        JPanel centro = new JPanel();
        centro.setLayout(new BorderLayout());
        escolhas.setLayout(new GridLayout(2, 3, 5, 5));
        escolhas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));


        JPanel calcular = new JPanel();
        calcular.setLayout(new GridBagLayout());
        calcular.setPreferredSize(new Dimension(740, 60));

        //CABECALHO
        JPanel cabecalho = new JPanel();
        JPanel cabecalhoTitulo = new JPanel();
        JPanel cabecalhoInformacao = new JPanel();
        cabecalhoTitulo.setBackground(Cores.corVerde);

        cabecalho.setLayout(new BorderLayout());
        cabecalhoTitulo.setLayout(new BorderLayout());
        cabecalhoInformacao.setLayout(new FlowLayout());

        JLabel tituloGeral = new JLabel(setTituloGeral());
        tituloGeral.setFont(Fontes.TITULO);
        tituloGeral.setForeground(Cores.corBotaoAzulEscuro);

        cabecalhoTitulo.add(tituloGeral, BorderLayout.WEST);
        try {
            URL fecharIcone = ClassLoader.getSystemResource("imagens/fechar.png");
            Icon fechar = new ImageIcon(fecharIcone);
            Botao botaoFechar = new Botao(fechar);
            botaoFechar.setContentAreaFilled(false);
            botaoFechar.addActionListener(new Fechar(painel));
            cabecalhoTitulo.add(botaoFechar, BorderLayout.EAST);
        } catch (Exception e){
            //apagar
            Icon fechar = new ImageIcon(getClass().getResource("/imagens\\fechar.png"));
            Botao botaoFechar = new Botao(fechar);
            botaoFechar.setContentAreaFilled(false);
            botaoFechar.addActionListener(new Fechar(painel));
            cabecalhoTitulo.add(botaoFechar, BorderLayout.EAST);
        }

        cabecalhoInformacao.add(new JLabel(setInformacao()));
        cabecalho.add(cabecalhoTitulo, BorderLayout.NORTH);
        cabecalho.add(cabecalhoInformacao, BorderLayout.CENTER);
        painel.add(cabecalho, BorderLayout.NORTH);

        //pega a coluna de escolha de media
        JLabel tituloColunaFixa = new JLabel("<html>OPCIONAL:<BR/>Escolha um atributo fixo</html>");
        String[] colunaFixa = new String[controlador.titulosColunas().length+1];
        colunaFixa[0] = "Nenhum";
        System.arraycopy(controlador.titulosColunas(), 0, colunaFixa, 1, controlador.titulosColunas().length);
        controlador.titulosColunas();
        setColunaFixa = new JComboBox(colunaFixa);
        setColunaFixa.setMaximumRowCount(4);
        setColunaFixa.addItemListener(new ColunaFixa());

        JLabel tituloElementoFixo = new JLabel("<html>OPCIONAL:<BR/>Escolha um elemento fixo</html>");
        setElementoFixo = new JComboBox(nenhum);
        setElementoFixo.setMaximumRowCount(4);
        setElementoFixo.addItemListener(new ElementoFixo());

        JLabel tituloColunaCalculavel = new JLabel(setInformacaoVariavel());
        String[] colunaCalculavel = controlador.colunasNumericas();
        this.colunaCalculavel = colunaCalculavel[0];
        setColunaCalculavel = new JComboBox(colunaCalculavel);
        setColunaCalculavel.setMaximumRowCount(4);
        setColunaCalculavel.addItemListener(new ColunaCalculavel());

        escolhas.add(tituloColunaCalculavel);
        escolhas.add(tituloColunaFixa);
        escolhas.add(tituloElementoFixo);

        escolhas.add(setColunaCalculavel);
        escolhas.add(setColunaFixa);
        escolhas.add(setElementoFixo);

        Botao botaoCalcular = new Botao("CALCULAR");
        botaoCalcular.setMargin(new Insets(0, 0,0 , 0));
        botaoCalcular.configurarFonteCorFundo(Fontes.TITULO, Cores.corBranca, Cores.azulEscuro2);
        botaoCalcular.addActionListener(new Calcular());

        //Só aparece quando aperta o botao calcular
        calcular.add(valor, gbc);

        painelBotao.add(botaoCalcular, gbc);
        centro.add(escolhas, BorderLayout.CENTER);
        centro.add(painelBotao, BorderLayout.EAST);
        painel.add(centro, BorderLayout.CENTER);
        painel.add(calcular, BorderLayout.SOUTH);


        informacoes.adicionaPainelCentral(painel, 230);

    }

    String setTituloGeral(){
        String tituloGeral = "";
        String espaco = "  ";
        if (nome.matches("media")) tituloGeral = "CALCULAR MÉDIA";
        if (nome.matches("moda")) tituloGeral = "CALCULAR MODA";
        if (nome.matches("mediana")) tituloGeral = "CALCULAR MEDIANA";
        if (nome.matches("desvio")) tituloGeral = "ALCULAR DESVIO PADRÃO";
        if (nome.matches("variancia")) tituloGeral = "CALCULAR VARIÂNCIA";
        if (nome.matches("minimo")) tituloGeral = "CALCULAR MÍNIMO";
        if (nome.matches("maximo")) tituloGeral = "CALCULAR MÁXIMO";
        if (nome.matches("skewness")) tituloGeral = "CALCULAR SKEWNESS";
        if (nome.matches("kurtosis")) tituloGeral = "CALCULAR KURTOSIS";

        tituloGeral = espaco + tituloGeral;
        return tituloGeral;
    }

    String setInformacao(){
        String tituloGeral = "";
        if (nome.matches("media")) tituloGeral = "a média";
        if (nome.matches("moda")) tituloGeral = "a moda";
        if (nome.matches("mediana")) tituloGeral = "a mediana";
        if (nome.matches("desvio")) tituloGeral = "o desvio padrão";
        if (nome.matches("variancia")) tituloGeral = "a variância";
        if (nome.matches("minimo")) tituloGeral = "o elemento mínimo";
        if (nome.matches("maximo")) tituloGeral = "o elemento máximo";
        if (nome.matches("skewness")) tituloGeral = "skewness";
        if (nome.matches("kurtosis")) tituloGeral = "kurtosis";
        String info = "<html><center>É possível calcular "+tituloGeral+" selecionando todos os valores de um atribudo ou realizar o cálculo" +
                "<br/>restringindo a população para aqueles que possuem o mesmo valor em um atributo fixo.</center></html>";
        return info;
    }

    String setInformacaoVariavel(){
        String tituloGeral = "";
        if (nome.matches("media")) tituloGeral = "MÉDIA";
        if (nome.matches("moda")) tituloGeral = "MODA";
        if (nome.matches("mediana")) tituloGeral = "MEDIANA";
        if (nome.matches("desvio")) tituloGeral = "DESVIO PADRÃO";
        if (nome.matches("variancia")) tituloGeral = "VARIÂNCIA";
        if (nome.matches("minimo")) tituloGeral = "MÍNIMO";
        if (nome.matches("maximo")) tituloGeral = "MÁXIMO";
        if (nome.matches("skewness")) tituloGeral = "SKEWNES";
        if (nome.matches("kurtosis")) tituloGeral = "KURTOSIS";

        return tituloGeral+" EM RELAÇÃO:";
    }

    String setResultado(String valor){
        String tituloGeral = "";
        String fixo = "";
        if (nome.matches("media")) tituloGeral = "A Média";
        if (nome.matches("moda")) tituloGeral = "A Moda";
        if (nome.matches("mediana")) tituloGeral = "A Mediana";
        if (nome.matches("desvio")) tituloGeral = "O Desvio padrão";
        if (nome.matches("variancia")) tituloGeral = "A Variância";
        if (nome.matches("minimo")) tituloGeral = "O elemento Mínimo";
        if (nome.matches("maximo")) tituloGeral = "O elemento Máximo";
        if (nome.matches("skewness")) tituloGeral = "Skewness";
        if (nome.matches("kurtosis")) tituloGeral = "Kurtosis";


        if (!colunaFixa.matches("Nenhum")) fixo = "<br/>que possuem o elemento: "+elementoFixo+" no atributo: "+colunaFixa;

        String result = "<html><center>"+tituloGeral+" dos elementos da propriedade: "+colunaCalculavel+" "+fixo+" <br/>é igual a "+valor+"</center></html>";
        if (nome.matches("moda")) result = "<html><center>Os elementos da propriedade: "+colunaCalculavel+" "+fixo+"<br/>"+valor+"</center></html>";
        return result;
    }

    public class ColunaFixa implements ItemListener {
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                colunaFixa = setColunaFixa.getSelectedItem().toString();
                String[] vetor;
                if (colunaFixa.matches("Nenhum")){
                    vetor = nenhum;
                }else{
                    vetor =  controlador.elementosColunaSemRepeticao(colunaFixa);
                    elementoFixo = vetor[0];
                }

                DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel(vetor);
                setElementoFixo.setModel(modelo);
                informacoes.revalidate();

            }

        }
    }

    public class ElementoFixo implements ItemListener {
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                elementoFixo = setElementoFixo.getSelectedItem().toString();
            }

        }
    }

    public class ColunaCalculavel implements ItemListener {
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                colunaCalculavel = setColunaCalculavel.getSelectedItem().toString();
            }

        }
    }

    public class Fechar implements ActionListener {
        JPanel painel;
        Fechar(JPanel painel){
            this.painel = painel;
        }
        public void actionPerformed(ActionEvent evento) {
            informacoes.eliminaPainelCentral(this.painel, 230);
        }
    }

    public class Calcular implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            Method metodo;
            Object texto;
            try{
                if (colunaFixa.matches("Nenhum")){
                    metodo = Controlador.class.getMethod(nome, String.class);
                    texto = metodo.invoke(controlador, colunaCalculavel);
                    valor.setText(setResultado(String.valueOf(texto)));

                } else{
                    metodo = Controlador.class.getMethod(nome, String.class, String.class, String.class);
                    texto = metodo.invoke(controlador, colunaFixa, elementoFixo, colunaCalculavel);
                    valor.setText(setResultado(String.valueOf(texto)));
                }

                informacoes.revalidate();

            } catch (Exception e){
                System.out.println("Nao deu: "+e);
            }
        }
    }
}
