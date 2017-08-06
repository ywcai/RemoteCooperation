package ywcai.ls.remote.socket.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

import ywcai.ls.remote.global.model.BusinessType;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.WorkStatus;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.global.presenter.CacheProcess;
import ywcai.ls.remote.global.util.statics.MsgHelper;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.cfg.ViewChangeT;
import ywcai.ls.remote.socket.model.ApplicationProtocol;

public class JsonProcess {
    CacheProcess cacheProcess=new CacheProcess();
    DeviceInfo localDev= CoreInstance.getInstance().localDev;
    WorkStatus workStatus=CoreInstance.getInstance().workStatus;
    public JsonProcess(ApplicationProtocol applicationProtocol) {
        switch (applicationProtocol.type)
        {
            case ResultCode.json_type_notify_back_reg_fail:
                regFail(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_back_reg_ok_with_list:
                regOk(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_back_add_temp_fail:
                //添加失败
                break;
            case ResultCode.json_type_notify_back_create_success:
                createControlProcessSuccess(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_back_create_fail:
                createControlProcessFail();
                break;
            case ResultCode.json_type_notify_back_cut_success:
                cutControlProcessSuccess();
                break;
            case ResultCode.json_type_notify_back_change_abs_fail:

                break;
            case ResultCode.json_type_notify_back_change_code_fail:

                break;
            case ResultCode.json_type_notify_back_change_detail_fail:

                break;
            case ResultCode.json_type_notify_loop_add_temp_ok:

                break;
            case ResultCode.json_type_notify_loop_change_abs_ok:

                break;
            case ResultCode.json_type_notify_loop_change_code_ok:

                break;
            case ResultCode.json_type_notify_loop_change_detail_ok:

                break;
            case ResultCode.json_type_notify_loop_process_down:

                break;
            case ResultCode.json_type_notify_loop_process_up:

                break;
            case ResultCode.json_type_notify_loop_turn_busy:
                deviceTurnBusy(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_loop_turn_free:
                deviceTurnOn(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_loop_turn_off:
                deviceTurnOff(applicationProtocol.content);
                break;
            case ResultCode.json_type_notify_loop_turn_on:
                deviceTurnOn(applicationProtocol.content);
                break;
            case ResultCode.json_type_switch_control_key:
                break;
            case ResultCode.json_type_switch_control_mouse:
                break;
            case ResultCode.json_type_switch_desk_config:
                break;
        }
    }
    private void createControlProcessSuccess(String content) {
        workStatus.businessType= BusinessType.FREE;
//        workStatus.remoteAccessCode=content;
        workStatus.identity=content;
        workStatus.lastStatus=ResultCode.device_status_link;
        MsgHelper.sendEvent(ViewChangeT.create_process_success,"建立连接成功",null);
    }
    private void createControlProcessFail() {
        MsgHelper.sendEvent(ViewChangeT.create_process_success,"建立连接失败",null);
    }
    private void cutControlProcessSuccess() {
        workStatus.businessType= BusinessType.NONE;
        workStatus.remoteAccessCode="";
        workStatus.identity="";
        workStatus.lastStatus=ResultCode.device_status_online;
        MsgHelper.sendEvent(ViewChangeT.cut_process_success,"断开连接成功",null);
    }
    private void regFail(String content) {
        MsgHelper.sendEvent(ViewChangeT.reg_fail,"设备登录失败:"+content,null);
    }
    private void regOk(String devices) {
        cacheProcess.setDevices(devices);
        MsgHelper.sendEvent(ViewChangeT.update_device_list,"update device list",null);
    }
    private void deviceTurnOn(String device) {
        cacheProcess.setDevice(device);
        MsgHelper.sendEvent(ViewChangeT.update_device_single,"turn on device : "+device,null);
    }
    private void deviceTurnBusy(String device) {
        cacheProcess.setDevice(device);
        MsgHelper.sendEvent(ViewChangeT.update_device_single,"turn on busy : "+device, null);
    }
    private void deviceTurnOff(String device) {
        cacheProcess.setDevice(device);
        MsgHelper.sendEvent(ViewChangeT.update_device_single,"turn off",null);
    }
}
