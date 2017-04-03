package com.josecm.mvpdagger2example.main.view;

import com.josecm.mvpdagger2example.model.Tarea;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public interface AdminView {

    void showNoFields();
    void showError();
    void showItsOk(Tarea tarea,int idUsuario);
}
