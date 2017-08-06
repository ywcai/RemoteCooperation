package ywcai.ls.remote.socket.presenter.inf;

import android.content.Context;

import java.util.List;

import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.socket.model.ApplicationProtocol;

/**
 * Created by zmy_11 on 2017/7/30.
 */
public interface SocketProcessInf {
    void connSuccess();
    void reConn();
    void revByte(byte[] bytes);
    void revJson(ApplicationProtocol applicationProtocol);
    void sessionClosed();
}
