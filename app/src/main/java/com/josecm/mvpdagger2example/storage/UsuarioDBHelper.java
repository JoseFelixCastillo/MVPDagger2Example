package com.josecm.mvpdagger2example.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.josecm.mvpdagger2example.Utils;
import com.josecm.mvpdagger2example.model.Tarea;
import com.josecm.mvpdagger2example.model.Usuario;
import com.josecm.mvpdagger2example.model.UsuarioSQLiteOpenHelper;
import com.josecm.mvpdagger2example.model.WebServiceObject;
import com.josecm.mvpdagger2example.model.enums.ListaTareasUsuario;
import com.josecm.mvpdagger2example.model.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import static com.josecm.mvpdagger2example.model.UsuarioSQLiteOpenHelper.KEY_TIPO;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class UsuarioDBHelper implements UserLocalStorage {

    private final static String TAG = UsuarioDBHelper.class.getSimpleName();

    private SQLiteDatabase db;


    @Inject
    public UsuarioDBHelper(Context context) {
        UsuarioSQLiteOpenHelper sqLiteOpenHelper = new UsuarioSQLiteOpenHelper(context);

        db = sqLiteOpenHelper.getWritableDatabase();

    }

    @Override
    public ArrayList<Usuario> getUsuarios() {
        String[] campos = new String[]{UsuarioSQLiteOpenHelper.KEY_ID,UsuarioSQLiteOpenHelper.KEY_NOMBRE,
                KEY_TIPO,UsuarioSQLiteOpenHelper.KEY_HABILIDADES};
        Cursor c = db.query(UsuarioSQLiteOpenHelper.DATABASE_TABLE, campos, null, null, null, null,
                null);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                int id = c.getInt(0);
                String nombre = c.getString(1);
                String tipo = c.getString(2);
                String habilidades = c.getString(3);

                Usuario usuario;
                if(TipoUsuario.valueOf(tipo).equals(TipoUsuario.ADMIN)) {
                    usuario = new Usuario(nombre,id, TipoUsuario.valueOf(tipo));
                }
                else{
                    usuario = new Usuario(nombre, id, TipoUsuario.valueOf(tipo), Utils.convertStringToArray(habilidades));
                }
                usuarios.add(usuario);

            } while (c.moveToNext());
        }
        c.close();

        return usuarios;
    }

    @Override
    public long saveUsuario(Usuario usuario) {
        ContentValues newUser = new ContentValues();
        newUser.put(UsuarioSQLiteOpenHelper.KEY_NOMBRE, usuario.getNombre());
        newUser.put(KEY_TIPO, usuario.getTipo().name());
        if(usuario.getTipo().equals(TipoUsuario.TECNICO)) {
            newUser.put(UsuarioSQLiteOpenHelper.KEY_HABILIDADES, Utils.convertArrayEnumToString(usuario.getHabilidades()));
        }


        long rows = db.insert(UsuarioSQLiteOpenHelper.DATABASE_TABLE, null, newUser);

        return rows;
    }

    @Override
    public long saveTarea(Tarea tarea, int idUsuario) {
        ContentValues newCostume = new ContentValues();
        newCostume.put(UsuarioSQLiteOpenHelper.KEY_TIPO, tarea.getTipo().name());
        newCostume.put(UsuarioSQLiteOpenHelper.KEY_DESCRIPCION, tarea.getDescripcion());
        newCostume.put(UsuarioSQLiteOpenHelper.KEY_DURACION, tarea.getDuracion());

        newCostume.put(UsuarioSQLiteOpenHelper.KEY_ID_USUARIO, idUsuario);

        long rows = db.insert(UsuarioSQLiteOpenHelper.DATABASE_TABLE2, null, newCostume);
        Log.d("LLEGO TAREAS","rows insertadas: " + rows);
        return rows;

    }

    @Override
    public ArrayList<Tarea> getTareasFromUser(int idUsuario){
        String select = "SELECT *" + " FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE2 +
                " WHERE " + UsuarioSQLiteOpenHelper.KEY_ID_USUARIO + "=" + idUsuario;
        Cursor c = db.rawQuery(select,null);


        ArrayList<Tarea> tareas = new ArrayList<>();
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                int id = c.getInt(0);
                String tipo = c.getString(1);
                String descripcion = c.getString(2);
                int duracion = c.getInt(3);

                Tarea tarea;

                tarea = new Tarea(id,ListaTareasUsuario.valueOf(tipo),descripcion,duracion);
                tareas.add(tarea);

            } while (c.moveToNext());
        }
        c.close();

        return tareas;
    }

    @Override
    public Usuario getBestUserForTarea(ListaTareasUsuario listaTarea) {
        //Buscamos los usuarios que tengan la habilidad
        Map<Integer, Integer> map = new HashMap<>();
        String select = "SELECT " + UsuarioSQLiteOpenHelper.KEY_ID  + " FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE +
               " WHERE " + UsuarioSQLiteOpenHelper.KEY_HABILIDADES + " LIKE " + "'%" + listaTarea + "%'";

        Cursor c = db.rawQuery(select,null);

        //Ahora tengo los ids de los usuarios que pueden hacerlo y sumo las duraciones de las tareas
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                int amountDuration;
                String select2 = "SELECT SUM(" + UsuarioSQLiteOpenHelper.KEY_DURACION + ") AS amountDuration" + " FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE2 +
                        " WHERE " + UsuarioSQLiteOpenHelper.KEY_ID_USUARIO + "=" + id;
                Cursor c2 = db.rawQuery(select2,null);

                if(c2.moveToFirst()){
                    do{
                        int sum = c2.getInt(0);
                        map.put(id,sum);
                        Log.d(TAG, "MAP: " + map.keySet().toArray()[0]);
                        Log.d(TAG, "sum es: " + sum);
                    } while(c2.moveToNext());
                }
                c2.close();

            } while (c.moveToNext());

        }
        c.close();
       // Log.d(TAG, "MAP VALOR ANTES: " + map.values().toArray()[0] + " Usuario id: " + map.keySet().toArray()[0]);
        map = Utils.sortByValue(map);
       // Log.d(TAG, "MAP VALOR DESPUES: " + map.values().toArray()[0] + " Usuario id: " + map.keySet().toArray()[0]);
        //map.keySet().toArray()[0]

        return getUser((int)map.keySet().toArray()[0]);
    }

    @Override
    public Usuario getUser(int idUsuario) {
        String select = "SELECT * FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE + " WHERE " + UsuarioSQLiteOpenHelper.KEY_ID +
                "=" + idUsuario;

        Cursor c = db.rawQuery(select,null);
        Usuario usuario = null;
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                int id = c.getInt(0);
                String nombre = c.getString(1);
                String tipo = c.getString(2);
                String habilidades = c.getString(3);

                if (TipoUsuario.valueOf(tipo).equals(TipoUsuario.ADMIN)) {
                    usuario = new Usuario(nombre, id, TipoUsuario.valueOf(tipo));
                } else {
                    usuario = new Usuario(nombre, id, TipoUsuario.valueOf(tipo), Utils.convertStringToArray(habilidades));
                }
            } while (c.moveToNext());
        }
        c.close();
        return usuario;
    }

    @Override
    public int deleteTarea(int idTarea){

       // String delete = "DELETE * FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE2 + " WHERE " + UsuarioSQLiteOpenHelper.KEY_ID  + "=" + idTarea;
        int rows = db.delete(UsuarioSQLiteOpenHelper.DATABASE_TABLE2, UsuarioSQLiteOpenHelper.KEY_ID + " = ?",new String[]{ Integer.valueOf(idTarea).toString()});

        return rows;
    }

    @Override
    public long saveWebService(WebServiceObject webServiceObject) {
        ContentValues newObject = new ContentValues();
        newObject.put(UsuarioSQLiteOpenHelper.KEY_ZIPCODE, webServiceObject.getZipcode());
        newObject.put(UsuarioSQLiteOpenHelper.KEY_ITEM, webServiceObject.getItem());
        newObject.put(UsuarioSQLiteOpenHelper.KEY_FARMERID, webServiceObject.getFarmer_id());
        newObject.put(UsuarioSQLiteOpenHelper.KEY_CATEGORY, webServiceObject.getCategory());

        long rows = db.insert(UsuarioSQLiteOpenHelper.DATABASE_TABLE3, null, newObject);

        return rows;
    }

    @Override
    public ArrayList<WebServiceObject> getWebServiceObjects() {
        String select = "SELECT *" + " FROM " + UsuarioSQLiteOpenHelper.DATABASE_TABLE3;

        Cursor c = db.rawQuery(select,null);

        ArrayList<WebServiceObject> objects = new ArrayList<>();
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                int id = c.getInt(0);
                String zipcode = c.getString(1);
                String item = c.getString(2);
                String farmer_id = c.getString(3);
                String category = c.getString(4);

                WebServiceObject object;

                object = new WebServiceObject(zipcode,item,farmer_id,category);
                objects.add(object);

            } while (c.moveToNext());
        }
        c.close();

        return objects;
    }


}
