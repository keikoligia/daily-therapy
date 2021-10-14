package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cadastro extends AppCompatActivity {

    EditText edtEmail, edtCttEmergencia, edtNomeUsuario, edtSenha;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtEmail         = (EditText) findViewById(R.id.edtEmail);
        edtCttEmergencia = (EditText) findViewById(R.id.edtCttEmergencia);
        edtNomeUsuario   = (EditText) findViewById(R.id.edtNomeUsuario);
        edtSenha         = (EditText) findViewById(R.id.edtSenha);

        btnEntrar        = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String email         = edtEmail.getText().toString();
                String cttEmergencia = edtCttEmergencia.getText().toString();
                String nomeUsuario   = edtNomeUsuario.getText().toString();
                String senha         = edtSenha.getText().toString();

                //Manipular parametros
                Bundle parametro = new Bundle();

                parametro.putString("sessaoEmail", email);
                parametro.putString("sessaoCttEmergencia",cttEmergencia);
                parametro.putString("sessaoNomeUsuario", nomeUsuario);
                parametro.putString("sessaoSenha",senha);

                Intent intent = new Intent(Cadastro.this,Login.class);
                intent.putExtras(parametro);

                startActivity(intent);
                finish();
            }
        });
    }
}