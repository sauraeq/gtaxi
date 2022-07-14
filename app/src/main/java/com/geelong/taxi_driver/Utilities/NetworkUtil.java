package com.geelong.taxi_driver.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.geelong.taxi_driver.R;


public class NetworkUtil {
    public static boolean checkInternetConnection(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // ARE WE CONNECTED TO THE NET
            if (conMgr.getActiveNetworkInfo() != null

                    && conMgr.getActiveNetworkInfo().isAvailable()

                    && conMgr.getActiveNetworkInfo().isConnected()) {

                return true;

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
