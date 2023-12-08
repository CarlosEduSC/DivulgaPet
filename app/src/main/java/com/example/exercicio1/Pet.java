package com.example.exercicio1;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    public enum Tipo{
        Cachorro,
        Gato
    }
    public enum FaixaEtaria {
        meses05("0 a 5 meses"),
        meses611("6 a 11 meses"),
        anos12("1 a 2 anos"),
        anos35("3 a 5 anos"),
        anos610("6 a 10 anos"),
        mais10("mais de 10 anos");

        private final String descricao;

        FaixaEtaria(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum RacaCachorro {
        viraLata("Vira-lata"),
        poodle("Poodle"),
        lhasaApso("Lhasa Apso"),
        pug("Pug"),
        pitbull("Pitbull"),
        rottweiler("Rottweiler"),
        outro("Outro");

        private final String descricao;

        RacaCachorro(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum RacaGato {
        viraLata("Vira-lata"),
        persa("Persa"),
        siames("Siamês"),
        angora("Angorá"),
        maineCoon("Maine Coon"),
        outro("Outro");

        private final String descricao;

        RacaGato(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum Sexo{
        Macho,
        Fêmea
    }

    public enum Porte{
        Pequeno,
        Medio,
        Grande
    }

    private String id;
    private String nome;
    private String tipo;
    private String faixaEtaria;
    private String raca;

    private String sexo;

    private String porte;
    private String catracao;

    private ArrayList<String> vacinas;
    private String descricao;
    private String idUsuario;
    private ArrayList<String> interessados;

    private String foto;

    public Pet(String nome, String tipo, String faixaEtaria, String raca, String sexo, String idUsuario, String foto) {
        this.nome = nome;
        this.tipo = tipo;
        this.faixaEtaria = faixaEtaria;
        this.raca = raca;
        this.sexo = sexo;
        this.idUsuario = idUsuario;
        this.foto = foto;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPorte() {
        if (porte == null) {
            return "Não informado";
        } else {
            return porte;
        }
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getCatracao() {
        if (catracao == null) {
            return "Não";
        } else {
            return catracao;
        }
    }

    public void setCatracao(String catracao) {
        this.catracao = catracao;
    }

    public ArrayList<String> getVacinas() {
        if (vacinas == null) {
            ArrayList<String> resposta = new ArrayList<String>();
            resposta.add("Não informadas");
            return resposta;
        } else {
            return vacinas;
        }
    }

    public void setVacinas(ArrayList<String> vacinas) {
        this.vacinas = vacinas;
    }

    public String getDescricao() {
        if (descricao == null || descricao == "") {
            return "Não informada";
        } else {
            return descricao;
        }
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdUsuario() {return idUsuario;}

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<String> getInteressados() {
        return interessados;
    }

    public void setInteressados(ArrayList<String> interessados) {
        this.interessados = interessados;
    }

    public String getFoto() {return foto;}

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
