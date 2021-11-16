package br.unicamp.dailytherapy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class ReminderBroadcast extends BroadcastReceiver {

    private static int REPEATING_ID = 1001;
    private static int ON_TIME_ID = 1002;
    private static String HOUR = "hour";
    private static String MINUTE = "minute";


    public static void setAlarm(Context context, int hour, int minute){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit()
                .putInt(HOUR, hour)
                .putInt(MINUTE, minute)
                .apply();
        setAlarm(context);
    }

    int icone = android.R.drawable.ic_dialog_info;
    String titulo = "Ingerir remédio";
    String texto = "Olá! Está na hora de você ingerir seu remédio" + "!";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            setAlarm(context);
            Toast.makeText(context, "Alarm set", Toast.LENGTH_LONG).show();

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit");

        builder.setSmallIcon(icone);
        builder.setContentTitle(titulo);
        builder.setContentText(texto);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());*/
    }

    private static void setAlarm(Context context) {

        int hour = getHour(context);
        int minute = getMinute(context);

        if(hour == -1 || minute == -1){
            //nenhum horário definido
            return;
        }
        //Cancela possiveis alarmes existentes
        cancelAlarm(context);

        Calendar calendar = getCalendar(hour, minute);

        //Se já passou
        if(isDateBeforeNow(calendar)){
            //adiciona um dia
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }else{
            //Alarme para o horário especificado
            setOneTimeAlarm(context, calendar);
            //adiciona um dia para repetir o alarme no dia seguinte
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        //Repete o alarme no dia seguinte
        setRepeatingAlarm(context, calendar);

    }

    public static void setRepeatingAlarm(Context context, Calendar calendar){

        PendingIntent pendingIntent = getPendingIntent(context, REPEATING_ID);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //Alarme que se repete todos os dias a uma determinada hora
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }

    private static void setOneTimeAlarm(Context context, Calendar calendar){

        PendingIntent pendingIntent= getPendingIntent(context, ON_TIME_ID);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //Alarme para o horário especificado
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent);
    }

    private static void cancelAlarm(Context context){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context, ON_TIME_ID));
        alarmManager.cancel(getPendingIntent(context, REPEATING_ID));

    }

    private static PendingIntent getPendingIntent(Context context, int id){
        //PendingIntent para lançar o serviço
        Intent serviceIntent = new Intent(context, BootService.class);
        return PendingIntent.getService(context, id, serviceIntent, 0);
    }

    private static Calendar getCalendar(int hour, int minute){

        // Cria um Calendar para o horário especificado
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private static int getHour(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(HOUR, -1);
    }
    private static int getMinute(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(MINUTE, -1);
    }

    private static boolean isDateBeforeNow(Calendar calendar){
        return calendar.getTimeInMillis() <= System.currentTimeMillis();
    }
}