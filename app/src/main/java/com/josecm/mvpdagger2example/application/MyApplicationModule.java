package com.josecm.mvpdagger2example.application;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.net.WebService;
import com.josecm.mvpdagger2example.storage.UserLocalStorage;
import com.josecm.mvpdagger2example.storage.UsuarioDBHelper;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JoseFelix on 21/03/2017.
 */
@Module
public class MyApplicationModule {


    private final Application mApplication;

    public MyApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    UserLocalStorage provideUserLocalStorage(UsuarioDBHelper usuarioDBHelper){
        return usuarioDBHelper;
    }

    @Provides
    public Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://data.ct.gov/")
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    WebService providesGitHubInterface(Retrofit retrofit) {
        return retrofit.create(WebService.class);
    }

}
