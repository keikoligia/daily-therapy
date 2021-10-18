package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLembrete, btnResumo, btnMensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLembrete = (Button) findViewById(R.id.btnLembrete);
        btnResumo = (Button) findViewById(R.id.btnResumo);
        btnMensagem = (Button) findViewById(R.id.btnMensagem);

        btnMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Chat.class);
                startActivity(intent);
                finish();
            }
        });

        btnResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Resumo.class);
                startActivity(intent);
                finish();
            }
        });

        btnLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Medicamento.class);
                startActivity(intent);
                finish();
            }
        });
    }


}