package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 12/06/2017.
 */


import java.io.Serializable;

/**
 * Created by Amanda on 10/06/2016.
 */
public class Diretor implements Serializable {
    private String nome;
    private int id;


    public Diretor(String nome, int id) /*throws IOException*/ {
        this.nome = nome;
        this.id = id;
        //this.imgBitmap = new AuxiliarImg().baixarImagem(linkFoto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}