package br.unicamp.dailytherapy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.LocalTime;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.Calendar;

import br.unicamp.dailytherapy.TratamentoErros.TrataErro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medicamento extends AppCompatActivity {

    EditText edtNomeMedicamento, edtInicioDia, edtInicioMes, edtInicioAno,
            edtFimDia, edtFimMes, edtFimAno, edtHorario, edtMinuto;
    Button btnSalvar;
    RadioGroup rgbDias;
    RadioButton rbDiariamente, rbEspecifico, rbSemanal;
    private Session session;//global variable
    private String nomeUsuario;
    ReminderBroadcast rb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        //createNotificationChannel();

        edtNomeMedicamento = (EditText) findViewById(R.id.edtNomeMedicamento);
        edtInicioDia = (EditText) findViewById(R.id.edtInicioDia);
        edtInicioMes = (EditText) findViewById(R.id.edtInicioMes);
        edtInicioAno = (EditText) findViewById(R.id.edtInicioAno);
        edtFimDia = (EditText) findViewById(R.id.edtFimDia);
        edtFimMes = (EditText) findViewById(R.id.edtFimMes);
        edtFimAno = (EditText) findViewById(R.id.edtFimAno);
        edtMinuto = (EditText) findViewById(R.id.edtMinuto);
        edtHorario = (EditText) findViewById(R.id.edtHorario);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        rgbDias = (RadioGroup) findViewById(R.id.rgbDias);
        rbDiariamente = (RadioButton) findViewById(R.id.rbDiaramente);
        rbEspecifico = (RadioButton) findViewById(R.id.rbEspecifico);
        rbSemanal = (RadioButton) findViewById(R.id.rbSemanal);

        rb = new ReminderBroadcast();

        btnSalvar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inserirMedicamento();
            }
        });
    }

    private void inserirMedicamento()
    {
        try
        {
            String nomeRemedio   = edtNomeMedicamento.getText().toString();
            String hora       = edtHorario.getText().toString();
            String minuto     = edtMinuto.getText().toString();
            String inicioDia     = edtInicioDia.getText().toString();
            String inicioMes     = edtInicioMes.getText().toString();
            String inicioAno     = edtInicioAno.getText().toString();
            String fimDia     = edtFimDia.getText().toString();
            String fimMes     = edtFimMes.getText().toString();
            String fimAno     = edtFimAno.getText().toString();

            String horario = hora+":"+minuto;
            String inicio = inicioAno+"-"+inicioMes+"-"+inicioDia;
            String fim    = fimAno+"-"+fimMes+"-"+fimDia;

            char frequencia = ' ';
            if(rbEspecifico.isChecked())
                frequencia = 'e';
            else if(rbSemanal.isChecked())
                frequencia = 's';
            else if(rbDiariamente.isChecked())
                frequencia = 'd';

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minuto));

            Intent intent = new Intent(Medicamento.this, ReminderBroadcast.class);
            rb.setRepeatingAlarm(this, calendar);
            rb.onReceive(this, intent);

            Session session = new Session(getApplicationContext());
            String userName = session.getusename();
            Remedio remedio = new Remedio(nomeRemedio, horario, frequencia, inicio, fim, userName);
            Toast.makeText(Medicamento.this, userName + " " + nomeRemedio + " " + horario + " " + frequencia + " " + inicio + " " + " "+ fim, Toast.LENGTH_SHORT).show();

            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Remedio> call = service.incluirMedicamento(remedio);

            call.enqueue(new Callback<Remedio>() {
                @Override
                public void onResponse(Call<Remedio> call, Response<Remedio> response)
                {
                    if(response.isSuccessful())
                    {
                        Remedio remed = response.body();
                        Toast.makeText(Medicamento.this, "REMEDIO INSERIDO!!!", Toast.LENGTH_SHORT).show();
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