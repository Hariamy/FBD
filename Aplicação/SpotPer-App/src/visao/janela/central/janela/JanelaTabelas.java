package visao.janela.central.janela;
import controlador.Controlador;
import visao.janela.PainelInformacoes;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;
import java.net.URL;

public class JanelaTabelas {
    //CONSTRUTOR
    private Controlador controlador;
    private PainelInformacoes informacoes;
    private String nome;
    private boolean coeficiente, covariancia, contingencia, frequencias;
    /////////////////////

    private String colunaFixa = "Nenhum";
    private String elementoFixo = "Nenhum";
    private String colunaCalculavel1;
    private String colunaCalculavel2;

    private JPanel painel;

    private JComboBox setColunaFixa;
    private JComboBox setElementoFixo;
    private JComboBox setColunaCalculavel1;
    private JComboBox setColunaCalculavel2;

    private final String[] nenhum = {"Nenhum"};

    private JPanel calcular;


    public JanelaTabelas(PainelInformacoes informacoes, String nome){
        this.controlador = informacoes.getControlador();
        this.informacoes = informacoes;
        this.nome = nome;
        if (nome.equals("coeficiente")) this.coeficiente = true;
        else if (nome.equals("covariancia")) this.covariancia = true;
        else if (nome.equals("contingencia")) this.contingencia = true;
        else if (nome.equals("frequencias")) this.frequencias = true;

        configuracaoPadrao();
    }

    public void configuracaoPadrao(){
        painel = new JPanel();
        painel.setPreferredSize(new Dimension(780, 680));
        painel.setLayout(new BorderLayout());

        JPanel painelBotao = new JPanel();
        painelBotao.setLayout(new GridBagLayout());
        painelBotao.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel escolhas = new JPanel();
        setPainelEscolhas(escolhas);
        escolhas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        JPanel centro = new JPanel();
        centro.setLayout(new BorderLayout());

        calcular = new JPanel();
        calcular.setLayout(new BorderLayout());
        calcular.setPreferredSize(new Dimension(700, 520));

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

        JLabel tituloColunaCalculavel1 = new JLabel(setInformacaoVariavel());
        String[] colunaCalculavel1 = colunasCalculaveis();
        this.colunaCalculavel1 = colunaCalculavel1[0];
        setColunaCalculavel1 = new JComboBox(colunaCalculavel1);
        setColunaCalculavel1.setMaximumRowCount(4);
        setColunaCalculavel1.addItemListener(new ColunaCalculavel1());

        JLabel tituloColunaCalculavel2 = new JLabel();
        if (coeficiente || covariancia || contingencia){
            tituloColunaCalculavel2 = new JLabel("EM RELAÇÃO:");
            String[] colunaCalculavel2 = colunasCalculaveis();
            this.colunaCalculavel2 = colunaCalculavel2[0];
            setColunaCalculavel2 = new JComboBox(colunaCalculavel2);
            setColunaCalculavel2.setMaximumRowCount(4);
            setColunaCalculavel2.addItemListener(new ColunaCalculavel2());

        }

        escolhas.add(tituloColunaCalculavel1);
        if (coeficiente || covariancia || contingencia) escolhas.add(tituloColunaCalculavel2);
        escolhas.add(tituloColunaFixa);
        escolhas.add(tituloElementoFixo);


        escolhas.add(setColunaCalculavel1);
        if (coeficiente || covariancia || contingencia) escolhas.add(setColunaCalculavel2);
        escolhas.add(setColunaFixa);
        escolhas.add(setElementoFixo);

        Botao botaoCalcular = new Botao("CALCULAR");
        botaoCalcular.setMargin(new Insets(0, 0,0 , 0));
        botaoCalcular.configurarFonteCorFundo(Fontes.TITULO, Cores.corBranca, Cores.azulEscuro2);
        botaoCalcular.addActionListener(new Calcular());

        painelBotao.add(botaoCalcular, gbc);

        centro.add(escolhas, BorderLayout.CENTER);
        centro.add(painelBotao, BorderLayout.EAST);

        painel.add(centro, BorderLayout.CENTER);
        painel.add(calcular, BorderLayout.SOUTH);

        informacoes.adicionaPainelCentral(painel, 690);

    }

