package `in`.umum.helper.Services.FCM

import `in`.umum.R
import `in`.umum.ui.Dashboard.MainDashboard
import `in`.umum.ui.SplashScreen
import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.notificationManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Math.*


import android.content.ContentResolver
import android.net.Uri


class FCMService : FirebaseMessagingService() {
    private  val DEFAULT_NOTIFICATION_CHANNEL = "default_notification_channel"
    private val TAG ="Galih"
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("Galih",p0)
    }
    private var locationManager : LocationManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage!!.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        buildNotificationChannel()
        sendNotification(remoteMessage.notification!!,remoteMessage)
    }
    //define the listener
    var latitude : Double = 0.0
    var longitude : Double = 0.0
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.longitude
            longitude = location.latitude

//            thetext.setText("" + + ":" + );
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
    private fun sendNotification(notification: RemoteMessage.Notification,remoteMessage: RemoteMessage) {
        val notificationBuilder: NotificationCompat.Builder
        var soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val intent = Intent(this, MainDashboard::class.java)
        doAsync{
            uiThread {
                // GPS
                locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
                try {
                    // Request location updates
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                    val latitude_gempa = remoteMessage.data["latitude"]?.toDouble()

                    val longitude_gempa = remoteMessage.data["longitude"]?.toDouble()
                    val radius = remoteMessage.data["radius"]?.toDouble()
                    var distance = haversine(latitude,longitude,latitude_gempa!!,longitude_gempa!!)
                    if(distance <= radius!!){
                        // Hidupkan alarm
                        val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.backsound)


                    }else{

                    }
                } catch(ex: SecurityException) {
                    Log.d("myTag", "Security Exception, no location available")
                }

            }
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = NotificationCompat.Builder(this, DEFAULT_NOTIFICATION_CHANNEL)
            notificationBuilder.setAutoCancel(true)
                    .setContentTitle(notification.title)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher_round))
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentText(notification.body)
                    .setSound(soundUri)
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
            notificationBuilder.setAutoCancel(true)

                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentTitle(notification.title)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher_round))
                    .setContentText(notification.body)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
        }

        notificationManager.notify(/*notification id*/1, notificationBuilder.build())
    }
    private fun buildNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL, "My Default Notifications", NotificationManager.IMPORTANCE_HIGH)
// Configure the notification channel.
            notificationChannel.description = "Default Channel"
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }
    companion object {
        var token : String = ""
        fun grabFcmToken():String{

            FirebaseInstanceId.getInstance().
                    instanceId.addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Log.d("FCMActivity", task.result!!.token)
                    token =  task.result!!.token
                }

            }
            FirebaseMessaging.getInstance().subscribeToTopic("alltester")
            return token
        }

    }
    val R_Bumi = 6372.8 // in kilometers

    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val λ1 = toRadians(lat1)
            val λ2 = toRadians(lat2)
            val Δλ = toRadians(lat2 - lat1)
            val Δφ = toRadians(lon2 - lon1)
            return 2 * R_Bumi * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
    }
}
