package ywcai.ls.remote.socket.model;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.mina.core.session.IoSession;

import java.io.UnsupportedEncodingException;

import ywcai.ls.mina.socket.SocketEventListener;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.presenter.SocketProcess;
import ywcai.ls.remote.socket.presenter.inf.SocketProcessInf;


public class SocketListener implements SocketEventListener {
    private SocketProcessInf socketProcessInf = new SocketProcess();

    @Override
    public void sessionCreated(IoSession ioSession) {

    }

    @Override
    public void sessionOpened(IoSession ioSession) {

    }

    @Override
    public void messageReceived(IoSession ioSession, byte[] bytes) {
        byte[] payLoad = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, payLoad, 0, payLoad.length);
        if (bytes[0] == ResultCode.byte_head_byte) {
            socketProcessInf.revByte(payLoad);
        }
        if (bytes[0] == ResultCode.byte_head_json) {
            String json = "";
            try {
                json = new String(payLoad, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            ApplicationProtocol applicationProtocol = gson.fromJson(json, ApplicationProtocol.class);
            socketProcessInf.revJson(applicationProtocol);
        }
    }

    @Override
    public void errorCatch(IoSession ioSession, Throwable throwable) {

    }

    @Override
    public void messageSent(IoSession ioSession, Object o) {

    }

    @Override
    public void sessionClosed(IoSession ioSession) {
        Log.i("ywcai", "session is close ");
        socketProcessInf.sessionClosed();
        socketProcessInf.reConn();
    }

    @Override
    public void sessionCreateStart(String s, int i) {

    }

    @Override
    public void sessionCreateEnd(IoSession ioSession, boolean b) {
        Log.i("ywcai", "session is start " + b);
        if (b) {
            //提示activity连接成功，后台发送注册数据进行注册
            socketProcessInf.connSuccess();
        } else {
            //提示网络连接中断，系统再后台自动重连
            socketProcessInf.reConn();
        }
    }
}
