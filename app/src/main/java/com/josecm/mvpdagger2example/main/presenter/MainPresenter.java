package com.josecm.mvpdagger2example.main.presenter;

import android.util.Log;

import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.main.view.MainView;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.WebServiceObject;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;
import com.josecm.mvpdagger2example.net.WebService;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public class MainPresenter {

    private UserLocalStorage userLocalStorage;
    private MyApplication myApplication;
    private MainView mainView;
    private WebService webService;

    @Inject
    public MainPresenter(UserLocalStorage userLocalStorage, WebService webService){
        this.userLocalStorage = userLocalStorage;
        this.webService = webService;
    }

    public void onInitialize(MainView mainView, MyApplication myApplication){
        this.myApplication = myApplication;
        this.mainView = mainView;
    }

    public void initViewFromUser(){
        Usuario usuario = myApplication.getUsuario();
        if(usuario.getTipo().equals(TipoUsuario.TECNICO)){
            mainView.goToTecnic();
        }
        else{
            mainView.goToAdmin();
        }
    }

    public void callService() {
        Call<ArrayList<WebServiceObject>> call = webService.getObjects();

        call.enqueue(new Callback<ArrayList<WebServiceObject>>() {
            @Override
            public void onResponse(Call<ArrayList<WebServiceObject>> call, Response<ArrayList<WebServiceObject>> response) {

                if(response.isSuccessful()){
                    for(WebServiceObject object : response.body()) {
                        userLocalStorage.saveWebService(object);
                    }
                    mainView.setData(response.body());
                }
                else{
                    mainView.showNoConexion();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WebServiceObject>> call, Throwable t) {
                mainView.showNoConexion();
            }
        });

    }

    public void callServiceNoNetwork() {
        ArrayList<WebServiceObject> objects = userLocalStorage.getWebServiceObjects();
        if(!objects.isEmpty()){
            mainView.setData(objects);
        }

    }
}

