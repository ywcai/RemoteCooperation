package ywcai.ls.remote.global.model;

import ywcai.ls.remote.global.presenter.LookDevice;
import ywcai.ls.remote.socket.cfg.ResultCode;

public class DeviceInfo {
    public String userId;
    public String deviceId;
    public String deviceName;
    public String deviceMode;
    public String status;
    public String accessCode;
    public String remoteIp;
    public void initDev(String userId)
    {
        this.userId="E73E438144BA28CAF69F9751B70205E2";//设置qqID
        this.setDeviceId();
        this.setDeviceName();
        this.deviceMode= ResultCode.device_mode_mobile;
        this.status=ResultCode.device_status_offline;
        this.accessCode="----";
    }

    private void setDeviceId() {
        LookDevice lookDevice=new LookDevice();
        deviceId=lookDevice.getDeviceId();
    }
    private void setDeviceName() {
        LookDevice lookDevice=new LookDevice();
        deviceName=lookDevice.getDeviceName();
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceMode='" + deviceMode + '\'' +
                ", status='" + status + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                '}';
    }
}
