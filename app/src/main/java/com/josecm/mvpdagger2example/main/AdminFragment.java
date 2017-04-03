package com.josecm.mvpdagger2example.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.josecm.mvpdagger2example.R;
import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.login.LoginActivity;
import com.josecm.mvpdagger2example.main.presenter.AdminPresenter;
import com.josecm.mvpdagger2example.main.view.AdminView;
import com.josecm.mvpdagger2example.model.Tarea;

import java.util.zip.Inflater;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment implements AdminView{

    @Inject
    AdminPresenter adminPresenter;

    @BindView(R.id.input_descripcion)
    TextInputEditText input_descripcion;

    @BindView(R.id.input_duracion)
    TextInputEditText input_duracion;

    @BindView(R.id.input_tipo)
    TextInputEditText input_tipo;

    @OnClick(R.id.btn_createTarea)
    void createTarea(){
        adminPresenter.createTarea(input_descripcion.getText().toString(),
                input_duracion.getText().toString(),input_tipo.getText().toString());
    }

    public AdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin,container,false);

        ((MyApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this,view);
        return view;
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminPresenter.onInitialize(this);
    }

    @Override
    public void showNoFields() {
        Toast.makeText(getActivity(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(),"Error insertando la tarea",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showItsOk(Tarea tarea, int idUsuario) {
        Toast.makeText(getActivity(),"Se insertó correctamente la tarea: " + tarea.getTipo() + " al usuario con id: " + idUsuario,Toast.LENGTH_LONG).show();
    }


}
