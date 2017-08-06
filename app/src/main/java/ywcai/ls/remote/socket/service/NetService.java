package ywcai.ls.remote.socket.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import ywcai.ls.mina.socket.ClientSocket;
import ywcai.ls.remote.socket.cfg.ServerInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.socket.model.SocketListener;

public class NetService extends Service {
    private static final String TAG = "LsSocketService";
    private static final int PID = 7772;
    CoreInstance instance=CoreInstance.getInstance();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance.lsSocket == null) {
            Log.i("ywcai", "start a new socket");
            initSocket();
        }
        else
        {
            Log.i("ywcai", "use a old socket");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("ywcai", "start net service !");
//        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
//        Intent nfIntent = new Intent(this, WelComeActivity.class);
//        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.nav))
//                .setContentTitle("远程协助")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText("远程协助")
//                .setWhen(System.currentTimeMillis());
//        Notification notification;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            notification = builder.build();
//        } else {
//            notification = builder.getNotification();
//        }
//        notification.defaults = Notification.DEFAULT_SOUND;
//        startForeground(PID, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        destroySocket();
        super.onDestroy();
    }
    private void initSocket() {
        instance.lsSocket = new ClientSocket();
        SocketListener listener = new SocketListener();
        instance.lsSocket.addListener(listener);
        connSocket();
    }
    private void connSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.lsSocket.CreateSession(ServerInfo.socket_server_address, ServerInfo.socket_server_port);
            }
        }).start();
    }
    private void destroySocket() {
        instance.lsSocket.removeAllListener();
        instance.lsSocket.CloseSession();
        instance.lsSocket=null;
        instance=null;
        Log.i("ywcai", "destroySocket: ");
    }
}
