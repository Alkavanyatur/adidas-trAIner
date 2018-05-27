package es.trainer.trainer.data.utils

import android.content.Context
import android.net.ConnectivityManager

class ControlNetwork(val context: Context) {
    private var mContext: Context

    init {
        mContext = context
    }

    fun isNetWorkAvaible():Boolean{
        val connectivityManager: ConnectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //  val activeNetworkInfo: NetworkInfo

        return connectivityManager.activeNetworkInfo != null


    }
}