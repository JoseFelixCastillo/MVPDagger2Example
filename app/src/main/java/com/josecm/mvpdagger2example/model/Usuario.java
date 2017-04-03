package com.josecm.mvpdagger2example.model;

import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;

import java.util.ArrayList;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class Usuario {

    private String nombre;
    private int id;
    private TipoUsuario tipo;
    private ArrayList<ListaTareasUsuario> habilidades;

    public Usuario(String nombre, TipoUsuario tipoUsuario){
        this(nombre,0,tipoUsuario,null);
    }

    public Usuario(String nombre, int id, TipoUsuario tipoUsuario) {
        this(nombre,id,tipoUsuario,null);
    }

    public Usuario(String nombre, int id, TipoUsuario tipo, ArrayList<ListaTareasUsuario> habilidades) {
        this.nombre = nombre;
        this.id = id;
        this.tipo = tipo;
        this.habilidades = habilidades;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public ArrayList<ListaTareasUsuario> getHabilidades() {
        return habilidades;
    }

}

