package com.example.wallet2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wallet2.ui.dashboard.DashboardFragment


class MainActivity : AppCompatActivity() {

    //El id de nuestro canal a registrar
    val CHANNEL_CURSOS = "CURSOS"
    val CHANNEL_OTHERS = "OTROS"

    val GRUPO_SIMPLE = "GRUPO_SIMPLE"


    companion object{
        //el nombre de la acción a ejecutar por el botón en la notificación
        const val ACTION_RECEIVED = "action_received"
    }


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var handler: Handler? = null
    var r: Runnable? = null

    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //goToDashboard()

        //Para android Oreo en adelante, s obligatorio registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
            setOthersChannel()
        }

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        handler = Handler()
        r = Runnable {
            Toast.makeText(
                this@MainActivity,
                "Logging out because of inactivity of the user",
                Toast.LENGTH_SHORT
            ).show()
            preferences.edit()
                .putBoolean(IS_LOGGED, false)
                .apply()
            val loginFragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit()
        }
        startHandler()
        sesionStarted()

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        stopHandler() //stop first and then start
        startHandler()
    }

    fun stopHandler() {
        handler!!.removeCallbacks(r!!)
    }

    fun startHandler() {
        // After 20 minutes of inactivity the app is going to close
        handler!!.postDelayed(r!!, (20 * 60 * 1000).toLong()) //for 20 minutes
    }

    private fun goToLogin() {
        val loginFragment = LoginFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()
    }

    private fun goToDashboard() {
        val dashboardFragment = DashboardFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, dashboardFragment)
            .commit()
    }

    private fun sesionStarted(){
        if(isLogged()){
            goToDashboard()
        }
        else{
            goToLogin()
        }
    }

    private fun isLogged(): Boolean {
        return preferences.getBoolean(IS_LOGGED, false)
    }

    fun simpleNotification() {

        var builder = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.ic_astronauta) //seteamos el ícono de la push notification
            .setColor(getColor(R.color.secondaryColor)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)
        with(NotificationManagerCompat.from(this)) {
            notify(20, builder.build())
        }
    }

    fun customNotification(){

        //obtenemos los layouts por medio de RemoteViews
        val notificationLayout = RemoteViews(packageName, R.layout.notification_custom)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_custom_expanded)


        var notification = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.ic_astronauta)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) //este estilo define que es personalizable
            .setCustomContentView(notificationLayout) //contenido en modo colapsado
            .setCustomBigContentView(notificationLayoutExpanded) //contenido en modo expandido
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(60, notification)
        }

    }

    private fun setNotificationChannel(){
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_CURSOS, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun setOthersChannel(){
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_OTHERS, getString(R.string.channel_others), importance).apply {
            description = getString(R.string.others_description)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    // Overwrite this method in order to get the context of each fragment (used in QR scanner)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}