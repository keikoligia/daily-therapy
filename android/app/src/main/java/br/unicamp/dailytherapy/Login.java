package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText edtUsuario, edtSenha;
    Button btnEntrar;
    Intent intent;
    private Session session;

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
                String nome = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();
                getUser(nome, senha);
            }
        });
    }

    private void setUserSession(Usuario nome)
    {
        session = new Session(this); //in oncreate
        session.setUseName(nome.getNome());
    }

    private void getUser(String nome, String senha)
    {
        try
        {
            Usuario usuario = new Usuario();
            usuario.setSenha(edtSenha.getText().toString());
            usuario.setNome(edtUsuario.getText().toString());

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Usuario> call = service.getUsuarioNome(usuario);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response)
                {
                    if(response.isSuccessful())
                    {
                        Usuario user = (Usuario) response.body();

                        if(user != null)
                        {
                            if(user.getSenha().equals(senha) && user.getNome().equals(nome))
                            {
                                setUserSession(user);
                                intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Login.this, "Senha invalida", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(Login.this, "Usuario inexistente!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String erro = jObjError.getJSONObject("error").getString("message");
                            Toast.makeText(Login.this, erro, Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<Usuario> call, Throwable t)
                {
                    String messageProblem = t.getMessage().toString();
                    Toast.makeText(Login.this, messageProblem, Toast.LENGTH_LONG).show();
                    Toast.makeText(Login.this, "Erro ao realizar login", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception erro){
            System.err.println(erro);
            erro.printStackTrace();
        }
    }
}