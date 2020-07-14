package com.example.enrique.myrent;

/**
 * Created by Enrique on 23/03/2017.
 */

public class Habitacion {

    String imagen;
    String titulo;
    String desc;
    String dir;
    String numero;
    String precio;

    public Habitacion() {

    }


    public Habitacion(String imagen, String titulo, String desc, String dir, String numero, String precio) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.desc = desc;
        this.dir = dir;
        this.numero = numero;
        this.precio = precio;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
