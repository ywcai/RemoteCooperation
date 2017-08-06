package ywcai.ls.remote.socket.presenter;

import android.util.Log;

import ywcai.ls.mina.socket.ClientSocket;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.global.presenter.CacheProcess;
import ywcai.ls.remote.global.util.statics.MsgHelper;
import ywcai.ls.remote.socket.cfg.ServerInfo;
import ywcai.ls.remote.socket.cfg.ViewChangeT;
import ywcai.ls.remote.socket.model.ApplicationProtocol;
import ywcai.ls.remote.socket.presenter.inf.LinkProcessInf;
import ywcai.ls.remote.socket.presenter.inf.SocketProcessInf;


public class SocketProcess implements SocketProcessInf {
    CoreInstance coreInstance=CoreInstance.getInstance();
    LinkProcessInf linkProcessInf=new LinkProcess();
    @Override
    public void connSuccess() {
        Log.i("ywcai", "connSuccess: ");
        linkProcessInf.regDev();
    }

    @Override
    public void reConn() {
        MsgHelper.sendEvent(ViewChangeT.session_create_fail,"无法连接服务器，请检查网络！",null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        coreInstance.lsSocket.CreateSession(ServerInfo.socket_server_address,ServerInfo.socket_server_port);
    }

    @Override
    public void revByte(byte[] bytes) {

    }

    @Override
    public void revJson(ApplicationProtocol applicationProtocol) {
        new JsonProcess(applicationProtocol);
    }

    @Override
    public void sessionClosed() {
        CacheProcess cacheProcess=new CacheProcess();
        cacheProcess.delDevices();
        MsgHelper.sendEvent(ViewChangeT.session_closed,"网络异常中断，请检查网络！",null);
    }
}
