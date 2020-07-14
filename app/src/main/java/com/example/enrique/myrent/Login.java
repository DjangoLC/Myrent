package com.example.enrique.myrent;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


    private Bd j ;
    private String UserN,PassN;

    private ArrayList<String> USUARIOS = new ArrayList();
    private  ArrayList<String> PASS = new ArrayList();
    private RelativeLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        coordinatorLayout = (RelativeLayout) findViewById(R.id
                .activity_login);

       j= new Bd(getApplicationContext());

        Button iniciar = (Button)findViewById(R.id.button3);
        TextView registro = (TextView)findViewById(R.id.txtRegistro) ;
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //**final TextInputLayout usuario = (TextInputLayout) findViewById(R.id.txtuser);
                final    TextInputLayout pass = (TextInputLayout) findViewById(R.id.getPass);
                final    TextInputLayout user = (TextInputLayout) findViewById(R.id.getUser);
                UserN = user.getEditText().getText().toString();
                PassN = pass.getEditText().getText().toString();
                USUARIOS = (ArrayList<String>) j.ConsultaGenerica("usuario","nombre");
                PASS = (ArrayList<String>) j.ConsultaGenerica("usuario","pass");

                if (UserN.equals("")|| PassN.equals("")){
                    Snackbar.make(coordinatorLayout, "Datos invalidos", Snackbar.LENGTH_LONG)
                            .show();

                }

                for (int a = 0; a<USUARIOS.size();a++)
                {
                    if (UserN.equals(USUARIOS.get(a)) && PassN.equals(PASS.get(a)))
                    {
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }

                }





            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registro_user.class));
            }
        });
    }
}
