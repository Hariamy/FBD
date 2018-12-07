package modelo.tabela;

public class Faixa {
    String descricao;
    String tipoComposicao;
    int compositor;
    int album;
    int tempo;
    int numero;
    String tipoGravacao;

    public void setAlbum(int album) { this.album = album; }
    public void setTempo(int tempo) { this.tempo = tempo; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCompositor(int compositor) { this.compositor = compositor; }
    public void setTipoGravacao(String tipoGravacao) { this.tipoGravacao = tipoGravacao; }
    public void setTipoComposicao(String tipoComposicao) { this.tipoComposicao = tipoComposicao; }

    public int getAlbum() { return album; }
    public int getTempo() { return tempo; }
    public int getNumero() { return numero; }
    public int getCompositor() { return compositor; }
    public String getDescricao() { return descricao; }
    public String getTipoGravacao() { return tipoGravacao; }
    public String getTipoComposicao() { return tipoComposicao; }

}
