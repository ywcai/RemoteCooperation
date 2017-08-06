package ywcai.ls.remote.welcome.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ywcai.ls.remote.global.model.User;
import ywcai.ls.remote.R;
import ywcai.ls.remote.global.model.GlobalEvent;
import ywcai.ls.remote.global.model.GlobalEventT;
import ywcai.ls.remote.global.presenter.ActivityRouter;
import ywcai.ls.remote.global.presenter.inf.RouterInf;
import ywcai.ls.remote.welcome.presenter.LoadUserCache;

public class WelComeActivity extends AppCompatActivity {
    RouterInf router;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.welcome_activity);
        router=new ActivityRouter(this);
        new LoadUserCache();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateView(GlobalEvent event) {
        switch (event.type) {
            case GlobalEventT.is_login:
                router.createNewSession((User)event.obj);
                break;
            case GlobalEventT.un_login:
                router.startLogin();
                break;
            case GlobalEventT.is_Run:
                router.recoverySession();
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
