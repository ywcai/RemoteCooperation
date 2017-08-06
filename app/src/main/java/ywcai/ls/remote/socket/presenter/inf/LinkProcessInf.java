package ywcai.ls.remote.socket.presenter.inf;

import android.content.Context;

import java.util.List;

import ywcai.ls.remote.global.model.DeviceInfo;

/**
 * Created by zmy_11 on 2017/7/31.
 */

public interface LinkProcessInf {
    void startService(Context context);

    List<DeviceInfo> getDevicesForCache();
    void delDevicesCache();

    void createLink(DeviceInfo dev);

    void regDev();

    void cutLink();
}
