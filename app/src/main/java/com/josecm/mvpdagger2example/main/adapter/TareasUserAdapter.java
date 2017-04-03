package com.josecm.mvpdagger2example.main.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josecm.mvpdagger2example.R;
import com.josecm.mvpdagger2example.model.Tarea;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 23/03/2017.
 */

public class TareasUserAdapter extends RecyclerView.Adapter<TareasUserAdapter.TareasUserViewHolder>{

    private List<Tarea> tareas;
    private OnItemClickListener listener;
    private Context context;


    public interface OnItemClickListener{
        void onItemClick(View view, Tarea tarea);
    }

    @Inject
    public TareasUserAdapter(Context context){
        this.context = context;
    }

    public void onInitialize(List<Tarea> tareas, OnItemClickListener listener){
        this.tareas = tareas;
        this.listener = listener;
    }
    @Override
    public TareasUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tareas_user_item,parent,false);

        return new TareasUserViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(TareasUserViewHolder holder, int position) {

        holder.onBind(tareas.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public class TareasUserViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_tipo;
        private TextView tv_duracion;
        private AppCompatButton btn_delete;

        public TareasUserViewHolder(View itemView,Context context) {
            super(itemView);

            tv_tipo = (TextView) itemView.findViewById(R.id.text_view_tipo_item_recicler_view);
            tv_duracion = (TextView) itemView.findViewById(R.id.text_view_duracion_item_recicler_view);
            btn_delete = (AppCompatButton) itemView.findViewById(R.id.btn_borrar);

        }

        public void onBind(final Tarea tarea, final OnItemClickListener listener){

            tv_tipo.setText(tarea.getTipo().name());
            tv_duracion.setText(String.format("%d", tarea.getDuracion()));

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemView,tarea);
                }
            });
        }
    }


}
