package com.example.imdbclone.BroadCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ConnectivityBroadcastReciever (val mConnectivityRecieverListener:ConnectivityRecieverListener):BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        mConnectivityRecieverListener.onNetworkConnectionConnected()
    }

}