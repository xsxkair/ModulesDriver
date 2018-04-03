package com.example.administrator.modulesdriver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.friendlyarm.AndroidSDK.GPIOEnum;
import com.friendlyarm.AndroidSDK.HardwareControler;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018\3\27 0027.
 */

public class GpioTestActivity extends Activity {

    @BindView(R.id.gpio_group_select_spinner)
    Spinner gpioGroupSelectSpinner;

    @BindViews({R.id.gpio_0_switch, R.id.gpio_1_switch, R.id.gpio_2_switch, R.id.gpio_3_switch, R.id.gpio_4_switch, R.id.gpio_5_switch, R.id.gpio_6_switch, R.id.gpio_7_switch, R.id.gpio_8_switch, R.id.gpio_9_switch, R.id.gpio_10_switch, R.id.gpio_11_switch, R.id.gpio_12_switch, R.id.gpio_13_switch, R.id.gpio_14_switch, R.id.gpio_15_switch, R.id.gpio_16_switch, R.id.gpio_17_switch, R.id.gpio_18_switch, R.id.gpio_19_switch, R.id.gpio_20_switch, R.id.gpio_21_switch, R.id.gpio_22_switch, R.id.gpio_23_switch, R.id.gpio_24_switch, R.id.gpio_25_switch, R.id.gpio_26_switch, R.id.gpio_27_switch, R.id.gpio_28_switch, R.id.gpio_29_switch, R.id.gpio_30_switch, R.id.gpio_31_switch})
    List<Switch> gpioSwitchList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gpio);
        ButterKnife.bind(this);

        int boardType = HardwareControler.getBoardType();
        Log.d("debug", String.format("主板型号：%d", boardType));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("xsx", "onRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("xsx", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("xsx", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("xsx", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("xsx", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("xsx", "onStart");
    }

    @OnClick({R.id.gpio_0_switch, R.id.gpio_1_switch, R.id.gpio_2_switch, R.id.gpio_3_switch, R.id.gpio_4_switch, R.id.gpio_5_switch, R.id.gpio_6_switch, R.id.gpio_7_switch, R.id.gpio_8_switch, R.id.gpio_9_switch, R.id.gpio_10_switch, R.id.gpio_11_switch, R.id.gpio_12_switch, R.id.gpio_13_switch, R.id.gpio_14_switch, R.id.gpio_15_switch, R.id.gpio_16_switch, R.id.gpio_17_switch, R.id.gpio_18_switch, R.id.gpio_19_switch, R.id.gpio_20_switch, R.id.gpio_21_switch, R.id.gpio_22_switch, R.id.gpio_23_switch, R.id.gpio_24_switch, R.id.gpio_25_switch, R.id.gpio_26_switch, R.id.gpio_27_switch, R.id.gpio_28_switch, R.id.gpio_29_switch, R.id.gpio_30_switch, R.id.gpio_31_switch})
    public void onViewClicked(View view) {

        int index = gpioSwitchList.indexOf(view);
        int pinNum = gpioGroupSelectSpinner.getSelectedItemPosition() * 32 + index;
        int value = GPIOEnum.LOW;

        if(((Switch)view).isChecked())
            value = GPIOEnum.LOW;
        else
            value = GPIOEnum.HIGH;

        writeGpioPinValue(pinNum, value);
    }

    private void writeGpioPinValue(int pin, int value)
    {
         if(0 != HardwareControler.exportGPIOPin(pin))
             Toast.makeText(GpioTestActivity.this, String.format("GPIO %d 导出失败！", pin), Toast.LENGTH_SHORT).show();
        else{
             HardwareControler.setGPIODirection(pin, GPIOEnum.OUT);
             HardwareControler.setGPIOValue(pin, value);
             HardwareControler.unexportGPIOPin(pin);
         }
    }
}
