package com.josecm.mvpdagger2example.login;

import android.util.Log;

import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public class LoginPresenter {

    private UserLocalStorage userLocalStorage;
    private LoginView loginView;

    private MyApplication myApplication;

    @Inject
    public LoginPresenter(UserLocalStorage userLocalStorage){
        this.userLocalStorage = userLocalStorage;
    }

    public void onInitialize(LoginView loginView, MyApplication myApplication){
        this.loginView = loginView;
        this.myApplication = myApplication;
    }
    public void checkUser(String idUsuario){

        if(idUsuario.equals("")){
            loginView.showNoUser();
        }
        else {
            try {
                int id = Integer.parseInt(idUsuario);
                Usuario usuario = userLocalStorage.getUser(id);
                if (usuario != null) {
                    myApplication.setUsuario(usuario);
                    loginView.goToNextActivity();
                } else {
                    loginView.showErrorUser();
                }
            }catch (NumberFormatException e){
                Log.d("Error", e.toString());
            }
        }

    }
}
