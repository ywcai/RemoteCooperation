package ywcai.ls.remote.socket.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.global.presenter.CacheProcess;
import ywcai.ls.remote.main.model.SortByStatus;
import ywcai.ls.remote.main.presenter.inf.BusinessProcessInf;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.model.instance.MesUtil;
import ywcai.ls.remote.socket.presenter.inf.LinkProcessInf;
import ywcai.ls.remote.socket.service.NetService;


public class LinkProcess implements LinkProcessInf {
    CacheProcess cacheProcess = new CacheProcess();
    CoreInstance coreInstance=CoreInstance.getInstance();
    @Override
    public void startService(Context context) {
        context.startService(new Intent(context, NetService.class));
    }

    @Override
    public List<DeviceInfo> getDevicesForCache() {
        List<DeviceInfo> devices = new ArrayList();
        List temp = cacheProcess.getDevices();
        if (temp != null) {
            devices.addAll(temp);
            SortByStatus sortByStatus = new SortByStatus();
            Collections.sort(devices, sortByStatus);
        }
        return devices;
    }

    @Override
    public void delDevicesCache() {
        cacheProcess.delDevices();
    }

    @Override
    public void createLink(DeviceInfo dev) {
        MesUtil.sendJson(coreInstance.lsSocket, ResultCode.json_type_req_ctrl_create, dev.accessCode);
        coreInstance.workStatus.remoteAccessCode=dev.accessCode;
        Log.i("ywcai", "request link ");
    }

    @Override
    public void regDev() {
        Gson gson = new Gson();
        String content = gson.toJson(coreInstance.localDev);
        MesUtil.sendJson(coreInstance.lsSocket, ResultCode.json_type_req_reg, content);
        Log.i("ywcai", "request regDev "+coreInstance.lsSocket.getSessionStatus());
    }

    @Override
    public void cutLink() {
        Gson gson = new Gson();
        String content = gson.toJson(coreInstance.localDev);
        MesUtil.sendJson(coreInstance.lsSocket, ResultCode.json_type_req_ctrl_cut,content);
        Log.i("ywcai", "request regDev "+coreInstance.lsSocket.getSessionStatus());
    }
}
