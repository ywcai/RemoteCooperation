package ywcai.ls.remote.global.presenter;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import ywcai.ls.remote.welcome.model.instance.MainApplication;

/**
 * Created by zmy_11 on 2017/7/16.
 */

public class LookDevice {

    public String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) MainApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "0000000";
        try {
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
        }
        return deviceId;
    }
    public String getDeviceName() {
        String deviceName= Build.BRAND+" "+Build.MODEL;
        return deviceName;
    }

}
