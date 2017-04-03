package com.josecm.mvpdagger2example.login;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Toast;

import com.josecm.mvpdagger2example.R;
import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.btn_login)
    AppCompatButton btn_login;

    @BindView(R.id.input_id)
    TextInputEditText input_id;

    @OnClick(R.id.btn_login)
    void pushBtn_login(){
        loginPresenter.checkUser(input_id.getText().toString());
    }

    //El password no lo cojo puesto que no lo voy a utilizar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((MyApplication)getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this);

        loginPresenter.onInitialize(this,((MyApplication)getApplication()));
    }

    @Override
    public void goToNextActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showNoUser() {
        Toast.makeText(LoginActivity.this,"Escriba su id",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorUser() {
        Toast.makeText(LoginActivity.this,"Usuario erroneo",Toast.LENGTH_LONG).show();
    }
}
