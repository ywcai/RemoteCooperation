package ywcai.ls.remote.main.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ywcai.ls.remote.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isRecovery;
    private List<Fragment> fragments = new ArrayList<>();
    private List<TextView> nav = new ArrayList<>();
    private int currentPage = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
        Bundle b = this.getIntent().getExtras();
        isRecovery = b.getBoolean("isRecovery");
        setContentView(R.layout.activity_main);
        InstallFragment();
        selectFragment(0);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(
                new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bot_nav_1:
                selectFragment(0);
                break;
            case R.id.bot_nav_2:
                selectFragment(1);
                break;
            case R.id.bot_nav_3:
                selectFragment(2);
                break;
            case R.id.bot_nav_4:
                selectFragment(3);
                break;
        }
    }

    private void InstallFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragmentDev netWorkFragment = new MainFragmentDev();
        Bundle b = new Bundle();
        b.putBoolean("isRecovery", isRecovery);
        netWorkFragment.setArguments(b);
        Fragment reserveFragment = new Fragment();
        Fragment reserveFragment2 = new Fragment();
        Fragment reserveFragment3 = new Fragment();
        transaction.add(R.id.main_fragment_container, netWorkFragment);
        transaction.add(R.id.main_fragment_container, reserveFragment);
        transaction.add(R.id.main_fragment_container, reserveFragment2);
        transaction.add(R.id.main_fragment_container, reserveFragment3);
        fragments.add(netWorkFragment);
        fragments.add(reserveFragment);
        fragments.add(reserveFragment2);
        fragments.add(reserveFragment3);
        transaction.hide(netWorkFragment);
        transaction.hide(reserveFragment);
        transaction.hide(reserveFragment2);
        transaction.hide(reserveFragment3);
        transaction.commit();
        TextView nav_1 = (TextView) findViewById(R.id.bot_nav_1);
        TextView nav_2 = (TextView) findViewById(R.id.bot_nav_2);
        TextView nav_3 = (TextView) findViewById(R.id.bot_nav_3);
        TextView nav_4 = (TextView) findViewById(R.id.bot_nav_4);
        nav_1.setOnClickListener(this);
        nav_2.setOnClickListener(this);
        nav_3.setOnClickListener(this);
        nav_4.setOnClickListener(this);
        nav.add(nav_1);
        nav.add(nav_2);
        nav.add(nav_3);
        nav.add(nav_4);
        nav_1.setTextColor(0xFF666967);
        nav_1.setTextSize(12);
        nav_2.setTextColor(0xFF666967);
        nav_2.setTextSize(12);
        nav_3.setTextColor(0xFF666967);
        nav_3.setTextSize(12);
        nav_4.setTextColor(0xFF666967);
        nav_4.setTextSize(12);
    }

    private void selectFragment(int selectPage) {
        if (selectPage != currentPage) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.hide(fragments.get(currentPage));
            transaction.show(fragments.get(selectPage));
            transaction.commit();
            nav.get(currentPage).setTextColor(0xFF666967);
            nav.get(selectPage).setTextColor(0xFF3eb875);
            nav.get(currentPage).setTextSize(12);
            nav.get(selectPage).setTextSize(14);
            currentPage = selectPage;
        }
    }


}
