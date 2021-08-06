package com.example.boosted

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val chanelID = "channelID" // este es el id del canal
    private val channelName = "channelName" // este el nombre de canal

    private val notificationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //llamamos el canal declarado
        createNotificacionChannel ()

        //creamos un intent accionde ventana
        val intent = Intent(this, ProyectActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //creamos un intent boton desde la notificacion
        val snoozeIntent = Intent(this, ProyectActivity::class.java)
        val snoozePendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, snoozeIntent, 0)

        val notification = NotificationCompat.Builder(this, chanelID).also{
            it.setContentTitle("NOtificacion de exito")
            it.setContentText("Este es el mi Notificacion")
            it.setColor(Color.RED)
            it.setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("El contenido de la notificacion es mi primerita y vamos a alargar esto para que se note aun mas si o no o como hacemos"))
            it.setSmallIcon(R.drawable.ic_stat_boosted)
            it.priority = NotificationCompat.PRIORITY_HIGH

            //intent
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)

            //boton
            it.addAction(R.drawable.ic_stat_boostednoti , "Redirect",
                snoozePendingIntent)

        }.build()

        val notificationManager = NotificationManagerCompat.from(this)

        val boton: Button = findViewById(R.id.btn_newNotificacion)
        boton.setOnClickListener {
            // es para mostrar la notificacion
            notificationManager.notify(notificationId, notification)
        }
    }

    // esto es el celular como reaciona cuando reciba una notificacion
    private fun createNotificacionChannel () {
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH //nivel de importancia donde se le notifica a l usuario un cambio externo

            val channel = NotificationChannel(chanelID,channelName, importance).apply {
                //INSIGNIA DE
                lightColor = Color.RED // color de red
                enableLights(true) // habiltamos la luz
                enableVibration(true)
            } // el canal con sus parametros

            //para poder construir esta notificacion hacemos un  notificacion manager
            //que es basicammente servivio de andoird el cual podemos crear o cancelar
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel) // creamo el canal

        }
    }
}