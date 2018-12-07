import visao.janela.Janela;
import visao.janela.PainelInicial;
import visao.layout.Fontes;
import modelo.Conexao;
import modelo.Executar;
import modelo.Conexao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class Main{
    public static void main(String args[]) {
/*
        Conexao c = new Conexao();
        String up = "insert into Gravadora values(?, ?, ?)";

        String gravadoraNome = "Gravadora ";
        String gravadoraSite = "Site ";

        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setString(2, gravadoraNome+i);
                    s.setString(3, gravadoraSite+i);
                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }


        int gravadoraTel = 10000000;
        String tipo = "Fixo";
        up = "insert into Telefones values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, gravadoraTel+i);
                    s.setString(3, tipo);
                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into Composicao values(?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setString(1, "Alguam descriçção qualuwer"+i);
                    s.setInt(2, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }


        up = "insert into interprete values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setString(2, "Alguam tipo qualuwer"+i);
                    s.setString(3, "Nome "+i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into periodo values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, 100+i);
                    s.setString(3, "Descrição qualquer "+i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into compositor values(?, ?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setString(1, "Nome "+i);
                    s.setInt(2, i);

                    s.setDate(3, new Date(1992, 11, 12));
                    s.setDate(4, new Date(1910, 11, 12));

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into album values(?, ?, ?, ?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setString(2, "Descriçaõ qualquer do album "+i);
                    s.setInt(3, i);
                    s.setBigDecimal(4, new BigDecimal("0"));

                    s.setDate(5, new Date(1992, 11, 12));
                    s.setString(6, "Fisico");

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }

        }
        up = "insert into CompositorPeriodo values(?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, i);
                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();


            } catch (Exception e) {
                System.out.println(e);
            }
    }

        up = "insert into faixa values(?, ?, ?, ?, ?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setString(2, "Descriçaõ qualquer da faixa  "+i);
                    s.setString(3, "DDD");
                    s.setInt(4, i);
                    s.setInt(5, i);
                    s.setInt(6, i*10);
                    s.setString(7, "DDD");

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into albumGravadora values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setDate(1, new Date(1994, 10, 1));
                    s.setInt(2, i);
                    s.setInt(3, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }


        up = "insert into Albumfaixa values(?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into faixainterprete values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, i);
                    s.setInt(3, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }


        up = "insert into albumfaixaplaylist values(?, ?, ?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setDate(2, new Date(1994, 03, 16));
                    s.setInt(3, i);
                    s.setInt(4, i);
                    s.setInt(5, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        up = "insert into faixaCompositor values(?, ?, ?)";
        for (int i = 0; i < 10; i++){
            try {
                c.abrirConexao();
                if (c.conectou()){
                    PreparedStatement s = c.getC().prepareStatement(up);
                    s.setInt(1, i);
                    s.setInt(2, i);
                    s.setInt(3, i);

                    s.executeUpdate();
                    c.fecharConexao();
                    s.close();

                }
                if (c.conectou()) c.fecharConexao();
            } catch (Exception e) {
                System.out.println(e);
            }

        }

*/

        System.setProperty("file.encoding", "UTF-8");

        Janela janela = new Janela();
        janela.conteudoJanela(new PainelInicial(janela));
        janela.exibirJanela();

        Fontes.setFonte();


    }

}
