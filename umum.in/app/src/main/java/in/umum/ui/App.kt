package `in`.umum.ui

import `in`.umum.database.AppDatabase
import `in`.umum.helper.Services.Background.SensorService
import android.app.Application
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class App : Application() {
    companion object {
        var instance : App? = null
        internal fun getInstance(): App {
            return instance!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        // Open DB with RX

        startService(Intent(applicationContext, SensorService::class.java))
    }
    fun createOpenDB(){
        Observable.fromCallable {
            AppDatabase.getInstance(applicationContext)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

    }


}