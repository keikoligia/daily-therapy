package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import br.unicamp.dailytherapy.TratamentoErros.TrataErro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {

    EditText edtEmail, edtCttEmergencia, edtNomeCtt, edtNomeUsuario, edtSenha;
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
        edtNomeCtt       = (EditText) findViewById(R.id.edtNomeCtt);

        btnEntrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                inserirUsuario();
            }
        });
    }

    private void inserirUsuario()
    {
        try
        {
            String email         = edtEmail.getText().toString();
            String cttEmergencia = edtCttEmergencia.getText().toString();
            String nomeCtt = edtNomeCtt.getText().toString();
            String nomeUsuario   = edtNomeUsuario.getText().toString();
            String senha         = edtSenha.getText().toString();

            Usuario user = new Usuario(nomeUsuario, cttEmergencia, nomeCtt, email, senha);

            user.setNome(nomeUsuario);
            user.setSenha(senha);

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Usuario> call = service.incluirUsuario(user);

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful())
                        {
                            Usuario user = response.body();
                            Intent intent = new Intent(Cadastro.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            try
                            {
                                Gson gson = new Gson();
                                TrataErro erro = gson.fromJson(response.errorBody().string(), TrataErro.class);

                                Toast.makeText(Cadastro.this, "toast1: "+erro.getError(), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception err)
                            {
                                Toast.makeText(Cadastro.this, "toast2: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(Cadastro.this, "Erro ao fazer cadastro", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Cadastro.this, "toast3: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
        catch (Exception err)
        {
            Toast.makeText(Cadastro.this, "toast4: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}