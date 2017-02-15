package com.mercacortex.callbroadcast;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telecom.Call;
import android.telephony.TelephonyManager;

public class BroadcastCall extends BroadcastReceiver{

    private final int CALL_NOTIFICATION = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Tiene el contexto de la aplicación
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String state = bundle.getString(TelephonyManager.EXTRA_STATE);
            if (state.equals(Call.STATE_RINGING)) {
                String number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                //Crea una notificación que se guarda en PendingIntent (es un token que
                //contiene un intent, pendiente de ejecutarse)
                Intent newIntent = new Intent(context, Call_Activity.class);
                newIntent.putExtra("number", number);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        CALL_NOTIFICATION, newIntent, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle(context.getApplicationInfo().className)
                        .setContentText("Llamada Número" + number);

                //Parámetros extra
                builder.setDefaults(Notification.DEFAULT_VIBRATE);
                builder.setDefaults(Notification.DEFAULT_LIGHTS);
                builder.setContentIntent(pendingIntent);    //<--- Muy importante
                builder.setSmallIcon(R.mipmap.ic_launcher);    //<--- Muy importante

                //Añadir la notificación al NotificationManager
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(CALL_NOTIFICATION, builder.build());

            }
        }
    }
}
