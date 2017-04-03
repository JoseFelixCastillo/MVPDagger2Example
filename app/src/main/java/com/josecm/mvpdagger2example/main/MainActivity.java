package com.josecm.mvpdagger2example.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.josecm.mvpdagger2example.R;
import com.josecm.mvpdagger2example.Utils;
import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.login.LoginActivity;
import com.josecm.mvpdagger2example.main.adapter.TareasUserAdapter;
import com.josecm.mvpdagger2example.main.adapter.WebServiceAdapter;
import com.josecm.mvpdagger2example.main.presenter.MainPresenter;
import com.josecm.mvpdagger2example.main.view.MainView;
import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.model.WebServiceObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView{

    @Inject
    MainPresenter mainPresenter;


    @BindView(R.id.recycler_view_web_service)
    RecyclerView recyclerView;

    @BindView(R.id.tv_usuario)
    TextView tv_usuario;

    @OnClick(R.id.image_log_out)
    void logOut(){
        //Aqui se deslogearia pero como al principio no compruebo que no hay alguien logueado pues lo omito
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_call_service)
    void callService(){
        if(Utils.isNetworkConnected(getApplicationContext())) {
            mainPresenter.callService();
        }
        else{
            mainPresenter.callServiceNoNetwork();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication)getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this);

        mainPresenter.onInitialize(this,((MyApplication)getApplication()));
        mainPresenter.initViewFromUser();

        tv_usuario.setText(MyApplication.getUsuario().getNombre());
        setupRecyclerView();

    }

    private void setupRecyclerView(){

        // recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_favourite);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void goToAdmin() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AdminFragment adminFragment = AdminFragment.newInstance();
        ft.replace(R.id.main_frame, adminFragment);
        ft.commit();
    }

    @Override
    public void goToTecnic() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TecnicFragment tecnicFragment = TecnicFragment.newInstance();
        ft.replace(R.id.main_frame, tecnicFragment);
        ft.commit();
    }

    @Override
    public void setData(ArrayList<WebServiceObject> body) {

        WebServiceAdapter webServiceAdapter = new WebServiceAdapter();
        webServiceAdapter.onInitialize(body);
        recyclerView.setAdapter(webServiceAdapter);

    }

    @Override
    public void showNoConexion() {
        Toast.makeText(MainActivity.this,"Error de conexion",Toast.LENGTH_LONG).show();
    }
}
