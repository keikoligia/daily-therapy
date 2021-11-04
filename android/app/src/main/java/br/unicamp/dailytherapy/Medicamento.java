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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        createNotificationChannel();

        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomeUsuario = extras.getString("key");
            Toast.makeText(Medicamento.this, "resultado: "+ nomeUsuario, Toast.LENGTH_SHORT).show();

        }*/

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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirMedicamento();

                Intent intent = new Intent(Medicamento.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Medicamento.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long hora       = Byte.parseByte(edtHorario.getText().toString());
                long minuto     = Byte.parseByte(edtMinuto.getText().toString());
                long horario = hora + (minuto/60);

                long clickHora = LocalDateTime.now().getHour() - 3;
                long clickMinuto = LocalDateTime.now().getMinute();
                long horarioClick = clickHora + (clickMinuto/60);
                long trigger = horarioClick - horario;

                if((horarioClick - horario) > 0)
                {
                    //Toast.makeText(Medicamento.this, "O tempo passou, seu alerme tocara daqui: " + (24-trigger) + " horas.", Toast.LENGTH_SHORT).show();
                    trigger = 24-trigger;
                }
                    //System.out.print("O tempo passou, seu alerme tocara daqui: " + (24-trigger) + " horas.");
                if((horarioClick - horario) < 0)
                {
                    //Toast.makeText(Medicamento.this, "Seu alerme tocara daqui: " + (trigger*60)*-1 + " minutos.", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Medicamento.this, "resultado: "+trigger*3600000, Toast.LENGTH_SHORT).show();

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        trigger*3600000,
                        pendingIntent);
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void inserirMedicamento()
    {
        try
        {
            String nomeUsuario = getIntent().getStringExtra("key");
            Toast.makeText(Medicamento.this, "resultado: "+ nomeUsuario, Toast.LENGTH_SHORT).show();

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

            Remedio remedio = new Remedio(nomeRemedio, horario, frequencia, inicio, fim, nomeUsuario);
            Toast.makeText(Medicamento.this, nomeUsuario + " " + nomeRemedio + " " + horario + " " + frequencia + " " + inicio + " " + " "+ fim, Toast.LENGTH_SHORT).show();

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