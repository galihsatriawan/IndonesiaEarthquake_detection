package `in`.umum.ui

import `in`.umum.R
import `in`.umum.helper.Constants

import `in`.umum.helper.Services.FCM.FCMService
import `in`.umum.helper.SharedPref
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
//-------------- Important Import ----///
import `in`.umum.helper.SharedPref.get
import `in`.umum.ui.Dashboard.MainDashboard

class SplashScreen : AppCompatActivity() {
    lateinit var pref : SharedPreferences
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY : Long = 3000
    internal val mRunnable: Runnable = Runnable {
        if(!isFinishing){

            var intent : Intent

                intent = Intent(applicationContext,MainDashboard::class.java)

            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        FCMService.grabFcmToken();



        //Initialize the Handler
        mDelayHandler = Handler()


        mDelayHandler!!.postDelayed(mRunnable,SPLASH_DELAY)
    }

    override fun onDestroy() {
        if(mDelayHandler != null){
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}


