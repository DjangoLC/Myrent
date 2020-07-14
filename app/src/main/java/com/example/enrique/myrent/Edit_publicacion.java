package com.example.enrique.myrent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Edit_publicacion extends AppCompatActivity {
    private Bd bd;

    TextInputLayout tituloH;
    TextInputLayout telH;
    TextInputLayout dirH;
    TextInputLayout descH;
    TextInputLayout precioH;
    ImageView imagen;
    Habitacion a;

    //CAPTURAR IMAGEN
    int PICK_IMAGE_REQUEST = 1;
    ImageView img1;
    Uri imgUri;
    String newFoto="";
    String idAux ="";
String oldImg="";

    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private ImageView tomar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publicacion);
        bd = new Bd(getApplicationContext());


        tomar = (ImageView)findViewById(R.id.imageView6);
        //Si no existe crea la carpeta donde se guardaran las fotos
        file.mkdirs();

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = ruta_fotos + getCode() + ".png";
                newFoto =file;
                System.out.println(newFoto);
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



        //Recibir y guardar variables del objeto
        Bundle bundle = getIntent().getExtras();
         a = new Habitacion();
        a.setTitulo(bundle.get("titulo").toString());
        a.setDir(bundle.get("dir").toString());
        a.setNumero(bundle.get("tel").toString());
        a.setDesc(bundle.get("des").toString());
        a.setPrecio(bundle.get("precio").toString());
        a.setImagen(bundle.get("imagen").toString());
        oldImg = String.valueOf(bundle.get("imagen".toString()));
        System.out.println("OldImg: "+oldImg);

         tituloH = (TextInputLayout)findViewById(R.id.LayautNombreH);
         telH = (TextInputLayout)findViewById(R.id.LayautTelH);
         dirH = (TextInputLayout)findViewById(R.id.LayautDirH);
         descH = (TextInputLayout)findViewById(R.id.LayoutDescH);
         precioH = (TextInputLayout)findViewById(R.id.LayautPrecioeH);
         imagen = (ImageView)findViewById(R.id.imageView6);

        tituloH.getEditText().setText(a.getTitulo().toString());
        telH.getEditText().setText(a.getNumero().toString());
        dirH.getEditText().setText(a.getDir().toString());
        descH.getEditText().setText(a.getDesc().toString());
        precioH.getEditText().setText(a.getPrecio().toString());

        //  Picasso.with(getContext()).load(new File(img)).error(R.drawable.bin).into(imagen);
        if (a.getImagen().equals(""))
        {
            Picasso.with(getApplicationContext().getApplicationContext())
                    .load(new File(a.getImagen())).error(R.drawable.loaderror).into(imagen);
        }
        else
        {
            imagen.setImageBitmap(shrinkmethod(a.getImagen(),80,80));
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save:

                     idAux = bd.idPubli(a.getImagen());
                System.out.println("Imagen extraida desde el objeto: "+idAux);
                //Si se edita y no se toma una foto nueva marca eeror y no asigna foto

                if (newFoto.equals("")){
                    newFoto =oldImg;
                }
                if (validarDatos(tituloH,telH,a.getTitulo(),a.getNumero()))
                {
                    bd.actualizarPubli(
                            tituloH.getEditText().getText().toString(),
                            dirH.getEditText().getText().toString(),
                            telH.getEditText().getText().toString(),
                            precioH.getEditText().getText().toString(),
                            descH.getEditText().getText().toString(),
                            newFoto,
                            Integer.parseInt(idAux)

                    );
                    finish();
                }


                break;
        }



        return super.onOptionsItemSelected(item);
    }

    Bitmap shrinkmethod(String file, int width, int height){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bit= BitmapFactory.decodeFile(file, options);

        int h=options.outHeight;
        int w=options.outWidth;

        int scaleFactor = 8;
        if (width >0 || (height >0))
        {
            scaleFactor = Math.min(w/width,h/height);
        }
        options.inJustDecodeBounds=false;
        options.inSampleSize = scaleFactor;

        bit=BitmapFactory.decodeFile(file, options);
        //  Bitmap bm = null;
        //  bit.compress(Bitmap.CompressFormat.PNG,60,null);
        Bitmap resize = Bitmap.createScaledBitmap(bit,width,height,true);


        return resize;

    }

    @SuppressLint("SimpleDateFormat")
    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        return photoCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            setPhoto(tomar, String.valueOf(newFoto));
        }
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



    private boolean esNombreValido(String nombre,TextInputLayout titulo) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            titulo.setError("Titulo inválido");
            return false;
        } else {
            titulo.setError(null);
        }

        return true;
    }

    private boolean esTelefonoValido(String telefono,TextInputLayout tel) {
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


    private boolean  validarDatos(TextInputLayout titulo,TextInputLayout tel,String nombre, String telefono) {
         nombre = titulo.getEditText().getText().toString();
         telefono = tel.getEditText().getText().toString();


        boolean a = esNombreValido(nombre,titulo);
        boolean b = esTelefonoValido(telefono,tel);


        if (a && b) {
            // OK, se pasa a la siguiente acción
            Toast.makeText(getApplicationContext(), "Se guarda el registro hete", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}
