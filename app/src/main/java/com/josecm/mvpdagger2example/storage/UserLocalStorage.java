package com.josecm.mvpdagger2example.storage;

import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.WebServiceObject;
import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;

import java.util.ArrayList;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public interface UserLocalStorage {

    public ArrayList<Usuario> getUsuarios();
    public long saveUsuario(Usuario usuario);

    public long saveTarea(Tarea tarea, int idUsuario);
    public ArrayList<Tarea> getTareasFromUser(int idUsuario);
    public Usuario getBestUserForTarea(ListaTareasUsuario listaTarea);
    public Usuario getUser(int idUsuario);
    public int deleteTarea(int idTarea);

    public long saveWebService(WebServiceObject webServiceObject);
    public ArrayList<WebServiceObject> getWebServiceObjects();
}
