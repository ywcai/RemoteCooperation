package ywcai.ls.remote.global.model.instance;


import ywcai.ls.mina.socket.ClientSocket;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.User;
import ywcai.ls.remote.global.model.WorkStatus;

public class CoreInstance {
    private static Object lock = new Object();
    public static CoreInstance socketObject = null;
    public ClientSocket lsSocket;
    public User loginUser;
    public DeviceInfo localDev;
    public WorkStatus workStatus;
    private CoreInstance() {

    }
    public static CoreInstance getInstance() {
        synchronized (lock) {
            if (socketObject == null) {
                socketObject = new CoreInstance();
            }
        }
        return socketObject;
    }
}
