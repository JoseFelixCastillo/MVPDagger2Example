package com.josecm.mvpdagger2example.main.view;

import com.josecm.mvpdagger2example.model.WebServiceObject;

import java.util.ArrayList;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public interface MainView {

    public void goToAdmin();
    public void goToTecnic();
    public void setData(ArrayList<WebServiceObject> body);
    void showNoConexion();
}