    private void setPainelEscolhas(JPanel escolhas){
        if (frequencias){
            escolhas.setLayout(new GridLayout(2, 3, 5, 5));
        } else {
            escolhas.setLayout(new GridLayout(2, 4, 5, 5));
        }
    }

    private String[] colunasCalculaveis(){
        if (coeficiente || covariancia){
            return controlador.colunasNumericas();
        }
        return controlador.titulosColunas();
    }

    private String setTituloGeral(){
        String tituloGeral = "";
        String espaco = "  ";
        if (nome.matches("frequencias")) tituloGeral = "CALCULAR TABELA DE FREQUÊNCIAS";
        if (nome.matches("contingencia")) tituloGeral = "CALCULAR TABELA DE CONTINGÊNCIA";
        if (nome.matches("covariancia")) tituloGeral = "CALCULAR TABELA DE COVARIÂNCIA";
        if (nome.matches("coeficiente")) tituloGeral = "ALCULAR TABELA DE COEFICIENTE DE PEARSON";

        return espaco + tituloGeral;
    }

    private String setInformacao(){
        String tituloGeral = "";
        if (nome.matches("frequencias")) tituloGeral = "a tabela de frequências";
        if (nome.matches("contingencia")) tituloGeral = "a tabela de contingência";
        if (nome.matches("covariancia")) tituloGeral = "a tabela de covariancia";
        if (nome.matches("coeficiente")) tituloGeral = "o coeficiente de variação de Pearson";

        tituloGeral = "<html><center>É possível calcular "+tituloGeral+" selecionando todos os valores de um atribudo ou realizar o cálculo" +
                "<br/>restringindo a população para aqueles que possuem o mesmo valor em um atributo fixo.</center></html>";
        return tituloGeral;
    }

