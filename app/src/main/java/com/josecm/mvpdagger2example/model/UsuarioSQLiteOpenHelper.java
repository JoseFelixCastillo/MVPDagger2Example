package com.josecm.mvpdagger2example.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JoseFelix on 21/03/2017.
 */

public class UsuarioSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "usuarioDataBase.db";
    public static final int DATABASE_VERSION = 22;
    public static final String DATABASE_TABLE = "usuarioTable";
    public static final String DATABASE_TABLE2 = "tareaTable";
    public static final String DATABASE_TABLE3 = "webServiceTable";

    public static final String KEY_ID = "id";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_TIPO = "tipo";
    public static final String KEY_HABILIDADES = "habilidades";

    //tarea
    public static final String KEY_DESCRIPCION = "descripcion";
    public static final String KEY_DURACION = "duracion";
    public static final String KEY_ID_USUARIO = "id_usuario";

    //webService
    public static final String KEY_ZIPCODE = "zip_code";
    public static final String KEY_ITEM = "item";
    public static final String KEY_FARMERID = "farmer_id";
    public static final String KEY_CATEGORY = "category";

    //SQL statement for create table costume  //Comprobar que sea con mayusculas
    String costumeSqlCreate = "CREATE TABLE " + DATABASE_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NOMBRE + " TEXT unique, "
            + KEY_TIPO + " TEXT, "
            + KEY_HABILIDADES + " TEXT) ";

    String costumeSqlCreate2 = "CREATE TABLE " + DATABASE_TABLE2 + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TIPO + " TEXT, "
            + KEY_DESCRIPCION + " TEXT unique, "
            + KEY_DURACION + " INTEGER, "
            + KEY_ID_USUARIO + " INTEGER, "
            + "FOREIGN KEY(" + KEY_ID_USUARIO + ") REFERENCES usuarioTable(id) ON DELETE CASCADE) ";

    String costumeSqlCreate3 = "CREATE TABLE " + DATABASE_TABLE3 + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ZIPCODE + " TEXT unique, "
            + KEY_ITEM + " TEXT, "
            + KEY_FARMERID + " TEXT, "
            + KEY_CATEGORY + " TEXT) ";

    public UsuarioSQLiteOpenHelper(Context context){
        this(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private UsuarioSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(costumeSqlCreate);
        sqLiteDatabase.execSQL(costumeSqlCreate2);
        sqLiteDatabase.execSQL(costumeSqlCreate3);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);

        sqLiteDatabase.execSQL(costumeSqlCreate);
        sqLiteDatabase.execSQL(costumeSqlCreate2);
        sqLiteDatabase.execSQL(costumeSqlCreate3);

    }
}
