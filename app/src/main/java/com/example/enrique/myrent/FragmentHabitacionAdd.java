package com.example.enrique.myrent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static android.R.attr.bitmap;
import static android.app.Activity.RESULT_OK;


/**
 * Created by Enrique on 23/03/2017.
 */

public class FragmentHabitacionAdd extends DialogFragment{


    TextInputLayout titulo;
    TextInputLayout dir;
    TextInputLayout tel;
    TextInputLayout desc;
    TextInputLayout precio;
     int PICK_IMAGE_REQUEST = 1;
    ImageView img1;
    Uri imgUri;
    String dataa="";





    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private ImageView tomar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_habitacion, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Nueva habitacion");
        Context context = getContext().getApplicationContext();

        //======== codigo nuevo ========
          tomar = (ImageView)view.findViewById(R.id.imageView6);
           //Si no existe crea la carpeta donde se guardaran las fotos
          file.mkdirs();



        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = ruta_fotos + getCode() + ".png";
                dataa =file;
                System.out.println(dataa);
                     File mi_foto = new File( file );
                     try {
                         mi_foto.createNewFile();

                     } catch (IOException ex) {
                                  Log.e("ERROR ", "Error:" + ex);
                                 }
                              //
                              Uri uri = Uri.fromFile( mi_foto );
                              //Abre la camara para tomar la foto
                              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                             //Guarda imagen
                              cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                             //Retorna a la actividad
                              startActivityForResult(cameraIntent, 0);
            }
        });



       /* Componentes(view);*/
         titulo = (TextInputLayout)view.findViewById(R.id.LayautNombreH);
         dir = (TextInputLayout)view.findViewById(R.id.LayautDirH);
         tel = (TextInputLayout)view.findViewById(R.id.LayautTelH);
         precio = (TextInputLayout)view.findViewById(R.id.LayautPrecioeH);
        desc = (TextInputLayout)view.findViewById(R.id.LayoutDescH);

        img1 = (ImageView)view.findViewById(R.id.imageView6);



        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        }

        setHasOptionsMenu(true);


        return view;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        return dialog;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View view = getView();
        Context context = getContext().getApplicationContext();
        Habitacion a = new Habitacion();
        switch (item.getItemId()){


            case R.id.save:
                String tituloH = titulo.getEditText().getText().toString();
                String telefonoH = tel.getEditText().getText().toString();
                String dirH = dir.getEditText().getText().toString();
                String descH = desc.getEditText().getText().toString();
                String precioH = precio.getEditText().getText().toString();

                System.out.println(validadTelefono(telefonoH));
                System.out.println(validarNombre(tituloH));
                final Bd j = new Bd(context.getApplicationContext());

                if (validarDatos())
                {
                    if (dataa.equals("")){
                       Toast.makeText(getContext(),"Debes tomar una foto",Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Entro el if");
                        j.addHabitacion(tituloH, precioH, dirH, telefonoH,dataa, descH);
                        dismiss();
                    }
                }



                break;


            case android.R.id.home:
                dismiss();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
      private String getCode()
      {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
           String date = dateFormat.format(new Date() );
           String photoCode = "pic_" + date;
           return photoCode;
          }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            titulo.setError("Titulo inválido");
            return false;
        } else {
            titulo.setError(null);
        }

        return true;
    }

    private boolean esTelefonoValido(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            tel.setError("Teléfono inválido");
            return false;
        } else {
            tel.setError(null);
        }

        return true;
    }

    private boolean validarNombre(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 30;
    }

    private boolean validadTelefono(String telefono) {
        return Patterns.PHONE.matcher(telefono).matches();
    }


    private boolean  validarDatos() {
        String nombre = titulo.getEditText().getText().toString();
        String telefono = tel.getEditText().getText().toString();


        boolean a = esNombreValido(nombre);
        boolean b = esTelefonoValido(telefono);


        if (a && b) {
            // OK, se pasa a la siguiente acción
           // Toast.makeText(getContext(), "Se guarda el registro hete", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }



    public void setPhoto(ImageView img1,String path)
    {
       try {
           BitmapFactory.Options options = new BitmapFactory.Options();
           options.inSampleSize = 8;
           img1.setImageBitmap(BitmapFactory.decodeFile(path, options));
           System.out.println(path);
           System.out.println("Se agrego foto");
       }
       catch (Exception e)
       {
           System.out.println("Error: "+e.getStackTrace());
       }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            setPhoto(tomar, String.valueOf(dataa));
        }
    }
}
