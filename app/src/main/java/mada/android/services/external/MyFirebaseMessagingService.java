package mada.android.services.external;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import mada.android.R;
import mada.android.models.external.FirebaseToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CANAL = "MyNotifCanal";
    private static final String TOPIC = "all_users_topic";

    private FirebaseTokenService firebaseTokenService;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        showNotification(message);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showNotification(message);
                    }
                });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        saveNewToken(token);
    }

    public void saveNewToken(String token){
        try{
            FirebaseToken firebaseToken = new FirebaseToken(token);
            Call call = new FirebaseTokenService().subscribeToDefaultToken(firebaseToken);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    //Toast.makeText(MyFirebaseMessagingService.this,
                    //        "Utilisateur bien souscrit", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(MyFirebaseMessagingService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void unsubscribeToken(String token){
        try{
            FirebaseToken firebaseToken = new FirebaseToken(token);
            Call call = new FirebaseTokenService().unsubscribeToDefaultToken(firebaseToken);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    //Toast.makeText(MyFirebaseMessagingService.this,
                    //        "Utilisateur bien souscrit", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(MyFirebaseMessagingService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveSavedToken() throws Exception{
        MyFirebaseMessagingService.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String token = task.getResult();
                saveNewToken(token);
            }
        });
    }

    public void unsubscribeSavedToken() throws Exception{
        MyFirebaseMessagingService.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String token = task.getResult();
                unsubscribeToken(token);
            }
        });
    }

    public static Task<String> getToken() throws Exception{
        return FirebaseMessaging.getInstance().getToken();
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
