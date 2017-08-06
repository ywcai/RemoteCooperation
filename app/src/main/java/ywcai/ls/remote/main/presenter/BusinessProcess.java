package ywcai.ls.remote.main.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ywcai.ls.remote.global.model.BusinessType;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.global.presenter.CacheProcess;
import ywcai.ls.remote.main.model.SortByStatus;
import ywcai.ls.remote.main.presenter.inf.BusinessProcessInf;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.model.instance.MesUtil;
import ywcai.ls.remote.socket.service.NetService;


public class BusinessProcess implements BusinessProcessInf {
    CacheProcess cacheProcess = new CacheProcess();
    CoreInstance coreInstance=CoreInstance.getInstance();

    @Override
    public void controlRemote() {

    }

    @Override
    public void pullDesk() {

    }

    @Override
    public void pullCamera() {

    }

    @Override
    public void pushDesk() {

    }

    @Override
    public void pushCamera() {

    }
}