    private String setInformacaoVariavel(){
        String tituloGeral = "";
        if (nome.matches("frequencias")) tituloGeral = "FREQUÊNCIA";
        if (nome.matches("contingencia")) tituloGeral = "CONTINGENCIA";
        if (nome.matches("covariancia")) tituloGeral = "COVARIÂNCIA";
        if (nome.matches("coeficiente")) tituloGeral = "COEFICIENTE DE VARIAÇÃO";

        return tituloGeral+" DE:";
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

    public class ColunaCalculavel1 implements ItemListener {
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                colunaCalculavel1 = setColunaCalculavel1.getSelectedItem().toString();
            }

        }
    }


    public class ColunaCalculavel2 implements ItemListener {
        public void itemStateChanged(ItemEvent evento) {
            if (evento.getStateChange() == ItemEvent.SELECTED){
                colunaCalculavel2 = setColunaCalculavel2.getSelectedItem().toString();
            }

        }
    }

    public class Fechar implements ActionListener {
        JPanel painel;
        Fechar(JPanel painel){
            this.painel = painel;
        }
        public void actionPerformed(ActionEvent evento) {
            informacoes.eliminaPainelCentral(this.painel, 690);
        }
    }

    public class Calcular implements ActionListener {
        public void actionPerformed(ActionEvent evento) {

            String[] titulo = tituloTabela();
            String[][] resultado = calcularMetrica();
            String tituloCalcular = "";
            if (resultado != null){
                JTable valor;
                tituloCalcular += tituloCalcular(resultado);
                valor = new JTable(resultado, titulo){
                    public boolean isCellEditable(int row,int column){
                        Object o = getValueAt(row,column);
                        return false;
                    }
                };
                setTamanhoColuna(valor, titulo);
                valor.setFillsViewportHeight(true);
                valor.getTableHeader().setReorderingAllowed(false);
                valor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                valor.setPreferredScrollableViewportSize(valor.getPreferredSize());
                valor.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane (valor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setPreferredSize(new Dimension( 700,500));
                calcular.removeAll();
                calcular.add(scrollPane, BorderLayout.CENTER);

            }
            else {
                tituloCalcular = "NÃO HÁ ELEMENTOS";
                calcular.removeAll();
            }
            calcular.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), tituloCalcular, TitledBorder.CENTER, TitledBorder.TOP));

            calcular.repaint();
            painel.revalidate();

        }

        void setTamanhoColuna(JTable valor, String[] titulo){
            TableColumn colunas = null;
            int tamanho = titulo.length;
            int largura = 770/tamanho < 60? 60 : 770/tamanho - 1;


            for (int coluna = 0; coluna < tamanho; coluna++){
                colunas = valor.getColumnModel().getColumn(coluna);
                colunas.setPreferredWidth(largura);
            }
        }


        String[] tituloTabela(){
            if (coeficiente || covariancia){
                String[] titulo = new String[5];
                titulo[0] = colunaCalculavel1;
                titulo[1] = colunaCalculavel2;
                titulo[2] = "desvio de "+colunaCalculavel1;
                titulo[3] = "desvio de "+colunaCalculavel2;
                titulo[4] = "Covariancia";
                return titulo;

            } else if(frequencias){
                String[] titulo = new String[3];
                titulo[0] = colunaCalculavel1;
                titulo[1] = "Frequencia absoluat";
                titulo[2] = "Frequencia relativa";
                return titulo;

            }
            else{
                String[] titulo = new String[controlador.elementosColunaSemRepeticao(colunaCalculavel2).length+1];
                titulo[0] = colunaCalculavel1+" \\ "+colunaCalculavel2;
                System.arraycopy(controlador.elementosColunaSemRepeticao(colunaCalculavel2), 0, titulo, 1, controlador.elementosColunaSemRepeticao(colunaCalculavel2).length);
                return titulo;
            }
        }

        String[][] calcularMetrica(){
            Method metodo;
            Object texto;
            try {
                if (frequencias){
                    if (colunaFixa.matches("Nenhum")){
                        metodo = Controlador.class.getMethod(nome, String.class);
                        texto = metodo.invoke(controlador, colunaCalculavel1);
                        return (String[][])texto;
                    }
                    else {
                        metodo = Controlador.class.getMethod(nome, String.class, String.class, String.class);
                        texto = metodo.invoke(controlador, colunaFixa, elementoFixo, colunaCalculavel1);
                        return (String[][])texto;
                    }
                } else{
                    if (colunaFixa.matches("Nenhum")){
                        metodo = Controlador.class.getMethod(nome, String.class, String.class);
                        texto = metodo.invoke(controlador, colunaCalculavel1, colunaCalculavel2);
                        return (String[][])texto;
                    }
                    else {
                        metodo = Controlador.class.getMethod(nome, String.class, String.class, String.class, String.class);
                        texto = metodo.invoke(controlador, colunaFixa, elementoFixo, colunaCalculavel1, colunaCalculavel2);
                        return (String[][])texto;
                    }
                }

            } catch (Exception e){
                System.out.println("Não deu! "+ e);
                return null;
            }
        }

        String tituloCalcular(String[][] resultado){
            if (coeficiente) return Math.sqrt(Double.parseDouble(resultado[resultado.length-1][2])*Double.parseDouble(resultado[resultado.length-1][3])) == 0? "COEFICIENTE DE PEARSON = 0"
                    : "COEFICIENTE DE PEARSON = "+String.valueOf((Double.parseDouble(resultado[resultado.length-1][4]))/Math.sqrt(Double.parseDouble(resultado[resultado.length-1][2])*Double.parseDouble(resultado[resultado.length-1][3])));
            if (covariancia) return "VALOR DA COVARIÂNCIA: "+resultado[resultado.length-1][4];
            if (contingencia) return "TABELA DE CONTINGENCIA: ";
            else return "TABELA DE FREQUENCIA";
        }
    }
}
