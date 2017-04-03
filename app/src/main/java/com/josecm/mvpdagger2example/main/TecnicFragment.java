package com.josecm.mvpdagger2example.main;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.josecm.mvpdagger2example.R;
import com.josecm.mvpdagger2example.application.MyApplication;
import com.josecm.mvpdagger2example.main.adapter.TareasUserAdapter;
import com.josecm.mvpdagger2example.main.presenter.TecnicPresenter;
import com.josecm.mvpdagger2example.main.view.TecnicView;
import com.josecm.mvpdagger2example.model.Tarea;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TecnicFragment extends Fragment implements TecnicView, TareasUserAdapter.OnItemClickListener{

    @BindView(R.id.recycler_view_tecnic)
    RecyclerView recyclerView;

    @Inject
    TecnicPresenter tecnicPresenter;

    public TecnicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tecnic,container,false);

        ((MyApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this,view);
        tecnicPresenter.onInitialize(this,((MyApplication)getActivity().getApplication()));

        return view;
    }

    public static TecnicFragment newInstance() {
        TecnicFragment fragment = new  TecnicFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            // loadUI();

            setupRecyclerView();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        drawList();

    }

    private void setupRecyclerView(){

        // recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_favourite);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void drawList(){
        final List<Tarea> tareas = tecnicPresenter.getTareas();
        TareasUserAdapter tareasUserAdapter = null;
        if(!tareas.isEmpty()){
            tareasUserAdapter = new TareasUserAdapter(getContext());
            tareasUserAdapter.onInitialize(tareas,this);
        }

        if(tareasUserAdapter!=null) {
            recyclerView.setAdapter(tareasUserAdapter);
        }
    }

    @Override
    public void showDeleteTarea() {
        Toast.makeText(getActivity(),"Se borro la tarea",Toast.LENGTH_LONG).show();
    }

    @Override
    public void reloadScreen() {
        drawList();
    }

    @Override
    public void showErrorDeletingTarea() {
        Toast.makeText(getActivity(),"Error borrando la tarea",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, Tarea tarea) {
        tecnicPresenter.removeTarea(tarea);
    }
}
