package com.chatterbox.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 15/08/16 at 15:46
 * <p/>
 * Description:
 */
public class ChatterBoxMessagingService extends FirebaseMessagingService{

    private static final String TAG = "MyCBService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "CB Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "CB Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "CB Data Message: " + remoteMessage.getData());
    }

/*class end*/
}
