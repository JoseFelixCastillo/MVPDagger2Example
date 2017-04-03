package com.josecm.mvpdagger2example.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.main.adapter.TareasUserAdapter;
import com.josecm.mvpdagger2example.main.view.TecnicView;
import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public class TecnicPresenter{

    private TecnicView tecnicView;
    private UserLocalStorage userLocalStorage;
    private MyApplication myApplication;

    @Inject
    public TecnicPresenter(UserLocalStorage userLocalStorage){
        this.userLocalStorage = userLocalStorage;
    }


    public void onInitialize(TecnicView tecnicView, MyApplication myApplication){
        this.tecnicView = tecnicView;
        this.myApplication = myApplication;
    }

    public ArrayList<Tarea> getTareas(){
        return userLocalStorage.getTareasFromUser(myApplication.getUsuario().getId());
    }

    public void removeTarea(Tarea tarea){
        int rows = userLocalStorage.deleteTarea(tarea.getId());

        if(rows>0){
            tecnicView.showDeleteTarea();
            tecnicView.reloadScreen();

        }
        else{
            tecnicView.showErrorDeletingTarea();
        }
    }


}
