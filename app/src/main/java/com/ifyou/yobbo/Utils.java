package com.ifyou.yobbo;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class Utils {

    public static boolean hasNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
