package com.movies.popular.popmovies.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by lenovo on 3/2/2018.
 */

public class NetworkUtility {
    private Context context;

    public NetworkUtility(Context context) {
        this.context = context;
    }


    private static NetworkUtility INSTANCE;

    public static NetworkUtility getNetworkState(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkUtility(context);
        }
        return INSTANCE;
    }

    public boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
