package modelo;

public class Gravadora extends Executar{
    private int codigo;
    private String nome;
    private String site;

    public void setCodigo(int codigo) { this.codigo = codigo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSite(String site) { this.site = site; }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getSite() { return site; }

    public boolean inserir(){
        String valores = "insert into Gravadora values(" + codigo + ", '" + nome + "', '" + site + "')";
        return update(valores);
    }
    public boolean remover(){
        String valores = "delete from Gravadora where codigo = " + codigo;
        return update(valores);
    }

}
