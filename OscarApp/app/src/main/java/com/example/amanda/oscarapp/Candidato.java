package com.example.amanda.oscarapp;

import java.io.Serializable;

/**
 * Created by Amanda on 10/06/2016.
 */
public class Candidato implements Serializable {
    private String nome;
    private String genero;
    private String linkFoto;
    private int id;
    //private Bitmap imgBitmap;


    public Candidato(String nome, String genero, String linkFoto, int id) /*throws IOException*/ {
        this.nome = nome;
        this.genero = genero;
        this.linkFoto = linkFoto;
        this.id = id;
        //this.imgBitmap = new AuxiliarImg().baixarImagem(linkFoto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLinkFoto() {
        return linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}