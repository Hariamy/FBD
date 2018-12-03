package visao.janela;
import visao.janela.usuario.PainelUsuario;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;


public class PainelInicial extends JPanel {
    private Janela janela;
    private boolean focoInicial = true;
    
    private JPanel painelSuperior = new JPanel();
    private JPanel painelInferior = new JPanel();
    private JPanel painelAdministrador = new JPanel();
    private JPanel painelUsuario = new JPanel();

    public PainelInicial(Janela janela){
        super();
        this.configuracoes();
        this.janela = janela;
        this.setVisible(true);
    }

    private void configuracoes(){

        //-----------------\\ LAYOUT DA PÁGINA INICIAL //-----------------\\
        Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        this.setBorder(bordaVazia);
        painelInferior.setBorder(bordaVazia);
        painelSuperior.setBorder(bordaVazia);

        this.setLayout(new BorderLayout());
        painelInferior.setLayout(new BorderLayout());
        painelSuperior.setLayout(new BorderLayout());

        this.setBackground(Cores.rosaClaro);
        painelSuperior.setBackground(Cores.rosaClaro);
        painelInferior.setBackground(Cores.rosaClaro);
        painelAdministrador.setBackground(Cores.rosaClaro);
        painelUsuario.setBackground(Cores.rosaClaro);
        
        
        //-----------------\\ INÍCIO Carrega Imagtem da Tela Inicial - Capa //-----------------\\
        try {
            //IMAGEM DA TELA DE INICIO
            URL inicial = ClassLoader.getSystemResource("imagens/inicial.png");
            Icon imagemTitulo = new ImageIcon(inicial);
            JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
            painelSuperior.add(titulo, BorderLayout.CENTER);
            painelSuperior.setPreferredSize(new Dimension(900, 450));

        }catch (Exception e){
            //Carrega icone alternativo
            Icon imagemTitulo = new ImageIcon(getClass().getResource("/imagens\\inicial.png"));
            JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
            painelSuperior.add(titulo, BorderLayout.CENTER);
            painelSuperior.setPreferredSize(new Dimension(900, 450));
        }
        //-----------------\\ FIM - Carrega Imagtem da Tela Inicial - Capa  //-----------------\\


        //-----------------\\ INÍCIO - BOTAO DO ADMINISTRADOR //-----------------\\

        Botao administrador = new Botao("ADMINISTRADOR");
        administrador.setMargin(new Insets(2, 10, 2, 10));
        administrador.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_PEQUENA, Cores.corBotaoAzulEscuro, Cores.corVerde);
        administrador.addActionListener(new BotaoAdministrador());
        administrador.requestFocusInWindow();
        
        painelAdministrador.add(administrador, BorderLayout.NORTH);
        painelInferior.add(painelAdministrador, "North");

        //-----------------\\ FIM - BOTAO DO ADMINISTRADOR //-----------------\\

        //-----------------\\ INÍCIO - BOTAO DO USUÁRIO //-----------------\\

        Botao uruario = new Botao("USUÁRIO");
        uruario.setMargin(new Insets(2, 10, 2, 10));
        uruario.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_PEQUENA, Cores.corBranca, Cores.corBotaoAzulEscuro);
        uruario.addActionListener(new BotaoUruario());

        painelUsuario.add(uruario, "Center");
        painelInferior.add(painelUsuario, "Center");
        
        //-----------------\\ FIM - BOTAO DO USUÁRIO //-----------------\\


        //-----------------\\ INÍCIO - ADICIONA OS PAINEIS NA JANELA PRINCIPAL //-----------------\\

        this.add(painelSuperior, "North");
        this.add(painelInferior, "Center");

        //-----------------\\ FIM - ADICIONA OS PAINEIS NA JANELA PRINCIPAL //-----------------\\

    }

    //-----------------\\ CLASSE IMPLEMENTA A CHAMADA DO PAINEL DO USUÁRIO //-----------------\\
    public class BotaoUruario implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            janela.getContentPane().removeAll();
            janela.conteudoJanela(new PainelUsuario(janela));
            janela.revalidate();
            janela.repaint();


        }
    }

    public class BotaoAlgumaCoisa implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            /*boolean erro;
            Controlador controlador = new Controlador(texto.getText());
            erro = controlador.erro();

            if (erro){
                String mensagem = "";
                if (texto.getText().equals("") || texto.getText().equals("Digite o diretório do arquivo")) mensagem = "<html>Escolha um arquivo clicando em administrador <br/>ou digite o diretório na caixa de texto</html>";
                else mensagem = "Não foi possível abrir o arquivo!";

                URL erroIcone = ClassLoader.getSystemResource("imagens/erro.png");
                Icon iconeErro = new ImageIcon(erroIcone);
                JOptionPane.showMessageDialog (new JFrame(), mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE, iconeErro);

            } else*/{
                //janela.setControlador(controlador);

            }

        }
    }
    
    public class BotaoAdministrador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            JFileChooser arquivo;
            LookAndFeel anteriro = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                arquivo = new JFileChooser();
                UIManager.setLookAndFeel(anteriro);

            } catch (Exception e) {
                arquivo = new JFileChooser();
            }

            FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("Arquivos CSV", "csv");
            arquivo.addChoosableFileFilter(filtroCSV);
            arquivo.setAcceptAllFileFilterUsed(false);

            if(arquivo.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION){
                //texto.setText(arquivo.getSelectedFile().getAbsolutePath());
            }
        }
    }

}
