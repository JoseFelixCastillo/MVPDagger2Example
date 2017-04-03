package com.josecm.mvpdagger2example.application;

import com.josecm.mvpdagger2example.login.LoginActivity;
import com.josecm.mvpdagger2example.main.AdminFragment;
import com.josecm.mvpdagger2example.main.MainActivity;
import com.josecm.mvpdagger2example.main.TecnicFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JoseFelix on 21/03/2017.
 */
@Singleton
@Component(modules = {MyApplicationModule.class})
public interface MyApplicationComponent {

    void inject(MyApplication myApplication);
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
    void inject(AdminFragment adminFragment);
    void inject(TecnicFragment tecnicFragment);

  //  LoginComponent plus(LoginModule loginModule);
}
