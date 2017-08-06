package ywcai.ls.remote.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import ywcai.ls.controls.loader.LoaderView;
import ywcai.ls.remote.R;
import ywcai.ls.remote.global.model.BusinessType;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.GlobalEvent;
import ywcai.ls.remote.global.model.WorkStatus;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.main.model.DeviceListAdapter;
import ywcai.ls.remote.main.presenter.BusinessProcess;
import ywcai.ls.remote.main.presenter.inf.BusinessProcessInf;
import ywcai.ls.remote.main.presenter.OnItemClickListener;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.socket.cfg.ViewChangeT;
import ywcai.ls.remote.socket.presenter.LinkProcess;
import ywcai.ls.remote.socket.presenter.inf.LinkProcessInf;


public class MainFragmentDev extends Fragment {
    private List<DeviceInfo> devices = new ArrayList();
    private RecyclerView deviceListView;
    private TextView tip;
    private DeviceListAdapter dAdapter;
    private boolean isRecovery;
    private BusinessProcessInf businessProcessInf;
    private LinkProcessInf linkProcessInf;
    private View view;
    WorkStatus workStatus = CoreInstance.getInstance().workStatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle b = this.getArguments();
        isRecovery = b.getBoolean("isRecovery");
        Log.i("ywcai", "onCreateView: " + isRecovery);
        view = inflater.inflate(R.layout.fragment_main_device_list, container, false);
        InitObject();
        InitView();
        InitToolBar();
        return view;
    }

    private void InitObject() {
        businessProcessInf = new BusinessProcess();
        linkProcessInf=new LinkProcess();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ywcai", "isRecovery: " + isRecovery);
        if (isRecovery) {
            updateDevices();
        } else {
            isRecovery = true;
            linkProcessInf.startService(this.getContext());
        }
    }

    private void InitToolBar() {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        mToolbar.setTitle("");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        break;
                    case R.id.menu2:
                        break;
                    case R.id.menu3:
                        showBusinessMenu();
                        break;
                    case R.id.menu4:
                        cutProcess();
                        break;
                }
                return false;
            }
        });
    }

    private void cutProcess() {
        if(workStatus.lastStatus.equals(ResultCode.device_status_link))
        {
            showLoader();
            linkProcessInf.cutLink();
        }
    }

    private void InitView() {
        Glide.with(this).load(R.drawable.main_head)
                .bitmapTransform(new BlurTransformation(this.getContext(), 50))
                .into((ImageView) view.findViewById(R.id.main_head_bg));
        tip = (TextView) view.findViewById(R.id.main_top_tip);
        deviceListView = (RecyclerView) view.findViewById(R.id.devices);
        deviceListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dAdapter = new DeviceListAdapter(devices, MainFragmentDev.this.getContext());
        dAdapter.setOnclickListener(new OnItemClickListener() {
            @Override
            public void OnClickItem(View v, int pos) {
                showToastMenu(pos);
                Log.i("ywcai", "OnClickItem: ");
            }
        });
        deviceListView.setAdapter(dAdapter);
    }

    private void showToastMenu(int pos) {
        final DeviceInfo dev=devices.get(pos);
        if(!dev.status.equals(ResultCode.device_status_online))
        {
            return;
        }
        final MaterialDialog materialdialog=new MaterialDialog(this.getContext());
        materialdialog.setTitle("与设备 【"+dev.deviceName+"】 建立应用连接!");
        materialdialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialog.dismiss();
            }
        });
        materialdialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialog.dismiss();
                showLoader();
                linkProcessInf.createLink(dev);
            }
        });
        materialdialog.setCanceledOnTouchOutside(true);
        materialdialog.show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onDestroy() {
        linkProcessInf.delDevicesCache();
        Log.i("ywcai", "onDestroy: ");
        super.onDestroy();
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDeviceList(GlobalEvent event) {
        switch (event.type) {
            case ViewChangeT.update_device_list:
                Log.i("ywcai", event.tip);
                tip.setVisibility(View.GONE);
                break;
            case ViewChangeT.update_device_single:
                Log.i("ywcai", event.tip);
                tip.setVisibility(View.GONE);
                break;
            case ViewChangeT.session_closed:
                Log.i("ywcai", event.tip);
                showInfo(event.tip);
                break;
            case ViewChangeT.session_create_fail:
                Log.i("ywcai", event.tip);
                showInfo(event.tip);
                break;
            case ViewChangeT.reg_fail:
                Log.i("ywcai", event.tip);
                showInfo(event.tip);
                break;
            case ViewChangeT.create_process_success:
                Log.i("ywcai", event.tip);
                hideLoader();
                showToast(event.tip);
                break;
            case ViewChangeT.create_process_fail:
                Log.i("ywcai", event.tip);
                hideLoader();
                showToast(event.tip);
                break;
            case ViewChangeT.cut_process_success:
                Log.i("ywcai", event.tip);
                hideLoader();
                showToast(event.tip);
                break;

        }
        updateDevices();
    }

    private void updateDevices() {
        //日志中读取LIST的数据
        devices.clear();
        devices.addAll(linkProcessInf.getDevicesForCache());
        if (devices.size() > 0) {
            DeviceInfo dev = devices.get(0);
            updateHead(dev);
            devices.remove(0);
        } else {
            updateHead(CoreInstance.getInstance().localDev);
        }
        dAdapter.notifyDataSetChanged();
    }

    private void updateHead(DeviceInfo dev_local) {
        AppCompatTextView user_name = (AppCompatTextView) view.findViewById(R.id.main_head_user_name);
//        AppCompatTextView local_ip = (AppCompatTextView) view.findViewById(R.id.main_head_local_ip);
        AppCompatTextView dev_id = (AppCompatTextView) view.findViewById(R.id.main_head_dev_id);
        AppCompatTextView dev_name = (AppCompatTextView) view.findViewById(R.id.main_head_dev_name);
        AppCompatTextView access_code = (AppCompatTextView) view.findViewById(R.id.main_head_access_code);
        AppCompatTextView dev_status = (AppCompatTextView) view.findViewById(R.id.main_head_dev_status);
        user_name.setText(dev_local.userId);
//        local_ip.setText(dev_local.remoteIp);
        dev_id.setText(dev_local.deviceId);
        dev_name.setText(dev_local.deviceName);
        access_code.setText(dev_local.accessCode);
        dev_status.setText(dev_local.status);
    }

    private void showToast(String tip) {
        Toast.makeText(this.getContext(),tip,Toast.LENGTH_LONG).show();
    }
    private void showInfo(String string) {
        tip.setText(string);
        tip.setVisibility(View.VISIBLE);
    }
    private void showLoader()
    {
        LoaderView loaderView=(LoaderView)view.findViewById(R.id.main_process_loader);
        loaderView.setVisibility(View.VISIBLE);
    }
    private void hideLoader()
    {
        LoaderView loaderView=(LoaderView)view.findViewById(R.id.main_process_loader);
        loaderView.setVisibility(View.INVISIBLE);
    }
    private boolean isCanUseBusiness() {
        if(!workStatus.lastStatus.equals(ResultCode.device_status_link))
        {
            return false;
        }
        if(workStatus.businessType != BusinessType.FREE)
        {
            return false;
        }
        return true;
    }
    private void showBusinessMenu() {
        if(!isCanUseBusiness())
        {
            return;
        }
        final RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.main_menu);
        rl.setVisibility(View.VISIBLE);
        RelativeLayout rl_mask = (RelativeLayout) view.findViewById(R.id.main_mask);
        rl_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
            }
        });
        LinearLayout op_control_remote = (LinearLayout) view.findViewById(R.id.op_control_remote);
        LinearLayout op_pull_desk = (LinearLayout) view.findViewById(R.id.op_pull_desk);
        LinearLayout op_pull_camera = (LinearLayout) view.findViewById(R.id.op_pull_camera);
        LinearLayout op_push_desk = (LinearLayout) view.findViewById(R.id.op_push_desk);
        LinearLayout op_push_camera = (LinearLayout) view.findViewById(R.id.op_push_camera);
        op_control_remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                showLoader();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        businessProcessInf.controlRemote();
                    }
                }).start();
            }
        });
        op_pull_desk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                showLoader();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        businessProcessInf.pullDesk();
                    }
                }).start();
            }
        });
        op_pull_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                showLoader();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        businessProcessInf.pullCamera();
                    }
                }).start();
            }
        });
        op_push_desk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                showLoader();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        businessProcessInf.pushDesk();
                    }
                }).start();
            }
        });
        op_push_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                showLoader();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        businessProcessInf.pushCamera();
                    }
                }).start();
            }
        });
    }
}
