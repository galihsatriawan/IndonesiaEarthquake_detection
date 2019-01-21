package `in`.umum.helper.Retrofit

import `in`.umum.helper.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestAdapter{

    // Lazy memberikan keefisienan dalam pendeklarasian , suatu variable akan dibuatkan pada saat dia dipanggil pertama kali
    // tanpa lazy maka secara otomatis variable tersebut akan langsung dibuatkan

    //Okhttp digunakan untuk debug masalah get/up response ke server
    private val instance : Retrofit by lazy{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        var okhttp = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

        Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(okhttp)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
    val retrofit : API
        get() = instance.create(API::class.java)
}