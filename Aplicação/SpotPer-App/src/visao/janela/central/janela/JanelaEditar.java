package visao.janela.central.janela;

import visao.janela.PainelInformacoes;
import visao.layout.Botao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EmptyStackException;

import controlador.Controlador;
import visao.layout.Cores;
import visao.layout.Fontes;

public class JanelaEditar {
    private Controlador controlador;
    private PainelInformacoes informacoes;
    private String nome;
    private JTable valor;

    private JPanel painel;
    private JPanel centro;
    private String[][] tabelaCSV;
    private String[] tituloTabela;

    public JanelaEditar(PainelInformacoes informacoes, String nome){
        this.controlador = informacoes.getControlador();
        this.informacoes = informacoes;
        this.nome = nome;
        //tabelaCSV = controlador.dadosCSV();
        //tituloTabela = controlador.titulosColunas();

        configuracoesPadrao();
    }

    public void configuracoesPadrao() {
        painel = new JPanel();
        painel.setPreferredSize(new Dimension(780, 680));
        painel.setLayout(new BorderLayout());

        centro = new JPanel();
        centro.setLayout(new GridLayout());

        JPanel salvar = new JPanel();
        salvar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BorderLayout());
        JLabel titulo = new JLabel("  "+nome);
        titulo.setFont(Fontes.TITULO);
        titulo.setForeground(Cores.corBotaoAzulEscuro);

        cabecalho.setBackground(Cores.corVerde);
        cabecalho.add(titulo, BorderLayout.WEST);
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


        String nomeArquivo = "Nome";
        String[] nomeArquivoArray = nomeArquivo.split("-");
        String tituloCalcular = "Nome do arquivo: "+nomeArquivoArray[nomeArquivoArray.length-1];

        valor = new JTable(tabelaCSV, tituloTabela){
            public boolean isCellEditable(int row,int column){
                Object o = getValueAt(row,column);
                return true;
            }
        };

        setTamanhoColuna(valor, tituloTabela);
        valor.setFillsViewportHeight(true);
        valor.getTableHeader().setReorderingAllowed(false);
        valor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane (valor);
        scrollPane.setPreferredSize(new Dimension( 700,500));

        centro.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), tituloCalcular, TitledBorder.CENTER, TitledBorder.TOP));
        centro.add(scrollPane, BorderLayout.NORTH);


        Botao botaoSalvar = new Botao("SALVAR");
        botaoSalvar.setMargin(new Insets(0, 0,0 , 0));
        botaoSalvar.configurarFonteCorFundo(Fontes.TITULO, Cores.corBranca, Cores.azulEscuro2);
        botaoSalvar.addActionListener(new Salvar());

        Botao botaoSalvarComo = new Botao("SALVAR COMO");
        botaoSalvarComo.setMargin(new Insets(0, 0,0 , 0));
        botaoSalvarComo.configurarFonteCorFundo(Fontes.TITULO, Cores.corBranca, Cores.corBotaoAzulEscuro);
        botaoSalvarComo.addActionListener(new SalvarComo());


        //Só aparece quando aperta o botao calcular

        salvar.add(botaoSalvar);
        salvar.add(botaoSalvarComo);
        painel.add(centro, BorderLayout.CENTER);
        painel.add(salvar, BorderLayout.SOUTH);

        informacoes.adicionaPainelCentral(painel, 690);

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

    private ArrayList<String> prepararArquivo(){
        try {
            ArrayList<String> lines = new ArrayList<>();
            String linhaCSV = "";
            for (int coluna = 0; coluna < valor.getColumnCount(); coluna++){
                linhaCSV += valor.getColumnName(coluna);
                if (coluna < tituloTabela.length-1) linhaCSV+=",";
            }
            lines.add(linhaCSV);
            for (int linha = 0; linha < valor.getRowCount(); linha++){
                String linhaCSVs;
                linhaCSVs = "";
                for (int coluna = 0; coluna < valor.getColumnCount(); coluna++){
                    String elemento = String.valueOf(valor.getValueAt(linha, coluna));
                    /*
                    if (controlador.eColunaNumerica(coluna) && ((elemento.matches("^([+-]?\\d*\\.?\\d*)$") || elemento.equals("NA") || elemento.equals("")))){
                        linhaCSVs = linhaCSVs + valor.getValueAt(linha, coluna);
                        if (coluna < valor.getColumnCount()-1) linhaCSVs += ",";
                    }
                    else if (!controlador.eColunaNumerica(coluna)){
                        if (elemento.contains(",") && !elemento.matches("^\".*\"")) linhaCSVs = linhaCSVs + "\""+valor.getValueAt(linha, coluna)+"\"";
                        else linhaCSVs = linhaCSVs + valor.getValueAt(linha, coluna);
                        if (coluna < valor.getColumnCount()-1) linhaCSVs += ",";
                    }

                    else{
                        URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                        Icon iconeErro = new ImageIcon(erroIcone);
                        JOptionPane.showMessageDialog (new JFrame(), "Não é possível colocar \""+elemento+"\" em uma coluna numérica", "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);
                        controlador.setControler(controlador.nomeArquivo);
                        throw new EmptyStackException();
                    }
                }*/

                lines.add(linhaCSVs);
            }
            return lines;

        } catch (Exception e){
            System.out.println("Erro ao salvar arquivo: " + e);
            return null;
        }
    }

    private void salvarArquivo(String onde){
        try {
            ArrayList<String> arquivo = prepararArquivo();
            if (arquivo == null) throw new EmptyStackException();
            Path file = Paths.get(onde);
            Files.write(file, arquivo, Charset.forName("utf-8"));

            controlador.setControler(controlador.nomeArquivo);
            URL okIcone = ClassLoader.getSystemResource("imagens/ok.png");
            Icon iconeOK = new ImageIcon(okIcone);
            JOptionPane.showMessageDialog (new JFrame(), "Arquivo salvo com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE, iconeOK);

        } catch (Exception e){
            System.out.println("Erro ao salvar o arquivo: "+e);
        }
    }

    public class Fechar implements ActionListener {
        JPanel painel;
        Fechar(JPanel painel){
            this.painel = painel;
        }
        public void actionPerformed(ActionEvent evento) {
            painel.removeAll();
            informacoes.eliminaPainelCentral(this.painel, 690);
        }
    }

    public class Salvar implements ActionListener{
        public void actionPerformed(ActionEvent evento){
            salvarArquivo(controlador.nomeArquivo);
        }
    }


    public class SalvarComo implements ActionListener{
        public void actionPerformed(ActionEvent evento){
            JFileChooser arquivo;
            LookAndFeel anteriro = UIManager.getLookAndFeel();
            String onde;
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                arquivo = new JFileChooser();
                UIManager.setLookAndFeel(anteriro);

            } catch (Exception e) {
                arquivo = new JFileChooser();
            }

            arquivo.setDialogTitle("Salvar arquivo como");
            FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("Arquivos CSV", "csv");
            arquivo.addChoosableFileFilter(filtroCSV);

            if(arquivo.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION){
                onde = arquivo.getSelectedFile().getAbsolutePath();
                salvarArquivo(onde);
            }
        }
    }
}
