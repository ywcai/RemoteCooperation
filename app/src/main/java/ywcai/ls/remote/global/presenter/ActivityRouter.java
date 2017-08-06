package ywcai.ls.remote.global.presenter;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.User;
import ywcai.ls.remote.global.model.WorkStatus;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.login.view.LoginActivity;
import ywcai.ls.remote.main.view.MainActivity;
import ywcai.ls.remote.global.presenter.inf.RouterInf;


public class ActivityRouter implements RouterInf {

    private Activity activity;
    public ActivityRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void startLogin() {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void createNewSession(User user) {
        setInstance(user);
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putBoolean("isRecovery",false);
        intent.putExtras(bundle);
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
    @Override
    public void recoverySession() {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putBoolean("isRecovery",true);
        intent.putExtras(bundle);
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
    private void setInstance(User user)
    {
        CoreInstance coreInstance=CoreInstance.getInstance();
        coreInstance.loginUser=user;
        DeviceInfo deviceInfo=new DeviceInfo();
        deviceInfo.initDev(user.userId);
        coreInstance.localDev=deviceInfo;
        coreInstance.workStatus=new WorkStatus();
    }
}
