package com.example.enrique.myrent;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Registro_user extends AppCompatActivity {

    private  Bd j =null;
    private String UserN,PassN;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_user);
         j = new Bd(getApplicationContext());
        final   TextInputLayout usuario = (TextInputLayout) findViewById(R.id.txtuser);
        final    TextInputLayout pass = (TextInputLayout) findViewById(R.id.txtpass);


        layout = (RelativeLayout) findViewById(R.id.activity_login_registro);



        Button reg = (Button)findViewById(R.id.button3);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserN =  usuario.getEditText().getText().toString();
                PassN =  pass.getEditText().getText().toString();

                if (UserN.equals("")  || PassN.equals(""))
                {
                   Snackbar snack=null;
                    snack.make(layout,"Debes llenar todos los campos",Snackbar.LENGTH_SHORT).show();

                }

                else
                {    j.addUser(UserN,PassN);
                    finish();
                }

            }
        });

    }
}
