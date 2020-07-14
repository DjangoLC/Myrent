package com.example.enrique.myrent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Netoc_000 on 24/03/2017.
 */

public class Bd  extends SQLiteOpenHelper{
    public Bd(Context context) {
        super(context, Database_name, null, Database_version);
    }

    public static  final int Database_version = 1;
    public static  final String Database_name ="myRent.db";


    // TABLA HABITACION CLUMNA Y ATRIBUTOS
    public  static final String Tabla_Habitacion= "Habitacion";
    public static  final String columnaid= "_id";
    public static  final String columnatitulo = "titulo";
    public static final String colomnaprecio = "precio";
    public static  final String columnandir = "dir";
    public static final String columnatel = "tel";
    public static final String columnadesc = "descString";
    public static final String columnaImagen = "imagen";


    // TABLA USUARIO CLUMNA Y ATRIBUTOS
    public  static final String Tabla_USUARIO= "usuario";
    public static  final String columnaid_user= "_id";
    public static  final String columnanomre = "nombre";
    public static final String columnapass = "pass";




    // CREA TABLA HABITACION
    public static final String SQL_crear = "create table "+ Tabla_Habitacion +"("+ columnaid+ " integer primary key autoincrement,"+
            columnatitulo + " String not null"+","+ colomnaprecio +" String not null,"+ columnandir +" String not null,"
            + columnatel +" String not null ,"+ columnaImagen +" String not null ," + columnadesc +"String not null);" ;

    public static final String SQL_crear_usuario = "create table "+ Tabla_USUARIO +"("+ columnaid_user+ " integer primary key autoincrement,"+
            columnanomre + " String not null"+","+ columnapass +" String not null);" ;


    @Override
    public void onCreate(SQLiteDatabase db) {
        //ejecucuion para crear las tablas
        db.execSQL(SQL_crear);
        db.execSQL(SQL_crear_usuario);
        System.out.println("crando--");
        System.out.println(SQL_crear);
        System.out.println("creo--");
        System.out.println(SQL_crear_usuario);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void addHabitacion (String titulo, String precio,String dir,String tel,String imagen,String desc){



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(columnatitulo,titulo);
        values.put(colomnaprecio,precio);
        values.put(columnandir,dir);
        values.put(columnatel, tel);
        values.put(columnaImagen,imagen);
        values.put(columnadesc, desc);

        db.insert(Tabla_Habitacion, null, values);
        System.out.println("se agrego la habitacion");

        db.close();
    }
    public String idPubli(String foto){
        SQLiteDatabase db = this.getWritableDatabase();
        String[]projection = {columnaid, columnatitulo, colomnaprecio, columnandir,columnatel ,columnaImagen,columnadesc};

        Cursor cursor=
                db.query(Tabla_Habitacion,projection,"imagen = ?",
                        new String[]{String.valueOf(foto)},
                        null,
                        null,null,null);
        if(cursor != null ) {
            cursor.moveToFirst();
            db.close();


        }
        return cursor.getString(0);
    }


    public void actualizarPubli (String titulo,String dire,String tel,String precio,String desc,String foto, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(columnatitulo,titulo);
        values.put(colomnaprecio,precio);
        values.put(columnandir,dire);
        values.put(columnatel,tel);
        values.put(columnaImagen,foto);
        values.put(columnadesc,desc);

        db.update(Tabla_Habitacion,values,"_id = " + id ,null);

        System.out.println("se actualizo la habitacion");
    }


    public void borrarPubli(int id){

        SQLiteDatabase db = getReadableDatabase();
        db.delete(Tabla_Habitacion,"_id = " + id,null);

    }



    public boolean addUser (String nombre, String pass){



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(columnanomre,nombre);
            values.put(columnapass,pass);

            db.insert(Tabla_USUARIO, null, values);
            System.out.println("se agrego el usuario");
            db.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public List getGeneric(String tabla,String atributo)
    {
        SQLiteDatabase db = getReadableDatabase();

        List<String>lista = new ArrayList<>();
        Cursor cur = db.rawQuery("Select "+atributo+" from " + tabla,null);

        while(cur.moveToNext()){
            System.out.println(atributo+" Ext:"+cur.getString(1));
            lista.add(cur.getString(0));
        }
        cur.close();
        db.close();

        return (lista);
    }



    public List consultarTitulo(){

        SQLiteDatabase db = getReadableDatabase();
        String resultado = "";

        List<String>lista = new ArrayList<String>();
        Cursor cur = db.rawQuery("Select * from " + Tabla_Habitacion,null);

        while(cur.moveToNext()){

            System.out.println("Titulo Ext:"+cur.getString(1));
            lista.add(cur.getString(1));
        }
        cur.close();
        db.close();

        return (lista);
    }

    public List ConsultaGenerica(String tabla,String atributo){

        SQLiteDatabase db = getReadableDatabase();

        List<String>lista = new ArrayList<>();
        Cursor cur = db.rawQuery("Select "+atributo+" from " + tabla,null);

        while(cur.moveToNext()){

            System.out.println(atributo+" Ext:"+cur.getString(0));
            lista.add(cur.getString(0));
        }
        cur.close();
        db.close();

        return (lista);
    }


    public List consultarHabitacionTel(){

        SQLiteDatabase db = getReadableDatabase();
        String resultado = "";

        List<String>lista = new ArrayList<String>();
        Cursor cur = db.rawQuery("Select * from " + Tabla_Habitacion,null);

        while(cur.moveToNext()){

            System.out.println("Telefono Ext:"+cur.getString(4));
            lista.add(cur.getString(4));
        }
        cur.close();
        db.close();

        return (lista);
    }

    public List consultarHabitacionDir(){

        SQLiteDatabase db = getReadableDatabase();
        String resultado = "";

        List<String>lista = new ArrayList<String>();
        Cursor cur = db.rawQuery("Select * from " + Tabla_Habitacion,null);

        while(cur.moveToNext()){
            System.out.println("Direecion Ext:"+cur.getString(3));
            lista.add(cur.getString(3));
        }
        cur.close();
        db.close();

        return (lista);
    }


    public List getDesc() {


        SQLiteDatabase db = getReadableDatabase();
        String resultado = "";

        List<String>lista = new ArrayList<String>();
        Cursor cur = db.rawQuery("Select descString from " + Tabla_Habitacion,null);

        while(cur.moveToNext()){
            System.out.println("Desc Ext:"+cur.getString(0));
            lista.add(cur.getString(0));
        }
        cur.close();
        db.close();

        return (lista);

    }

    public List getPrecio() {
        SQLiteDatabase db = getReadableDatabase();


        List<String>lista = new ArrayList<String>();
       String sql = "Select precio from Habitacion";
        Cursor cur = db.rawQuery(sql,null);

        while(cur.moveToNext()){
            System.out.println("Precio Ext:"+cur.getString(0));
            lista.add(cur.getString(0));
        }
        cur.close();
        db.close();

        return (lista);
    }

    public List getFoto() {
        SQLiteDatabase db = getReadableDatabase();


        List<String>lista = new ArrayList<String>();
        String sql = "Select imagen from Habitacion";
        Cursor cur = db.rawQuery(sql,null);

        while(cur.moveToNext()){
            System.out.println("Foto Ext:"+cur.getString(0));
            lista.add(cur.getString(0));
        }
        cur.close();
        db.close();
        return (lista);

    }

    public boolean checaregistro()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + Tabla_Habitacion + " where " + "_id "+ " = " + 1;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            return false;
        }
        return true;
    }


}
