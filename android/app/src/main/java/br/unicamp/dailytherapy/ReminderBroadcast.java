package br.unicamp.dailytherapy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver
{
    private String nr;

    public String getNomeRemedio(String nome)
    {
        this.nr = nome;
        return nome;
    }

    int icone = android.R.drawable.ic_dialog_info;
    String titulo = "Ingerir " + this.nr;
    String texto = "Olá! Está na hora de você ingerir " + this.nr + "!";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit");

        builder.setSmallIcon(icone);
        builder.setContentTitle(titulo);
        builder.setContentText(texto);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }
}
