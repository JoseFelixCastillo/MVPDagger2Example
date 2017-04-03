package com.josecm.mvpdagger2example.model;

import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;

import java.util.List;

/**
 * Created by JoseFelix on 22/03/2017.
 */

public class Tarea {

    private int id;
    private ListaTareasUsuario tipo;
    private String descripcion;
    private int duracion;       //horas

    public Tarea(ListaTareasUsuario tipo, String descripcion, int duracion) {
        this(0,tipo,descripcion,duracion);
    }

    public Tarea(int id, ListaTareasUsuario tipo, String descripcion, int duracion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.duracion = duracion;
    }

    public ListaTareasUsuario getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracion() {
        return duracion;
    }
}
