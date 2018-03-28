package com.example.administrator.modulesdriver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.friendlyarm.AndroidSDK.HardwareControler;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018\3\27 0027.
 */

public class LedTestActivity extends Activity {

    @BindView(R.id.gpio_group_select_spinner)
    Spinner gpioGroupSelectSpinner;

    @BindViews({R.id.gpio_0_switch, R.id.gpio_1_switch, R.id.gpio_2_switch, R.id.gpio_3_switch, R.id.gpio_4_switch, R.id.gpio_5_switch, R.id.gpio_6_switch, R.id.gpio_7_switch, R.id.gpio_8_switch, R.id.gpio_9_switch, R.id.gpio_10_switch, R.id.gpio_11_switch, R.id.gpio_12_switch, R.id.gpio_13_switch, R.id.gpio_14_switch, R.id.gpio_15_switch, R.id.gpio_16_switch, R.id.gpio_17_switch, R.id.gpio_18_switch, R.id.gpio_19_switch, R.id.gpio_20_switch, R.id.gpio_21_switch, R.id.gpio_22_switch, R.id.gpio_23_switch, R.id.gpio_24_switch, R.id.gpio_25_switch, R.id.gpio_26_switch, R.id.gpio_27_switch, R.id.gpio_28_switch, R.id.gpio_29_switch, R.id.gpio_30_switch, R.id.gpio_31_switch})
    List<Switch> gpioSwitchList;

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

    @OnClick({R.id.gpio_0_switch, R.id.gpio_1_switch, R.id.gpio_2_switch, R.id.gpio_3_switch, R.id.gpio_4_switch, R.id.gpio_5_switch, R.id.gpio_6_switch, R.id.gpio_7_switch, R.id.gpio_8_switch, R.id.gpio_9_switch, R.id.gpio_10_switch, R.id.gpio_11_switch, R.id.gpio_12_switch, R.id.gpio_13_switch, R.id.gpio_14_switch, R.id.gpio_15_switch, R.id.gpio_16_switch, R.id.gpio_17_switch, R.id.gpio_18_switch, R.id.gpio_19_switch, R.id.gpio_20_switch, R.id.gpio_21_switch, R.id.gpio_22_switch, R.id.gpio_23_switch, R.id.gpio_24_switch, R.id.gpio_25_switch, R.id.gpio_26_switch, R.id.gpio_27_switch, R.id.gpio_28_switch, R.id.gpio_29_switch, R.id.gpio_30_switch, R.id.gpio_31_switch})
    public void onViewClicked(View view) {
  //      int viewId = view.getId();
  //      if(viewId != R.id.gpio_group_select_spinner)
           Toast.makeText(LedTestActivity.this, String.format("你点击了：%s, 状态：%s", ((Switch)view).getText(), ((Switch)view).isChecked()), Toast.LENGTH_LONG);
  /*      switch (view.getId()) {
            case R.id.gpio_group_select_spinner:
                break;
            case R.id.gpio_0_switch:
                Toast.makeText(LedTestActivity.this, String.format("你点击了：%s, 状态：%s", ((Switch)view).getText(), ((Switch)view).isChecked()), Toast.LENGTH_LONG);
                break;
            case R.id.gpio_1_switch:
                break;
            case R.id.gpio_2_switch:
                break;
            case R.id.gpio_3_switch:
                break;
            case R.id.gpio_4_switch:
                break;
            case R.id.gpio_5_switch:
                break;
            case R.id.gpio_6_switch:
                break;
            case R.id.gpio_7_switch:
                break;
            case R.id.gpio_8_switch:
                break;
            case R.id.gpio_9_switch:
                break;
            case R.id.gpio_10_switch:
                break;
            case R.id.gpio_11_switch:
                break;
            case R.id.gpio_12_switch:
                break;
            case R.id.gpio_13_switch:
                break;
            case R.id.gpio_14_switch:
                break;
            case R.id.gpio_15_switch:
                break;
            case R.id.gpio_16_switch:
                break;
            case R.id.gpio_17_switch:
                break;
            case R.id.gpio_18_switch:
                break;
            case R.id.gpio_19_switch:
                break;
            case R.id.gpio_20_switch:
                break;
            case R.id.gpio_21_switch:
                break;
            case R.id.gpio_22_switch:
                break;
            case R.id.gpio_23_switch:
                break;
            case R.id.gpio_24_switch:
                break;
            case R.id.gpio_25_switch:
                break;
            case R.id.gpio_26_switch:
                break;
            case R.id.gpio_27_switch:
                break;
            case R.id.gpio_28_switch:
                break;
            case R.id.gpio_29_switch:
                break;
            case R.id.gpio_30_switch:
                break;
            case R.id.gpio_31_switch:
                break;
        }*/
    }
}
