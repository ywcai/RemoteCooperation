package ywcai.ls.remote.main.model;

import java.util.Comparator;

import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.socket.cfg.ResultCode;

public class SortByStatus implements Comparator<DeviceInfo> {
    CoreInstance instance=CoreInstance.getInstance();
    @Override
    public int compare(DeviceInfo lhs, DeviceInfo rhs) {
        int lIndex=0,rIndex=0;
        if(lhs.deviceId.equals(instance.localDev.deviceId))
        {
            return -1;
        }
        if(lhs.status.equals(ResultCode.device_status_link))
        {
            lIndex=3;
        }
        if(lhs.status.equals(ResultCode.device_status_online))
        {
            lIndex=2;
        }
        if(lhs.status.equals(ResultCode.device_status_offline))
        {
            lIndex=1;
        }
        if(rhs.status.equals(ResultCode.device_status_link))
        {
            rIndex=3;
        }
        if(rhs.status.equals(ResultCode.device_status_online))
        {
            lIndex=2;
        }
        if(rhs.status.equals(ResultCode.device_status_offline))
        {
            rIndex=1;
        }
        return lIndex-rIndex;
    }
}
