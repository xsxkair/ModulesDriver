package com.example.administrator.modulesdriver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.friendlyarm.AndroidSDK.GPIOEnum;
import com.friendlyarm.AndroidSDK.HardwareControler;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018\3\27 0027.
 */

public class LedTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_led);
        ButterKnife.bind(this);

        int boardType = HardwareControler.getBoardType();
        Log.d("debug", String.format("主板型号：%d", boardType));

        /*if(0 == HardwareControler.exportGPIOPin(58))
            infoTextView.setText("open success");
        else
            infoTextView.setText("open fail");
        HardwareControler.setGPIODirection(58, GPIOEnum.OUT);
        HardwareControler.setGPIOValue(58, GPIOEnum.LOW);*/
    }
}
