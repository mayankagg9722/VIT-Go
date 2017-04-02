package com.vitgo.mayankaggarwal.viteventsapp.utils;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mayankaggarwal on 29/03/17.
 */

public class MyFirebaseInstantService extends FirebaseInstanceIdService {

    public static final String token="TOKEN";
    @Override
    public void onTokenRefresh() {
        Log.v(token, FirebaseInstanceId.getInstance().getToken().toString());
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

}
