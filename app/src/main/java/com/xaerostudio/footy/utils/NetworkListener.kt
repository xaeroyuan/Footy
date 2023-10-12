package com.xaerostudio.footy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import android.util.Log

class NetworkListener: ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)
        isNetworkAvailable.value = isNetworkAvailable(context)
        return isNetworkAvailable
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
        Log.d("Network", "active network $network")
        network ?: return false  // return false if network is null
        val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // return false if Network Capabilities is null
        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
                Log.d("Network", "wifi connected")
                true
            }
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
                Log.d("Network", "cellular network connected")
                true
            }
            else -> {
                Log.d("Network", "internet not connected")
                false
            }
        }
    }
    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}