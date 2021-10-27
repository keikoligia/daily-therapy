package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import br.unicamp.dailytherapy.TratamentoErros.TrataErro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medicamento extends AppCompatActivity {

    EditText edtNomeMedicamento, edtInicioDia, edtInicioMes, edtInicioAno, edtFimDia, edtFimMes, edtFimAno, edtHorario;
    Button btnSalvar;
    RadioGroup rgbDias;
    RadioButton rbDiariamente, rbEspecifico, rbSemanal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        edtNomeMedicamento = (EditText) findViewById(R.id.edtNomeMedicamento);
        edtInicioDia = (EditText) findViewById(R.id.edtInicioDia);
        edtInicioMes = (EditText) findViewById(R.id.edtInicioMes);
        edtInicioAno = (EditText) findViewById(R.id.edtInicioAno);
        edtFimDia = (EditText) findViewById(R.id.edtFimDia);
        edtFimMes = (EditText) findViewById(R.id.edtFimMes);
        edtFimAno = (EditText) findViewById(R.id.edtFimAno);

        edtHorario = (EditText) findViewById(R.id.edtHorario);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        rgbDias = (RadioGroup) findViewById(R.id.rgbDias);
        rbDiariamente = (RadioButton) findViewById(R.id.rbDiaramente);
        rbEspecifico = (RadioButton) findViewById(R.id.rbEspecifico);
        rbSemanal = (RadioButton) findViewById(R.id.rbSemanal);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirMedicamento();
            }
        });
    }

    private void inserirMedicamento()
    {
        try
        {
            String nomeRemedio   = edtNomeMedicamento.getText().toString();
            String horario       = edtHorario.getText().toString();
            String inicioDia     = edtInicioDia.getText().toString();
            String inicioMes     = edtInicioMes.getText().toString();
            String inicioAno     = edtInicioAno.getText().toString();
            String fimDia     = edtFimDia.getText().toString();
            String fimMes     = edtFimMes.getText().toString();
            String fimAno     = edtFimAno.getText().toString();

            String inicio = inicioAno+"-"+inicioMes+"-"+inicioDia;
            String fim    = fimAno+"-"+fimMes+"-"+fimDia;

            char frequencia = ' ';
            if(rbEspecifico.isChecked())
                frequencia = 'e';
            else if(rbSemanal.isChecked())
                frequencia = 's';
            else if(rbDiariamente.isChecked())
                frequencia = 'd';

            Remedio remedio = new Remedio(nomeRemedio, horario, frequencia, inicio, fim);
            Toast.makeText(Medicamento.this, nomeRemedio + " " + horario + " " + frequencia + " " + inicio + " " + " "+ fim, Toast.LENGTH_SHORT).show();

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Remedio> call = service.incluirMedicamento(remedio);

            call.enqueue(new Callback<Remedio>() {
                @Override
                public void onResponse(Call<Remedio> call, Response<Remedio> response) {
                    if(response.isSuccessful())
                    {
                        Remedio remed = response.body();
                        Toast.makeText(Medicamento.this, "REMEDIO INSERIDO!!!", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(Cadastro.this, Login.class);
                        startActivity(intent);
                        finish();*/
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