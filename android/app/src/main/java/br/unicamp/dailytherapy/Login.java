package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText edtUsuario, edtSenha;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha   = (EditText) findViewById(R.id.edtSenha);

        btnEntrar  = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String nomeUsuario = edtUsuario.getText().toString();
                String senha       = edtSenha.getText().toString();

                //Manipular parametros
                Bundle parametro = new Bundle();


                parametro.putString("sessaoNomeUsuario", nomeUsuario);
                parametro.putString("sessaoSenha",senha);

                Intent intent = new Intent(Login.this,MainActivity.class);
                intent.putExtras(parametro);

                startActivity(intent);
                finish();
            }
        });
    }
}