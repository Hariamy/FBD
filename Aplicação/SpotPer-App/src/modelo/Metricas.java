package modelo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;


public class Metricas extends Csv {

    public Metricas(File file){
        super(file);
    }

    public boolean colunaNumerica(int coluna){
        int tamanho = getNumeroLinhas();
        for (int linha = 0; linha < tamanho; linha ++){
            String elemento = getElemento(linha, coluna);
            if (!(elemento.equals("NA") || elemento.equals("") || elemento.matches("^([+-]?\\d*\\.?\\d*)$"))) return false;
        }
        return true;

    }

    protected boolean eNumerico(int linha, int coluna){
        if (getElemento(linha, coluna).equals("")) return false;
        if (getElemento(linha, coluna).matches("^([+-]?\\d*\\.?\\d*)$")) return true;
        else return false;
    }

    public int indiceCabecalho(String nome){
        return getCabecalhoMap().get(nome);
    }

    public double elementoNumerico(int linha, int coluna){
        return Double.parseDouble(getElemento(linha, coluna));
    }

    public boolean erroArquivo(){
        return this.getErro();
    }

    public String tituloColuna(int coluna) {
        return this.getCabecalhoArray()[coluna];
    }

    public ArrayList<String[]> getDadosCSV(){
        return this.getDados();
    }

    public String[] titulosColunas(){
        return getCabecalhoArray();
    }

    public String[] elementosSemRepeticao(int coluna){
        int tamanho = this.getNumeroLinhas();
        Set<String> semRepeticao = new HashSet<>();
        int indice = 0;
        if (coluna < this.getNumeroColunas()){
            for (int linha = 0; linha < tamanho; linha++){
                semRepeticao.add(getElemento(linha, coluna));
            }
        }

        String[] elementos = new String[semRepeticao.size()];

        for (String item : semRepeticao){
            elementos[indice] = item;
            indice++;
        }

        return elementos;

    }

    public String[] colunasNumericas(){
        int tamanho = this.getNumeroColunas();
        String[] auxiliar = new String[tamanho];
        String[] elementos;
        int indice = 0;
        for (int coluna = 0; coluna < tamanho; coluna++){
            if (colunaNumerica(coluna)){
                auxiliar[indice] = tituloColuna(coluna);
                indice++;
            }
        }
        elementos = new String[indice];
        for (int i = 0; i < indice; i++){
            elementos[i] = auxiliar[i];
        }
        return elementos;
    }

    public String[] getElementosColuna(int coluna){
        String[] resultado = new String[getNumeroLinhas()];
        for (int linha = 0; linha < getNumeroLinhas(); linha++){
            resultado[linha] = getElemento(linha, coluna);
        }
        return resultado;
    }
    public double media(int coluna) {
        double media = 0;
        int tamanho = this.getNumeroLinhas();
        int total = 0;
        for (int linha = 0; linha < tamanho; linha++) {
            if (this.eNumerico(linha, coluna)){
                media += this.elementoNumerico(linha, coluna);
                total++;
            }
        }
        return total > 0?  media/total : 0;
    }

