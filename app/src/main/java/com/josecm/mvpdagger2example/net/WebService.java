package com.josecm.mvpdagger2example.net;

import com.josecm.mvpdagger2example.model.WebServiceObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JoseFelix on 27/03/2017.
 */


public interface WebService {


    @GET("resource/hma6-9xbg.json?category=Fruit&item=Peaches")
    Call<ArrayList<WebServiceObject>> getObjects();

}
