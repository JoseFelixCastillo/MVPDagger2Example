package com.josecm.mvpdagger2example.application;

import android.util.Log;

import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class MyApplicationPresenter {

    private UserLocalStorage userLocalStorage;

    @Inject
    public MyApplicationPresenter(UserLocalStorage userLocalStorage) {
        this.userLocalStorage = userLocalStorage;
    }

    public void saveUsuarios(){

        for(Usuario user : userLocalStorage.getUsuarios()){
            Log.d("APP", user.getNombre() + " id: " + user.getId());
        }
        userLocalStorage.saveUsuario(new Usuario("LORENZO" , 0, TipoUsuario.ADMIN));
        userLocalStorage.saveUsuario(new Usuario("MARTA" , 0, TipoUsuario.ADMIN));
        ArrayList array1 = new ArrayList<>(Arrays.asList(ListaTareasUsuario.COBRAR,ListaTareasUsuario.ENVOLVER));
        ArrayList array2 = new ArrayList<>(Arrays.asList(ListaTareasUsuario.LIMPIAR,ListaTareasUsuario.ENVOLVER));
        ArrayList array3 = new ArrayList<>(Arrays.asList(ListaTareasUsuario.REPONEDOR_PRODUCTOS,ListaTareasUsuario.LIMPIAR));
        ArrayList array4 = new ArrayList<>(Arrays.asList(ListaTareasUsuario.COBRAR,ListaTareasUsuario.ENVOLVER));
        ArrayList array5 = new ArrayList<>(Arrays.asList(ListaTareasUsuario.COBRAR));
        userLocalStorage.saveUsuario(new Usuario("JUAN" , 0, TipoUsuario.TECNICO,array1));
        userLocalStorage.saveUsuario(new Usuario("JOSE" , 0, TipoUsuario.TECNICO,array2));
        userLocalStorage.saveUsuario(new Usuario("ANA" , 0, TipoUsuario.TECNICO,array3));
        userLocalStorage.saveUsuario(new Usuario("ALVARO" , 0, TipoUsuario.TECNICO,array4));
        userLocalStorage.saveUsuario(new Usuario("ÑOÑO" , 0, TipoUsuario.TECNICO,array5));
        userLocalStorage.saveUsuario(new Usuario("LUCIA" , 0, TipoUsuario.ADMIN));

        userLocalStorage.saveTarea(new Tarea(ListaTareasUsuario.ENVOLVER,"mucha",20),3);
        userLocalStorage.saveTarea(new Tarea(ListaTareasUsuario.COBRAR,"hello",200),3);
        userLocalStorage.saveTarea(new Tarea(ListaTareasUsuario.COBRAR,"hello",220),7);
        userLocalStorage.saveTarea(new Tarea(ListaTareasUsuario.LIMPIAR,"pon",200),4);

        for(Tarea tarea : userLocalStorage.getTareasFromUser(3)){
            Log.d("APP", tarea.getTipo() + " id: " + tarea.getId());
        }

        Log.d("MIRAMOS USER", "USER: " + userLocalStorage.getBestUserForTarea(ListaTareasUsuario.COBRAR).getNombre());


    }
}