    public double media(int colunaFixa, String nome, int coluna) {
        double media = 0;
        int tamanho = this.getNumeroLinhas();
        int total = 0;
        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && eNumerico(linha, coluna)){
                media += this.elementoNumerico(linha, coluna);
                total++;
            }
        }
        return total > 0?  media/total : 0;
    }

    public double variancia(int coluna) {
        double media = this.media(coluna);
        double variancia = 0;
        int total = 0;
        int tamanho = this.getNumeroLinhas();

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.eNumerico(linha, coluna)){
                variancia += Math.pow(this.elementoNumerico(linha, coluna)-media, 2);
                total++;
            }
        }
        return total > 0?  variancia/total : 0;
    }
    public double variancia(int colunaFixa, String nome, int coluna) {
        double media = this.media(colunaFixa, nome, coluna);
        double variancia = 0;
        int tamanho = this.getNumeroLinhas();
        int total = 0;
        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)){
                variancia += Math.pow(this.elementoNumerico(linha, coluna)-media, 2);
                total++;
            }
        }
        return total > 0?  variancia/total : 0;
    }

    public double desvio(int coluna) {
        double variancia = this.variancia(coluna);
        return Math.sqrt(variancia);
    }

    public double desvio(int colunaFixa, String nome, int coluna) {
        double variancia = this.variancia(colunaFixa, nome, coluna);
        return Math.sqrt(variancia);
    }


    public double mediana(int coluna) {
        int total = 0;
        double valor = 0;
        int tamanho = this.getNumeroLinhas();
        double mediana[] = new double[tamanho];

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.eNumerico(linha, coluna)) {
                mediana[linha] = this.elementoNumerico(linha, coluna);
                total++;
            }
        }

        if (total == 0) return 0;

        Arrays.sort(mediana);

        if ((total)%2 == 0) {
            valor = (mediana[total/2] + mediana[total/2+1]) / 2;
        } else {
            valor = mediana[(total+1)/2];
        }

        return valor;
    }

    public double mediana(int colunaFixa, String nome, int coluna) {
        int total = 0;
        double valor = 0;
        int tamanho = this.getNumeroLinhas();
        double mediana[] = new double[tamanho];

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)) {
                mediana[linha] = this.elementoNumerico(linha, coluna);
                total++;
            }
        }
        int inicio = tamanho - total;
        if (total == 0) return 0;

        Arrays.sort(mediana);

        if ((total)%2 == 0) {
            valor = (mediana[(total/2)+inicio] + mediana[((total+1)/2)+inicio]) / 2;

        } else {
            valor = mediana[((total+1)/2)+inicio];

        }

        return valor;
    }


    public double[] moda(int coluna) {
        int tamanho = this.getNumeroLinhas();
        double[] moda = new double[tamanho+1];
        int indice = 1;
        int repeticao = 0;
        int maior = 0;
        HashMap<Double, Integer> dicionario = new HashMap<>();
        for (int linha = 0; linha < tamanho ; linha++) {
            if (this.eNumerico(linha, coluna)) {
                if (dicionario.containsKey(this.elementoNumerico(linha, coluna))) {
                    repeticao = dicionario.get(this.elementoNumerico(linha, coluna));
                    repeticao++;
                    dicionario.put(this.elementoNumerico(linha, coluna), repeticao);
                } else {
                    repeticao = 1;
                    dicionario.put(this.elementoNumerico(linha, coluna), repeticao);
                }

                if (repeticao > maior) {
                    maior = repeticao;
                    indice = 1;
                    moda[indice] = this.elementoNumerico(linha, coluna);
                    indice++;

                } else if (repeticao == maior) {
                    moda[indice] = this.elementoNumerico(linha, coluna);
                    indice++;
                }
            }
        }

        if (maior == 1) moda[0] = 0;
        else moda[0] = indice;

        return moda;


    }

    public double[] moda(int colunaFixa, String nome, int coluna) {
        int tamanho = this.getNumeroLinhas();
        double[] moda = new double[tamanho + 1];
        int indice = 1;
        int repeticao = 0;
        int maior = 0;
        HashMap<Double, Integer> dicionario = new HashMap<>();

        for (int linha = 0; linha < tamanho; linha++) {

            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)) {
                if (dicionario.containsKey(this.elementoNumerico(linha, coluna))) {
                    repeticao = dicionario.get(this.elementoNumerico(linha, coluna));
                    repeticao++;
                    dicionario.put(this.elementoNumerico(linha, coluna), repeticao);


                } else {
                    repeticao = 1;
                    dicionario.put(this.elementoNumerico(linha, coluna), repeticao);
                }

                if (repeticao == maior) {
                    moda[indice] = this.elementoNumerico(linha, coluna);
                    indice++;

                } else if (repeticao > maior) {
                    maior = repeticao;
                    indice = 1;
                    moda[indice] = this.elementoNumerico(linha, coluna);
                    indice++;
                }
            }
        }
        if (maior == 1) moda[0] = 0;
        else moda[0] = indice;

        return moda;


    }


    public double minimo(int coluna) {
        int tamanho = this.getNumeroLinhas();
        boolean primeiro = true;
        double minimo = 0;

        for (int linha = 0; linha < tamanho ; linha++) {
            if (this.eNumerico(linha, coluna)) {
                if (primeiro) {
                    minimo = this.elementoNumerico(linha, coluna);
                    primeiro = false;

                } else if (minimo > this.elementoNumerico(linha, coluna)){
                    minimo = this.elementoNumerico(linha, coluna);
                }
            }
        }


        return minimo;
    }

    public double minimo(int colunaFixa, String nome, int coluna) {
        int tamanho = this.getNumeroLinhas();
        boolean primeiro = true;
        double minimo = 0;

        for (int linha = 0; linha < tamanho ; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)) {
                if (primeiro) {
                    primeiro = false;
                    minimo = this.elementoNumerico(linha, coluna);

                } else if (minimo > this.elementoNumerico(linha, coluna)){
                    minimo = this.elementoNumerico(linha, coluna);
                }
            }
        }

        return minimo;
    }


    public double maximo(int coluna) {
        int tamanho = this.getNumeroLinhas();
        double maximo = 0;
        boolean primeiro = true;

        for (int linha = 0; linha < tamanho ; linha++) {
            if (this.eNumerico(linha, coluna)) {
                if (primeiro) {
                    primeiro = false;
                    maximo = this.elementoNumerico(linha, coluna);

                } else if (maximo < this.elementoNumerico(linha, coluna)){
                    maximo = this.elementoNumerico(linha, coluna);
                }  ;
            }
        }

        return maximo;
    }

    public double maximo(int colunaFixa, String nome, int coluna) {
        int tamanho = this.getNumeroLinhas();
        double maximo = 0;
        boolean primeiro = true;

        for (int linha = 0; linha < tamanho ; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)) {
                if (primeiro) {
                    maximo = this.elementoNumerico(linha, coluna);
                    primeiro = false;

                } else if (maximo < this.elementoNumerico(linha, coluna)){
                    maximo = this.elementoNumerico(linha, coluna);
                }  ;
            }
        }

        return maximo;
    }

    public double kurtosis (int coluna) {
        double media = media(coluna);
        double somatorio1 = 0;
        double somatorio2 = 0;
        int tamanho = getNumeroLinhas();
        int total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (eNumerico(linha, coluna)) {
                somatorio1 += (Math.pow((elementoNumerico(linha, coluna) - media), 4));
                somatorio2 += (Math.pow((elementoNumerico(linha, coluna) - media), 2));
                total++;
            }
        }

        return (Math.pow(somatorio2, 2)) == 0? 0 : (somatorio1*total)/(Math.pow(somatorio2, 2));
    }

    public double kurtosis (int colunaFixa, String nome, int coluna) {
        double media = media(colunaFixa, nome, coluna);
        double somatorio1 = 0;
        double somatorio2 = 0;
        int tamanho = getNumeroLinhas();
        int total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && eNumerico(linha, coluna)) {
                somatorio1 += (Math.pow((elementoNumerico(linha, coluna) - media), 4));
                somatorio2 += (Math.pow((elementoNumerico(linha, coluna) - media), 2));
                total++;
            }
        }

        return (Math.pow(somatorio2, 2)) == 0? 0 : (somatorio1*total)/(Math.pow(somatorio2, 2));
    }

    public double skewness (int coluna) {
        double media = media(coluna);
        double somatorio1 = 0;
        double somatorio2 = 0;
        int tamanho = getNumeroLinhas();
        int total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (eNumerico(linha, coluna)) {
                somatorio1 += (Math.pow(elementoNumerico(linha, coluna) - media, 3));
                somatorio2 += (Math.pow(elementoNumerico(linha, coluna) - media, 2));
                total++;
            }
        }

        return Math.pow(somatorio2, 1.5) == 0? 0 : (somatorio1) *Math.sqrt(total) / Math.pow(somatorio2, 1.5);
    }

    public double skewness (int colunaFixa, String nome, int coluna) {
        double media = media(colunaFixa, nome, coluna);
        double somatorio1 = 0;
        double somatorio2 = 0;
        int tamanho = getNumeroLinhas();
        int total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && eNumerico(linha, coluna)) {
                somatorio1 += (Math.pow(elementoNumerico(linha, coluna) - media, 3));
                somatorio2 += (Math.pow(elementoNumerico(linha, coluna) - media, 2));
                total++;
            }
        }

        return Math.pow(somatorio2, 1.5) == 0? 0 :(somatorio1) *Math.sqrt(total) / Math.pow(somatorio2, 1.5);
    }


    public ArrayList<Double[]> covariancia(int coluna1, int coluna2) {
        ArrayList<Double[]> covariancia = new ArrayList<>();
        Double[] vetorLinha;
        int tamanho = this.getNumeroLinhas();
        double somaColuna1 = 0;
        double somaColuna2 = 0;
        double somaColuna3 = 0;
        double somaColuna4 = 0;
        double somaColuna5 = 0;
        double mediaColuna1 = 0;
        double mediaColuna2 = 0;
        double mediaColuna3 = 0;
        double mediaColuna4 = 0;
        double mediaColuna5 = 0;
        double total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                somaColuna1 += elementoNumerico(linha, coluna1);
                somaColuna2 += elementoNumerico(linha, coluna2);
                total++;
            }
        }

        mediaColuna1 = somaColuna1/total;
        mediaColuna2 = somaColuna2/total;

        if (total != 0){
            for (int linha = 0; linha < tamanho; linha++) {
                if (this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                    vetorLinha = new Double[5];
                    vetorLinha[0] = elementoNumerico(linha, coluna1);
                    vetorLinha[1] = elementoNumerico(linha, coluna2);

                    vetorLinha[2] = vetorLinha[0] - mediaColuna1;
                    somaColuna3 += vetorLinha[2];
                    vetorLinha[3] = vetorLinha[1] - mediaColuna2;
                    somaColuna4 += vetorLinha[3];

                    vetorLinha[4] = vetorLinha[2]*vetorLinha[3];
                    somaColuna5 += vetorLinha[4];
                    covariancia.add(vetorLinha);

                }
            }
            vetorLinha = new Double[5];
            mediaColuna3 = somaColuna3/total;
            mediaColuna4 = somaColuna4/total;
            mediaColuna5 = somaColuna5/total;

            vetorLinha[0] = mediaColuna1;
            vetorLinha[1] = mediaColuna2;
            vetorLinha[2] = mediaColuna3 < 0.00000000001? 0 : mediaColuna3;
            vetorLinha[3] = mediaColuna4 < 0.00000000001? 0 : mediaColuna4;
            vetorLinha[4] = mediaColuna5;
            covariancia.add(vetorLinha);

            return covariancia;
        }
        return null;
    }


    public ArrayList<Double[]> covariancia(int colunaFixa, String nome, int coluna1, int coluna2){
        ArrayList<Double[]> covariancia = new ArrayList<>();
        Double[] vetorLinha;
        int tamanho = this.getNumeroLinhas();
        double somaColuna1 = 0;
        double somaColuna2 = 0;
        double somaColuna3 = 0;
        double somaColuna4 = 0;
        double somaColuna5 = 0;
        double mediaColuna1 = 0;
        double mediaColuna2 = 0;
        double mediaColuna3 = 0;
        double mediaColuna4 = 0;
        double mediaColuna5 = 0;
        double total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                somaColuna1 += elementoNumerico(linha, coluna1);
                somaColuna2 += elementoNumerico(linha, coluna2);
                total++;
            }
        }

        mediaColuna1 = somaColuna1/total;
        mediaColuna2 = somaColuna2/total;

        if (total != 0){
            for (int linha = 0; linha < tamanho; linha++) {
                if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                    vetorLinha = new Double[5];
                    vetorLinha[0] = elementoNumerico(linha, coluna1);
                    vetorLinha[1] = elementoNumerico(linha, coluna2);

                    vetorLinha[2] = vetorLinha[0] - mediaColuna1;
                    somaColuna3 += vetorLinha[2];
                    vetorLinha[3] = vetorLinha[1] - mediaColuna2;
                    somaColuna4 += vetorLinha[3];

                    vetorLinha[4] = vetorLinha[2]*vetorLinha[3];
                    somaColuna5 += vetorLinha[4];

                    covariancia.add(vetorLinha);
                }
            }
            vetorLinha = new Double[5];
            mediaColuna3 = somaColuna3/total;
            mediaColuna4 = somaColuna4/total;
            mediaColuna5 = somaColuna5/total;

            vetorLinha[0] = mediaColuna1;
            vetorLinha[1] = mediaColuna2;
            vetorLinha[2] = mediaColuna3 < 0.00000000001? 0 : mediaColuna3;
            vetorLinha[3] = mediaColuna4 < 0.00000000001? 0 : mediaColuna4;
            vetorLinha[4] = mediaColuna5;
            covariancia.add(vetorLinha);

            return covariancia;

        }
        return null;
    }

    public HashMap<String, Float[]> frequenciasT(int coluna) {
        String[] chaves = elementosSemRepeticao(coluna);
        HashMap<String, Float[]> frequencia = new HashMap<>();
        int total = 0;

        for(int indice = 0; indice < chaves.length; indice++) {
            Float[] valor = new Float[2];
            valor[0] = Float.parseFloat("0");
            frequencia.put(chaves[indice], valor);
        }

        int tamanho = this.getNumeroLinhas();

        for (int linha = 0; linha < tamanho; linha++) {
            Float[] valor = frequencia.get(getElemento(linha, coluna));
            valor[0] ++;
            frequencia.put(getElemento(linha, coluna), valor);
            total++;
        }

        for (int indice = 0; indice < chaves.length; indice++) {
            Float[] valor = frequencia.get(chaves[indice]);
            float repeticao =  valor[0];
            valor[1] = (repeticao*100) / total;
            frequencia.put(chaves[indice], valor);
        }
        return frequencia;


    }

    public HashMap<String, Float[]> frequenciasT(int colunaFixa, String nome, int coluna) {
        String[] chaves = elementosSemRepeticao(coluna);
        HashMap<String, Float[]> frequencia = new HashMap<>();
        int total = 0;

        for(int indice = 0; indice < chaves.length; indice++) {
            Float[] valor = new Float[2];
            valor[0] = Float.parseFloat("0");
            frequencia.put(chaves[indice], valor);
        }

        int tamanho = this.getNumeroLinhas();

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome)){
                Float[] valor = frequencia.get(getElemento(linha, coluna));
                valor[0] ++;
                frequencia.put(getElemento(linha, coluna), valor);
                total++;
            }
        }

        for (int indice = 0; indice < chaves.length; indice++) {
            Float[] valor = frequencia.get(chaves[indice]);
            if (valor[0] == 0){
                frequencia.remove(chaves[indice]);
            }
            else {
                float repeticao =  valor[0];
                valor[1] = (repeticao*100) / total;
                frequencia.put(chaves[indice], valor);
            }
        }
        return frequencia;
    }

    public ArrayList<Double[]> coeficiente(int coluna1, int coluna2){
        ArrayList<Double[]> coeficiente = new ArrayList<>();
        Double[] vetorLinha;
        int tamanho = this.getNumeroLinhas();
        double somaColuna1 = 0;
        double somaColuna2 = 0;
        double somaColuna3 = 0;
        double somaColuna4 = 0;
        double somaColuna5 = 0;
        double mediaColuna1 = 0;
        double mediaColuna2 = 0;
        double mediaColuna3 = 0;
        double mediaColuna4 = 0;
        double mediaColuna5 = 0;
        double total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                somaColuna1 += elementoNumerico(linha, coluna1);
                somaColuna2 += elementoNumerico(linha, coluna2);
                total++;
            }
        }

        if (total != 0){
            mediaColuna1 = somaColuna1/total;
            mediaColuna2 = somaColuna2/total;

            for (int linha = 0; linha < tamanho; linha++) {
                if (this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                    vetorLinha = new Double[5];
                    vetorLinha[0] = elementoNumerico(linha, coluna1);
                    vetorLinha[1] = elementoNumerico(linha, coluna2);

                    vetorLinha[2] = Math.pow(vetorLinha[0] - mediaColuna1, 2);
                    somaColuna3 += vetorLinha[2];
                    vetorLinha[3] = Math.pow(vetorLinha[1] - mediaColuna2, 2);
                    somaColuna4 += vetorLinha[3];

                    vetorLinha[4] = (vetorLinha[0] - mediaColuna1)*(vetorLinha[1] - mediaColuna2);
                    somaColuna5 += vetorLinha[4];
                    coeficiente.add(vetorLinha);

                }
            }
            vetorLinha = new Double[5];
            mediaColuna3 = somaColuna3/total;
            mediaColuna4 = somaColuna4/total;
            mediaColuna5 = somaColuna5/total;

            vetorLinha[0] = mediaColuna1;
            vetorLinha[1] = mediaColuna2;
            vetorLinha[2] = mediaColuna3;
            vetorLinha[3] = mediaColuna4;
            vetorLinha[4] = mediaColuna5;
            coeficiente.add(vetorLinha);

            return coeficiente;
        }
        return null;
    }

    public ArrayList<Double[]> coeficiente(int colunaFixa, String nome, int coluna1, int coluna2){
        ArrayList<Double[]> coeficiente = new ArrayList<>();
        Double[] vetorLinha;
        int tamanho = this.getNumeroLinhas();
        double somaColuna1 = 0;
        double somaColuna2 = 0;
        double somaColuna3 = 0;
        double somaColuna4 = 0;
        double somaColuna5 = 0;
        double mediaColuna1 = 0;
        double mediaColuna2 = 0;
        double mediaColuna3 = 0;
        double mediaColuna4 = 0;
        double mediaColuna5 = 0;
        double total = 0;

        for (int linha = 0; linha < tamanho; linha++) {
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                somaColuna1 += elementoNumerico(linha, coluna1);
                somaColuna2 += elementoNumerico(linha, coluna2);
                total++;
            }
        }
        if (total != 0){

            mediaColuna1 = somaColuna1/total;
            mediaColuna2 = somaColuna2/total;

            for (int linha = 0; linha < tamanho; linha++) {
                if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna1) && this.eNumerico(linha, coluna2)) {
                    vetorLinha = new Double[5];
                    vetorLinha[0] = elementoNumerico(linha, coluna1);
                    vetorLinha[1] = elementoNumerico(linha, coluna2);

                    vetorLinha[2] = Math.pow(vetorLinha[0] - mediaColuna1, 2);
                    somaColuna3 += vetorLinha[2];
                    vetorLinha[3] = Math.pow(vetorLinha[1] - mediaColuna2, 2);
                    somaColuna4 += vetorLinha[3];

                    vetorLinha[4] = (vetorLinha[0] - mediaColuna1)*(vetorLinha[1] - mediaColuna2);
                    somaColuna5 += vetorLinha[4];
                    coeficiente.add(vetorLinha);

                }
            }
            vetorLinha = new Double[5];
            mediaColuna3 = somaColuna3/total;
            mediaColuna4 = somaColuna4/total;
            mediaColuna5 = somaColuna5/total;

            vetorLinha[0] = mediaColuna1;
            vetorLinha[1] = mediaColuna2;
            vetorLinha[2] = mediaColuna3;
            vetorLinha[3] = mediaColuna4;
            vetorLinha[4] = mediaColuna5;
            coeficiente.add(vetorLinha);

            return coeficiente;
        }
        return null;
    }

    public String[][] contingencia(int coluna1, int coluna2){
        String[] chaves1 = elementosSemRepeticao(coluna1);
        String[] chaves2 = elementosSemRepeticao(coluna2);
        HashMap<String, HashMap<String, Integer>> frequencia = new HashMap<>();
        HashMap<String, Integer> lista;
        int tamanho1 = 0;
        int tamanho2 = 0;

        for(int indice1 = 0; indice1 < chaves1.length; indice1++) {
            lista = new HashMap<>();
            for (int indice2 = 0; indice2 < chaves2.length; indice2++){
                lista.put(chaves2[indice2], 0);
            }
            frequencia.put(chaves1[indice1], lista);
        }

        for (int linha = 0; linha < getNumeroLinhas(); linha++){
            int valor = frequencia.get(getElemento(linha, coluna1)).get(getElemento(linha, coluna2))+1;
            HashMap<String, Integer> auxiliar = frequencia.get(getElemento(linha, coluna1));
            auxiliar.put(getElemento(linha, coluna2), valor);
            frequencia.put(getElemento(linha, coluna1), auxiliar);
        }


        String[][] resultado = new String[chaves1.length][];
        for (int indice1 = 0; indice1 < chaves1.length; indice1++) {
            String[] auxiliar = new String[chaves2.length+1];
            auxiliar[0] = chaves1[indice1];
            for (int indice2 = 0; indice2 < chaves2.length; indice2++){
                auxiliar[indice2+1] = String.valueOf(frequencia.get(chaves1[indice1]).get(chaves2[indice2]));
            }
            resultado[indice1] = auxiliar;
        }
        return resultado;
    }

    public String[][] contingencia(int colunaFixa, String nome, int coluna1, int coluna2){
        String[] chaves1 = elementosSemRepeticao(coluna1);
        String[] chaves2 = elementosSemRepeticao(coluna2);
        HashMap<String, HashMap<String, Integer>> frequencia = new HashMap<>();
        HashMap<String, Integer> lista;
        int tamanho1 = 0;
        int tamanho2 = 0;

        for(int indice1 = 0; indice1 < chaves1.length; indice1++) {
            lista = new HashMap<>();
            for (int indice2 = 0; indice2 < chaves2.length; indice2++){
                lista.put(chaves2[indice2], 0);
            }
            frequencia.put(chaves1[indice1], lista);
        }

        for (int linha = 0; linha < getNumeroLinhas(); linha++){
            if (getElemento(linha, colunaFixa).matches(nome)){
                int valor = frequencia.get(getElemento(linha, coluna1)).get(getElemento(linha, coluna2))+1;
                HashMap<String, Integer> auxiliar = frequencia.get(getElemento(linha, coluna1));
                auxiliar.put(getElemento(linha, coluna2), valor);
                frequencia.put(getElemento(linha, coluna1), auxiliar);
            }
        }


        String[][] resultado = new String[chaves1.length][];
        for (int indice1 = 0; indice1 < chaves1.length; indice1++) {
            String[] auxiliar = new String[chaves2.length+1];
            auxiliar[0] = chaves1[indice1];
            for (int indice2 = 0; indice2 < chaves2.length; indice2++){
                auxiliar[indice2+1] = String.valueOf(frequencia.get(chaves1[indice1]).get(chaves2[indice2]));
            }
            resultado[indice1] = auxiliar;
        }
        return resultado;
    }

    public double[][] histograma(int colunaFixa, String nome, int coluna){
        String[] valores = getElementosColuna(coluna);
        double[][] resultado = new double[3][];
        double[] auxiliar = new  double[valores.length];
        double minimo = 0;
        double maximo = 0;
        int tamanho = 0;
        for (int linha = 0; linha < valores.length; linha++){
            if (this.getElemento(linha, colunaFixa).matches(nome) && this.eNumerico(linha, coluna)){
                auxiliar[tamanho] = elementoNumerico(linha, coluna);
                if (tamanho == 0){
                    minimo = auxiliar[tamanho];
                    maximo = auxiliar[tamanho];
                } else {
                    if (auxiliar[tamanho] > maximo) maximo = auxiliar[tamanho];
                    else if (auxiliar[tamanho] < minimo) minimo = auxiliar[tamanho];
                }
                tamanho++;
            }
        }

        if (tamanho == 0) return null;
        double[] extremos = new double[2];
        extremos[0] = minimo;
        extremos[1] = maximo;

        double[] tam = new double[1];
        tam[0] = tamanho;

        resultado[0] = auxiliar;
        resultado[1] = tam;
        resultado[2] = extremos;
        return resultado;
    }

    public String[][] scatterplot(int colunaFixa, String nome, int coluna1, int coluna2){
        String[][] resultado = new String[3][];
        String elementosColuna1Fixo[] = getElementosColuna(coluna1);
        String elementosColuna2Fixo[] = getElementosColuna(coluna2);

        String[] coluna1Certo = new String[elementosColuna1Fixo.length];
        String[] coluna2Certo = new String[elementosColuna2Fixo.length];
        int cont = 0;
        for (int linha = 0; linha < elementosColuna1Fixo.length; linha++){
            if (this.getElemento(linha, colunaFixa).matches(nome)){
                coluna1Certo[cont] = elementosColuna1Fixo[linha];
                coluna2Certo[cont] = elementosColuna2Fixo[linha];
                cont++;
            }
        }

        if (cont == 0) return null;

        String[] tamanho = new String[1];
        tamanho[0] = String.valueOf(cont);

        resultado[0] = coluna1Certo;
        resultado[1] = coluna2Certo;
        resultado[2] = tamanho;
        return resultado;
    }

}
