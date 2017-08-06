package ywcai.ls.remote.main.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ywcai.ls.remote.R;

public class DeviceListViewHolder extends RecyclerView.ViewHolder {
    public TextView deviceCode_TV, deviceId_TV, deviceName_TV, deviceStatus_TV, deviceOwner_TV;
    public CircleImageView deviceMode_img;

    public DeviceListViewHolder(View itemView) {
        super(itemView);
        deviceMode_img = (CircleImageView) itemView.findViewById(R.id.device_mode);
        deviceCode_TV = (TextView) itemView.findViewById(R.id.device_code);
        deviceId_TV = (TextView) itemView.findViewById(R.id.device_id);
        deviceName_TV = (TextView) itemView.findViewById(R.id.device_name);
        deviceStatus_TV = (TextView) itemView.findViewById(R.id.device_status);
        deviceOwner_TV = (TextView) itemView.findViewById(R.id.device_owner);
    }
}

