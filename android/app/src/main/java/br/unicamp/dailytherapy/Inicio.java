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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inicio extends AppCompatActivity {

    ImageView imgLogo;
    Button btnCadastro, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        //imgLogo.setImageResource(R.drawable.DailyTherapy);

        btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnLogin    = (Button) findViewById(R.id.btnLogin);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,Cadastro.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}