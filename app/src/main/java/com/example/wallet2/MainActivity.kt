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
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.wallet2.ui.user.IS_LOGGED
import com.example.wallet2.ui.user.PREF_NAME
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    val CHANNEL_USUARIOS = "USUARIOS"
    val CHANNEL_ANUNCIOS = "ANUNCIOS"

    val GRUPO_SIMPLE = "GRUPO_SIMPLE"


    companion object{

        const val ACTION_RECEIVED = "action_received"
    }


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var handler: Handler? = null
    var r: Runnable? = null

    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
            setOthersChannel()
        }
        val mAuth = FirebaseAuth.getInstance()
        //Navigation
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

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
            /*val loginFragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit()*/

            mAuth!!.signOut()
            navController.navigate(R.id.action_dashboardFragmentDest_to_loginFragment, null)
        }

        startHandler()
        //sessionStarted()

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

    /*private fun sessionStarted(){
        if(isLogged()){
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, null)
        } else {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null)
        }
    }*/

    fun isLogged(): Boolean {
        return preferences.getBoolean(IS_LOGGED, false)
    }

    fun simpleNotification(Message_title: String,Message_text: String, Channel: Int) {

        var builder = NotificationCompat.Builder(this, CHANNEL_ANUNCIOS)
            .setSmallIcon(R.drawable.astr_logo)
            .setColor(getColor(R.color.primaryColor))
            .setContentTitle(Message_title)
            .setContentText(Message_text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroup(GRUPO_SIMPLE)
        with(NotificationManagerCompat.from(this)) {
            notify(Channel, builder.build())
        }
    }

    fun customNotification(){

        val notificationLayout = RemoteViews(packageName, R.layout.notification_custom)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_custom_expanded)


        var notification = NotificationCompat.Builder(this, CHANNEL_ANUNCIOS)
            .setSmallIcon(R.drawable.astr_logo)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(60, notification)
        }

    }

    private fun setNotificationChannel(){
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_USUARIOS, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun setOthersChannel(){
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ANUNCIOS, getString(R.string.channel_others), importance).apply {
            description = getString(R.string.others_description)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    // Overwrite this method in order to get the context of each fragment (used in QR scanner camera)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }*/
        // New method to read fragments context in Navigation
        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        if (navHostFragment != null) {
            val childFragments = navHostFragment?.childFragmentManager?.fragments
            childFragments.forEach { fragment ->
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}