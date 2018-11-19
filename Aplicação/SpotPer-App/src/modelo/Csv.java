package modelo;

import java.util.*;
import java.io.File;

public class Csv {
    private HashMap<String, Integer> cabecalhoMap;
    private String[] cabecalhoArray;
    private ArrayList<String[]> dados;
    private int numeroLinhas;
    private int numeroColunas;
    private boolean erro = false;

    protected Csv(File arquivo){
        int aux = 0;
        int cont = 1;
        dados = new ArrayList<>();
        cabecalhoMap = new HashMap<>();
        Scanner leitor = null;
        try {
            leitor = new Scanner(arquivo, "utf-8");

            String linhasDoArquivo;
            String cabecalhoString = "";
            if (leitor.hasNext()) cabecalhoString = leitor.nextLine();

            cabecalhoArray = cabecalhoString.split(",");

            numeroColunas = this.cabecalhoArray.length;

            for (int i = 0; i < numeroColunas; i++){
                this.cabecalhoMap.put(cabecalhoArray[i], i);
            }

            while(leitor.hasNext()) {

                linhasDoArquivo = leitor.nextLine();
                String[] vetorDaLinha = new String[numeroColunas];
                String[] valoresEntreVirgulas = linhasDoArquivo.split(",");
                String valor = "";
                int indice = 0;
                for (int i = 0; i < valoresEntreVirgulas.length; i++) {
                    if (valoresEntreVirgulas[i].length() != 0) {

                        char a = valoresEntreVirgulas[i].charAt(0);
                        char b =  valoresEntreVirgulas[i].charAt(valoresEntreVirgulas[i].length()-1);
                        if (a == '"') {
                            valor = "";
                            aux = 1;
                        }
                        if (b == '"') {
                            valor += valoresEntreVirgulas[i];
                            vetorDaLinha[indice] = valor;
                            indice++;
                            aux = 0;
                        }
                        else if (aux == 0) {
                            valor = valoresEntreVirgulas[i];
                            vetorDaLinha[indice] = valor;
                            indice++;
                        }
                        if (aux == 1) {
                            valor += valoresEntreVirgulas[i]+",";
                        }

                    } else if (valoresEntreVirgulas[i].matches("")){
                        vetorDaLinha[indice] = "";
                        indice++;
                    } else {
                        vetorDaLinha[indice] = valoresEntreVirgulas[i];
                        indice++;
                    }
                }

                if (valoresEntreVirgulas.length == numeroColunas-1) vetorDaLinha[numeroColunas-1] = "";
                else if (valoresEntreVirgulas.length != numeroColunas) throw new EmptyStackException();

                dados.add(vetorDaLinha);
                cont++;
            }

            numeroLinhas = dados.size();
            leitor.close();

        } catch(Exception e) {
            erro = true;
            if (leitor != null) leitor.close();
            System.out.println("Erro ao carregar o CSV: "+cont+". Erro: "+e);
        }
    }

    boolean getErro(){
        return this.erro;
    }

    HashMap<String, Integer> getCabecalhoMap(){
        return this.cabecalhoMap;
    }

    String[] getCabecalhoArray(){
        return this.cabecalhoArray;
    }

    ArrayList<String[]> getDados(){
        return this.dados;
    }

    int getNumeroLinhas() {
        return this.numeroLinhas;
    }

    int getNumeroColunas() {
        return this.numeroColunas;
    }

    String getElemento(int linha, int coluna) {
        return this.dados.get(linha)[coluna];
    }

}
