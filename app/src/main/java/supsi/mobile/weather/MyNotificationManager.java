package supsi.mobile.weather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationManager {

    private static MyNotificationManager instance;

    public static MyNotificationManager getInstance() {
        if (instance == null)
            instance = new MyNotificationManager();
        return instance;
    }


    public void setupNotificationManager(NotificationManager mNotificationManager) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(Context mContext, String title, String contentText) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
        manager.notify(0, mBuilder.build());
    }
}
