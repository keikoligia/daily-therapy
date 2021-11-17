package br.unicamp.dailytherapy;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import androidx.annotation.Nullable;

public class BootService extends IntentService {

    private PowerManager.WakeLock wakeLock;

    public BootService() {
        super("BootService");
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "BootService");
        wakeLock.acquire();
    }

    //int icone = android.R.drawable.ic_dialog_info;
    //String titulo = "Ingerir remédio";
    //String texto = "Olá! Está na hora de você ingerir seu remédio" + "!";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //Lance a notificação aqui.
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit");

        builder.setSmallIcon(icone);
        builder.setContentTitle(titulo);
        builder.setContentText(texto);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wakeLock.isHeld()) {
            //Verificou-se que o iluminar do ecrã
            //não acontecia devido ao WakeLock ser
            //rapidamente libertado(apesar de PowerManager.ON_AFTER_RELEASE !?).
            try {
                //Atrasa a libertação do WakeLock
                //de forma a permitir a iluminação do ecrâ.
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                wakeLock.release();
            }
        }
    }
}
