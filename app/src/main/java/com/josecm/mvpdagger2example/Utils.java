package com.josecm.mvpdagger2example;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class Utils {

    public static String strSeparator = ",";

    public static String convertArrayEnumToString(ArrayList<ListaTareasUsuario> array){
        String str = "";
        for (int i = 0;i<array.size(); i++) {
            str = str+array.get(i);
            if(i<array.size()-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static ArrayList<ListaTareasUsuario> convertStringToArray(String str){
        List<String> arrayList = new ArrayList<>(Arrays.asList(str.split(strSeparator)));
        ArrayList<ListaTareasUsuario> enumArray = new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            enumArray.add(ListaTareasUsuario.valueOf(arrayList.get(i)));
        }
        return enumArray;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )  {
        List<Map.Entry<K, V>> list =  new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ){
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
