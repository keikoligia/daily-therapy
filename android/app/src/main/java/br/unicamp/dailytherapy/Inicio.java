package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {

    ImageView imgLogo;
    TextView txtCadastro, txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        //imgLogo.setImageResource(R.drawable.DailyTherapy);

        txtCadastro = (TextView) findViewById(R.id.txtCadastro);
        txtLogin    = (TextView) findViewById(R.id.txtLogin);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,Cadastro.class);
                startActivity(intent);
                finish();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}