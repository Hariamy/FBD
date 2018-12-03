import visao.janela.Janela;
import visao.janela.PainelInicial;
import visao.layout.Fontes;
import modelo.Conexao;
import modelo.Executar;


public class Main{
    public static void main(String args[]) {
       /*
        Executar c = new Executar();
        c.inseriTabela("Fnciona");
        String[] nomes = {"Hariamy", "Scarlat", "Felipe", "Matheus", "Gustavo", "San", "Diego", "Gabriel", "Samir", "Tibet"};
        //for (int i = 0; i < 10; i++) c.inserirTupla("Funciona", i, nomes[i]);
        //c.consulta(" Funciona");
*/
        System.setProperty("file.encoding", "UTF-8");

        Janela janela = new Janela();
        janela.conteudoJanela(new PainelInicial(janela));
        janela.exibirJanela();

        Fontes.setFonte();

    }

}