package `in`.umum.helper.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils{

    // Agar dapat di akses tanpa adanya instansi static
    companion object {
        fun isNetworkAvailable(ctx : Context?) :Boolean{
            var cm : ConnectivityManager? = ctx!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo : NetworkInfo? = cm!!.activeNetworkInfo ?: return false

            if(networkInfo!!.isConnected) return true
            return false
        }
    }




}