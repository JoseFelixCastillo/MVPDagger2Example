package com.josecm.mvpdagger2example.application;

import android.app.Application;
import android.util.Log;

import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class MyApplication extends Application {

    @Inject
    MyApplicationPresenter myApplicationPresenter;

    private static Usuario usuario;

    private MyApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerMyApplicationComponent.builder()
                .myApplicationModule(new MyApplicationModule(this))
                .build();

        applicationComponent.inject(this);

        myApplicationPresenter.saveUsuarios();
    }

    public MyApplicationComponent getApplicationComponent(){
        return this.applicationComponent;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        MyApplication.usuario = usuario;
    }
}
