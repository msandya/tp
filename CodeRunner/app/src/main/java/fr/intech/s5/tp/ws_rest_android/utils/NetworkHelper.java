package fr.intech.s5.tp.ws_rest_android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

    public static boolean hasNetworkAccess(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            return null != activeNetwork &&
                    activeNetwork.isConnectedOrConnecting();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
