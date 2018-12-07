package modelo.tabela;

import modelo.Executar;

import java.sql.Date;
import java.util.ArrayList;

public class Album {
    private int codigo;
    private int gravadora;
    private float preco;

    private String descricao;
    private Date dataCompra;
    private String tipo;

    private ArrayList<Faixa> faixas;

    private boolean exibicao = false;

    public Album(){}

    public int getCodigo() { return codigo; }
    public boolean getExibicao() { return exibicao; }
    public String getDescricao() { return descricao; }
    public float getPreco() { return preco; }
    public int getGravadora() { return gravadora; }
    public Date getDataCompra() { return dataCompra; }

    public void setExibicao() {
        if (exibicao) exibicao = false;
        else exibicao = true;
    }
    public void setDataCompra(Date dataCompra) { this.dataCompra = dataCompra; }
    public void setGravadora(int gravadora) { this.gravadora = gravadora; }
    public void setPreco(float preco) { this.preco = preco; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setFaixas(ArrayList<Faixa> faixas) { this.faixas = faixas; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }


    public ArrayList<Faixa> getFaixas() {
        Executar e = new Executar();
        return e.selectFaixas(codigo);
    }
}
