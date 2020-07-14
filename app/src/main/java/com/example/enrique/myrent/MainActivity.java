package com.example.enrique.myrent;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {


    static ArrayList<Habitacion> myData = new ArrayList<>();
     static ListView listView;
    private SwipeRefreshLayout refreshLayout;
    ArrayList<String> myDataStringTitulo ;
    ArrayList<String> myDataStringTelefono ;
    ArrayList<String> myDataStringDir ;
    ArrayList<String> myDataStringDesc ;
    ArrayList<String> myDataStringPrecio ;
    ArrayList<String> myDataStringFoto;
    ArrayList<String> myDataClear;
    String titulo;
    String telefono;
    String dir ;
    String desc ;
    String precio ;
    String img ;



    ImageView ima ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.content_main);
        refreshLayout.setOnRefreshListener(this);
         ima = (ImageView)findViewById(R.id.imageView2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.pencil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //CODE HERE...
        //Llena ListView
         refresh();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Obtener la posiciond de la lista y comparar con la posicion del arreglo
                 titulo = myDataStringTitulo.get(position).toString();
                 telefono = myDataStringTelefono.get(position).toString();
                 dir = myDataStringDir.get(position).toString();
                 desc = myDataStringDesc.get(position).toString();
                precio = myDataStringPrecio.get(position).toString();
                img = myDataStringFoto.get(position).toString();
                System.out.println("Titulo: "+titulo);
                System.out.println("direccion: "+dir);
                System.out.println("Telefono: "+telefono);
                System.out.println("Desc:" +desc);
                System.out.println("precio:" +precio);
                System.out.println("Img: " +precio);

                showDtealle();




            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final Bd j = new Bd(getApplicationContext());

        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this,Mis_publicaciones.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void show()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentHabitacionAdd newFragment = new FragmentHabitacionAdd();


        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }

    public void showDtealle()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HabitacionDetalle newFragment = new HabitacionDetalle();


        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();

        Bundle b = new Bundle();
        b.putString("titulo",titulo);
        b.putString("dir",dir);
        b.putString("tel",telefono);
        b.putString("desc",desc);
        b.putString("precio",precio);
        b.putString("foto",img);

        newFragment.setArguments(b);

    }



    @Override
    public void onRefresh() {

        listView.setAdapter(null);


        myData.clear();

        refresh();

        refreshLayout.setRefreshing(false);

    }


    public void refresh()
    {
        final Bd j = new Bd(getApplicationContext());

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
        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        // Limpiando dsatos

    }








}
