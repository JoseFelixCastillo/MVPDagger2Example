package com.josecm.mvpdagger2example.main.presenter;

import com.josecm.mvpdagger2example.main.view.AdminView;
import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public class AdminPresenter {

    private UserLocalStorage userLocalStorage;
    private AdminView adminView;

    @Inject
    public AdminPresenter(UserLocalStorage userLocalStorage){
        this.userLocalStorage = userLocalStorage;
    }


    public void onInitialize(AdminView adminView){
        this.adminView = adminView;
    }

    public void createTarea(String descripcion, String duracion, String tipo){

        if ((descripcion.equals("") || duracion.equals("") || tipo.equals(""))) {
            adminView.showNoFields();
        }
        else{
            Tarea tarea = new Tarea(ListaTareasUsuario.valueOf(tipo),descripcion,Integer.parseInt(duracion));
            Usuario usuario = userLocalStorage.getBestUserForTarea(ListaTareasUsuario.valueOf(tipo));
            long fields = userLocalStorage.saveTarea(tarea, usuario.getId());
            if(fields<0){
                adminView.showError();
            }
            else{
                adminView.showItsOk(tarea,usuario.getId());
            }
        }


    }
}
