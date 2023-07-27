package mada.android.services.extern;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import mada.android.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CANAL = "MyNotifCanal";
    private static final String TOPIC = "all_users_topic";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showNotification(message);
                    }
                });
    }

    private void showNotification(RemoteMessage message) {
        String notificationTitle = message.getNotification().getTitle();
        String notificationMessage = message.getNotification().getBody();

        // Create notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CANAL);
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationMessage);

        //user icon
        notificationBuilder.setSmallIcon(R.drawable.splash_screen);

        // Send notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = getString(R.string.notification_channel_id);
            String channelTitle = getString(R.string.notification_channel_title);
            String channelDescription = getString(R.string.notification_channel_description);

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelTitle,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelDescription);

            notificationManager.createNotificationChannel(notificationChannel);
            notificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(1, notificationBuilder.build());
    }
}
