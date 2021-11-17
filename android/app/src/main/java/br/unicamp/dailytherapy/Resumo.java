package br.unicamp.dailytherapy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resumo extends AppCompatActivity
{
    TextView tvNomePaciente, tvNomeAcompanhante;
    GridView gvRemedios;
    private Session session;
    private List<Remedio> remedioList;
    private ResumoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        tvNomeAcompanhante = (TextView) findViewById(R.id.tvNomeAcompanhante);
        tvNomePaciente = (TextView) findViewById(R.id.tvNomePaciente);
        gvRemedios = (GridView) findViewById(R.id.gvRemedios);

        session = new Session(Resumo.this);
        String nomePaciente = session.getusename();
        String nomeContato = session.getCttNome();

        tvNomePaciente.setText(nomePaciente);
        tvNomeAcompanhante.setText(nomeContato);

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);

            Call<List<Remedio>> call = service.mostrarRemedio(nomePaciente);
            call.enqueue(new Callback<List<Remedio>>() {
                @Override
                public void onResponse(Call<List<Remedio>> call, Response<List<Remedio>> response)
                {
                    if(response.isSuccessful())
                    {
                        populateGridView(response.body());
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String erro = jObjError.getJSONObject("error").getString("message");
                            Toast.makeText(Resumo.this, erro, Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Resumo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Remedio>> call, Throwable t)
                {
                    String messageProblem = t.getMessage().toString();
                    Toast.makeText(Resumo.this, messageProblem, Toast.LENGTH_LONG).show();
                    Toast.makeText(Resumo.this, "Erro ao mostrar remédios", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void populateGridView(List<Remedio> listaDog){
        gvRemedios = (GridView) findViewById(R.id.gvRemedios);
        adapter = new ResumoAdapter(listaDog, Resumo.this);
        gvRemedios.setAdapter(adapter);
    }

    private void getRemedios()
    {
        try
        {
            Usuario usuario = new Usuario();
            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);

            Call<List<Remedio>> call = service.mostrarRemedio(session.getusename());
            call.enqueue(new Callback<List<Remedio>>() {
                @Override
                public void onResponse(Call<List<Remedio>> call, Response<List<Remedio>> response)
                {
                    if(response.isSuccessful())
                    {
                        remedioList = response.body();
                        //adapter = new ResumoAdapter(remedioList, Resumo.this);
                        gvRemedios.setAdapter(adapter);
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String erro = jObjError.getJSONObject("error").getString("message");
                            Toast.makeText(Resumo.this, erro, Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Resumo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Remedio>> call, Throwable t)
                {
                    String messageProblem = t.getMessage().toString();
                    Toast.makeText(Resumo.this, messageProblem, Toast.LENGTH_LONG).show();
                    Toast.makeText(Resumo.this, "Erro ao mostrar remédios", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch(Exception erro)
        {
            System.err.println(erro);
            erro.printStackTrace();
        }
    }

}
