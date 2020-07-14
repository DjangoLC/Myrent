package com.example.enrique.myrent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Mis_publicaciones extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    static ArrayList<Habitacion> myData = new ArrayList<>();
    static ListView listView;

    ArrayList<String> myDataStringTitulo ;
    ArrayList<String> myDataStringTelefono ;
    ArrayList<String> myDataStringDir ;
    ArrayList<String> myDataStringDesc ;
    ArrayList<String> myDataStringPrecio ;
    ArrayList<String> myDataStringFoto;
    ArrayList<String> myDataClear;
    String dirImg ="";
    int count=0;
    private Bd j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_publicaciones);

        j = new Bd(getApplicationContext());
        refresh();
        myDataStringFoto = (ArrayList<String>) j.getFoto();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 dirImg = myDataStringFoto.get(position);
                System.out.println(dirImg);
                creralert(dirImg);
                count = position;
            }
        });

    }


    @Override
    public void onRefresh() {

        myData.clear();

        refresh();



    }


    public void creralert(final String direFoto){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Atención");
        dialogo1.setMessage("¿Que acción deseas realizar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent intent = new Intent(Mis_publicaciones.this,Edit_publicacion.class);
                intent.putExtra("titulo",myDataStringTitulo.get(count));
                intent.putExtra("tel",myDataStringTelefono.get(count));
                intent.putExtra("dir",myDataStringDir.get(count));
                intent.putExtra("des",myDataStringDesc.get(count));
                intent.putExtra("precio",myDataStringPrecio.get(count));
                intent.putExtra("imagen",myDataStringFoto.get(count));
                System.out.println("Direccion enviada: "+myDataStringFoto.get(count));
                startActivityForResult(intent,0);

            }
        });
        dialogo1.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Context context =  getApplicationContext();
                final Bd j = new Bd(context.getApplicationContext());


                String idPubli = j.idPubli(direFoto);
                int i = Integer.parseInt(idPubli);
                j.borrarPubli(i);
                dialogo1.dismiss();
                refresh();
            }
        });
        dialogo1.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogo1.show();
    }


    public void refresh()
    {


        myData.clear();
        myDataStringDesc = (ArrayList<String>) j.getDesc();
        myDataStringPrecio = (ArrayList<String>) j.getPrecio();
        myDataStringTitulo = (ArrayList<String>) j.consultarTitulo();
        myDataStringTelefono = (ArrayList<String>) j.consultarHabitacionTel();
        myDataStringDir = (ArrayList<String>) j.consultarHabitacionDir();
        myDataStringFoto = (ArrayList<String>) j.getFoto();

        for(int a=0;a<myDataStringTelefono.size();a++)
        {
            Habitacion hb =new Habitacion();
            hb.setTitulo(myDataStringTitulo.get(a));
            hb.setNumero(myDataStringTelefono.get(a));
            hb.setDir(myDataStringDir.get(a));
            hb.setImagen(myDataStringFoto.get(a));
            System.out.println(a);
            System.out.println("Foto extraida:"+myDataStringFoto.get(a));
            myData.add(hb);


        }

        Adapter adapter = new Adapter(this,R.layout.layout_item_list,myData);
        listView = (ListView)findViewById(R.id.listviewPub);
        listView.setAdapter(adapter);
        // Limpiando dsatos

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }
}
