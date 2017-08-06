package ywcai.ls.remote.main.model;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


import ywcai.ls.remote.R;
import ywcai.ls.remote.global.model.DeviceInfo;
import ywcai.ls.remote.global.model.instance.CoreInstance;
import ywcai.ls.remote.main.presenter.OnItemClickListener;
import ywcai.ls.remote.socket.cfg.ResultCode;
import ywcai.ls.remote.welcome.model.instance.MainApplication;


public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListViewHolder> implements View.OnClickListener {
    private List<DeviceInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private CoreInstance coreInstance = CoreInstance.getInstance();

    public DeviceListAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public DeviceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.listview_main_devices, parent,
                false);
        DeviceListViewHolder holder = new DeviceListViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceListViewHolder deviceListViewTemp, int position) {
        deviceListViewTemp.itemView.setTag(position);
        DeviceInfo deviceInfo = list.get(position);
        deviceListViewTemp.deviceName_TV.setText(deviceInfo.deviceName);
        deviceListViewTemp.deviceStatus_TV.setText(deviceInfo.status);
        deviceListViewTemp.deviceCode_TV.setText(deviceInfo.accessCode);
        deviceListViewTemp.deviceId_TV.setText(deviceInfo.deviceId);
        if (deviceInfo.userId.equals(coreInstance.localDev.userId)) {
            deviceListViewTemp.deviceOwner_TV.setText("个人设备");
        } else {
            deviceListViewTemp.deviceOwner_TV.setText("临时设备");
        }
        if (deviceInfo.deviceMode.equals(ResultCode.device_mode_pc)) {
            deviceListViewTemp.deviceMode_img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mm_login));
        }
        if (deviceInfo.deviceMode.equals(ResultCode.device_mode_mobile)) {
            deviceListViewTemp.deviceMode_img.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance().getApplicationContext(), R.drawable.qq_login));
        }
        if (deviceInfo.status.equals(ResultCode.device_status_online)) {
            deviceListViewTemp.deviceStatus_TV.setTextColor(Color.GREEN);
        }
        if (deviceInfo.status.equals(ResultCode.device_status_link)) {
            deviceListViewTemp.deviceStatus_TV.setTextColor(Color.RED);
        }
        if (deviceInfo.status.equals(ResultCode.device_status_offline)) {
            deviceListViewTemp.deviceStatus_TV.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.OnClickItem(v, (int) v.getTag());
        }
    }

    public void setOnclickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
