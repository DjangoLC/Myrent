package com.example.enrique.myrent;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Enrique on 23/03/2017.
 */

public class Adapter extends ArrayAdapter<Habitacion> {

    Context Mycontext;
    int LayoutResourceId;
    ArrayList<Habitacion> myList = null;
    DatosHolder holder = null;


    public Adapter(Context context, int layoutResourceId, ArrayList<Habitacion> list) {
        super(context,layoutResourceId,list);
        this.Mycontext = context;
        this.LayoutResourceId = layoutResourceId;
        this.myList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;


        if (v == null)
        {
            LayoutInflater layoutInflater = ((Activity)Mycontext).getLayoutInflater();
            v = layoutInflater.inflate(LayoutResourceId, parent, false);

            holder = new DatosHolder();
       //     holder.imageView = (ImageView)v.findViewById(R.id.imageView2);
            holder.imageView2 = (SubsamplingScaleImageView)v.findViewById(R.id.imageView2);
            holder.titulo = (TextView)v.findViewById(R.id.txtTitulo);
            holder.ubicacion = (TextView)v.findViewById(R.id.txtUbicacion);
            holder.numero = (TextView)v.findViewById(R.id.txtNumber);
            v.setTag(holder);
        }
        else
        {
            holder = (DatosHolder)v.getTag();

        }

         Habitacion  Habitacion = myList.get(position);
        holder.titulo.setText(Habitacion.getTitulo());
        holder.ubicacion.setText(Habitacion.getDir());
        holder.numero.setText(Habitacion.getNumero());

if (Habitacion.getImagen().equals("")){
    holder.imageView2.setImage(ImageSource.resource(R.drawable.loaderror).dimensions(200,200));
}
       else {
    holder.imageView2.setImage(ImageSource.uri(Habitacion.getImagen()));
    holder.imageView2.isQuickScaleEnabled();
   // holder.imageView2.setMinimumWidth(500);
}


        return v;
    }

    Bitmap resize(String file,int width,int height){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bit= BitmapFactory.decodeFile(file, options);

        int h=options.outHeight;
        int w=options.outWidth;

        int scaleFactor =2;
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


    static class DatosHolder
    {
     //   ImageView imageView;
        SubsamplingScaleImageView imageView2;
        TextView titulo;
        TextView ubicacion;
        TextView numero;
    }


}
