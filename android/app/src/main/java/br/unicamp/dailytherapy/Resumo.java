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

public class Resumo extends AppCompatActivity {

    private void mostrarRemedio() {
        try
        {
            Remedio remedio = new Remedio(nomeRemedio, horario, frequencia, inicio, fim);

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Remedio> call = service.mostrarRemedio(remedio);

            call.enqueue(new Callback<Remedio>() {
                @Override
                public void onResponse(Call<Remedio> call, Response<Remedio> response) {
                    if(response.isSuccessful())
                    {
                        Remedio remed = response.body();
                        Toast.makeText(Medicamento.this, "REMEDIO" + remedio.getNomeRemedio() +
                                " " + remedio.getFrequencia() +
                                " " + remedio.getHorario() +
                                " " + remedio.getInicio() +
                                " " + remedio.getFim , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        try
                        {
                            Gson gson = new Gson();
                            TrataErro erro = gson.fromJson(response.errorBody().string(), TrataErro.class);

                            Toast.makeText(Medicamento.this, "toast1: "+erro.getError(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception err)
                        {
                            Toast.makeText(Medicamento.this, "toast2: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Remedio> call, Throwable t) {
                    Toast.makeText(Medicamento.this, "Erro ao fazer cadastro", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Medicamento.this, "toast3: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception err)
        {
            Toast.makeText(Medicamento.this, "toast4: "+err.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
