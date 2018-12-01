package modelo;
import java.sql.Date;

public class Playlist extends Executar{
    private int id;
    private String nome;
    private Date date;

    public void setNome(String nome) { this.nome = nome; }
    public void setDate(Date date) { this.date = date; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public Date getDate() { return date; }
    public int getId() { return id; }

    public boolean inserir(){
        return false;
    }
}
