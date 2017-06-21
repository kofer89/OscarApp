package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 10/06/2017.
 */


import java.io.Serializable;

/**
 * Created by Ricardo on 17/11/2016.
 */

public class Usuario implements Serializable {
    private String nome;
    private String senha;
    private int usuario;
    private int votou;

    public Usuario(String nome, String senha, int usuario, int votou) {
        this.nome = nome;
        this.senha = senha;
        this.usuario = usuario;
        this.votou = votou;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getVotou() {
        return votou;
    }

    public void setVotou(int votou) {
        this.votou = votou;
    }
}
