package ywcai.ls.remote.welcome.presenter;

import ywcai.ls.remote.global.model.GlobalEventT;
import ywcai.ls.remote.global.model.User;
import ywcai.ls.remote.global.presenter.CacheProcess;
import ywcai.ls.remote.global.util.statics.MsgHelper;
import ywcai.ls.remote.global.model.instance.CoreInstance;


public class LoadUserCache {
    private CacheProcess userCacheInf;
    public LoadUserCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                load();
            }
        }).start();
    }
    private void load() {
        userCacheInf=new CacheProcess();
        User user=userCacheInf.getCacheUser();
        if(user==null)
        {
            MsgHelper.sendEvent(GlobalEventT.un_login,"",null);
            return;
        }
        CoreInstance coreInstance= CoreInstance.getInstance();
        if(coreInstance.lsSocket==null)
        {
            MsgHelper.sendEvent(GlobalEventT.is_login,"",user);
            return;
        }
        if(!coreInstance.lsSocket.getSessionStatus())
        {
            MsgHelper.sendEvent(GlobalEventT.is_login,"",user);
            return;
        }
        MsgHelper.sendEvent(GlobalEventT.is_Run,"",null);
    }
}
