package com.java.githubsearch.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.java.githubsearch.R;
import com.java.githubsearch.activities.BaseActivity;

/**
 * Created by achau on 28-12-2017.
 */

public class Util {

    public static void hideEmptyContentLayout(BaseActivity context){
        if(context == null)
            return;
        RelativeLayout root = (RelativeLayout) context.findViewById(R.id.ecl_root);
        if(root != null)
            root.setVisibility(View.GONE);


    }

    public static boolean checkNotNullOrEmpty(String s){
        return !(s == null || s.isEmpty());
    }

    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }


    public static void showEmptyContentLayout( Context context, String message ){
        if(context == null)
            return;
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        if(rootView == null)
            return;
        RelativeLayout root = rootView.findViewById(R.id.ecl_root);
        if(root != null)
            root.setVisibility(View.VISIBLE);
        TextView messageTV = (TextView) rootView.findViewById(R.id.ecl_message);
        if(messageTV != null) {
            if(checkNotNullOrEmpty(message)){
                messageTV.setVisibility(View.VISIBLE);
                messageTV.setText(message);
            }
            else
                messageTV.setVisibility(View.GONE);
        }
    }


}
