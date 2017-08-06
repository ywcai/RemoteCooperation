package ywcai.ls.remote.global.model;

import ywcai.ls.remote.socket.cfg.ResultCode;

/**
 * Created by zmy_11 on 2017/8/1.
 */
public class WorkStatus {
    public String lastStatus= ResultCode.device_status_offline;
    public String remoteAccessCode="";
    public BusinessType businessType=BusinessType.NONE;
    public String identity="";
}
