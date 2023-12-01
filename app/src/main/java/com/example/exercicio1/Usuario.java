package com.example.exercicio1;

public class Usuario {
    private String id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String rg;
    private String cep;
    private String senha;
    private String rua;
    private int numero = 0;
    private String bairro;
    private String cidade;
    private String complemento;

    public Usuario(String nome, String dataNascimento, String telefone, String email, String senha) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        if (cpf == null || cpf == "") {
            cpf = "Não informado";
        }
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        if (rg == null || rg == "") {
            rg = "Não informado";
        }
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCep() {
        if (cep == null || cep == "") {
            cep = "Não informado";
        }
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRua() {
        if (rua == null || rua == "") {
            rua = "Não informada";
        }
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        if (bairro == null || bairro == "") {
            bairro = "Não informado";
        }
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        if (cidade == null || cidade == "") {
            cidade = "Não informada";
        }
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        if (complemento == null || complemento == "") {
            complemento = "Não informado";
        }
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
