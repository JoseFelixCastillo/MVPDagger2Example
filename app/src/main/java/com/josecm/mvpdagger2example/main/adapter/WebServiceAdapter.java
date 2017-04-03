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
import com.josecm.mvpdagger2example.model.WebServiceObject;
import com.josecm.mvpdagger2example.net.WebService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JoseFelix on 27/03/2017.
 */

public class WebServiceAdapter extends RecyclerView.Adapter<WebServiceAdapter.WebServiceViewHolder>{

    private List<WebServiceObject> objects;


    @Inject
    public WebServiceAdapter(){
    }

    public void onInitialize(List<WebServiceObject> objects){
        this.objects = objects;
    }
    @Override
    public WebServiceAdapter.WebServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_service_item,parent,false);

        return new WebServiceViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(WebServiceViewHolder holder, int position) {
        holder.onBind(objects.get(position));
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class WebServiceViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_zipcode;
        private TextView tv_item;
        private TextView tv_farmerId;
        private TextView tv_category;

        public WebServiceViewHolder(View itemView) {
            super(itemView);

            tv_zipcode = (TextView) itemView.findViewById(R.id.text_view_zipcode_recycler_web);
            tv_item = (TextView) itemView.findViewById(R.id.text_view_item_recycler_web);
            tv_farmerId = (TextView) itemView.findViewById(R.id.text_view_farmerid_recycler_web);
            tv_category = (TextView) itemView.findViewById(R.id.text_view_category_recycler_web);

        }

        public void onBind(final WebServiceObject object){

            tv_zipcode.setText(object.getZipcode());
            tv_item.setText(object.getItem());
            tv_farmerId.setText(object.getFarmer_id());
            tv_category.setText(object.getCategory());

        }
    }


}
