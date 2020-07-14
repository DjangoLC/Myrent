package com.example.enrique.myrent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Enrique on 24/03/2017.
 */

public class HabitacionDetalle extends DialogFragment {

    String titulo="";
    String dir="";
    String tel="";
    String desc="";
    String precio="";
    String img="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_detalle_item, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Detalle habitacion");

         titulo =getArguments().getString("titulo");
         dir =getArguments().getString("dir");
         tel =getArguments().getString("tel");
         desc =getArguments().getString("desc");
        precio = getArguments().getString("precio");
        img = getArguments().getString("foto");

        System.out.println("titulo recibido: "+titulo);
        System.out.println("direccion recibida: "+dir);
        System.out.println("telefono recibido: "+tel);
        System.out.println("descripcion recibida: "+desc);
        System.out.println("precio recibido: "+precio);
        System.out.println("foto recibido: "+img);



        TextView tituloH = (TextView)view.findViewById(R.id.txtTitulo);
        TextView telH = (TextView)view.findViewById(R.id.txtTelefono);
        TextView dirH = (TextView)view.findViewById(R.id.txtUbicacion);
        TextView descH = (TextView)view.findViewById(R.id.txtDescripcion);
        TextView precioH = (TextView)view.findViewById(R.id.txtPrecio);
        final SubsamplingScaleImageView imagen = (SubsamplingScaleImageView)view.findViewById(R.id.imageView3) ;

        tituloH.setText(titulo);
        telH.setText(tel);
        dirH.setText(dir);
        descH.setText(desc);
        precioH.setText(precio);
      //  Picasso.with(getContext()).load(new File(img)).error(R.drawable.bin).into(imagen);
        if (img.equals(""))
        {
           imagen.setImage(ImageSource.resource(R.drawable.loaderror).dimensions(200,200));
        }
        else
        {
            imagen.setImage(ImageSource.bitmap(shrinkmethod(img,200,200)));
         //   imagen.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_FIXED);
        }
imagen.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
      //      actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_info_details);

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
        inflater.inflate(R.menu.menu_cerrar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View view = getView();


        switch (item.getItemId()){


            case android.R.id.home:
                System.out.println("si jala");
                 titulo ="";
                 dir ="";
                 tel ="";
                 desc ="";
                precio="";
                dismiss();
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

        int scaleFactor = 4;
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


}
