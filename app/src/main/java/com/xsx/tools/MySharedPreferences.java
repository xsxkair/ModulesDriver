package com.xsx.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.administrator.modulesdriver.R;
import com.xsx.entitys.EthernetConfig;

public class MySharedPreferences {

    private final static String settingFileName = "xsx_set";

    public static void putValue(Context context, String key, int value){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putInt(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, boolean value){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putBoolean(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, float value){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putFloat(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, long value){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putLong(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, String value){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }

    public static int getValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    public static boolean getValue(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    public static String getValue(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    public static float getValue(Context context, String key, float defValue) {
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        float value = sp.getFloat(key, defValue);
        return value;
    }

    public static long getValue(Context context, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        long value = sp.getLong(key, defValue);
        return value;
    }

    //ethernet config action
    public static void getEthernetConfigValue(Context context, EthernetConfig ethernetConfig){
        SharedPreferences sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE);
        ethernetConfig.setDhcp(sp.getBoolean("dhcp", context.getResources().getBoolean(R.bool.eth_def_connect_type)));
        ethernetConfig.setIpv4(sp.getString("ipv4", context.getResources().getString(R.string.eth_def_ip)));
        ethernetConfig.setDns(sp.getString("dns", context.getResources().getString(R.string.eth_def_dns)));
        ethernetConfig.setGateway(sp.getString("gateway", context.getResources().getString(R.string.eth_def_gateway)));
    }

    public static void setEthernetConfigValue(Context context, EthernetConfig ethernetConfig){
        Editor sp = context.getSharedPreferences(settingFileName, Context.MODE_PRIVATE).edit();
        sp.putBoolean("dhcp", ethernetConfig.isDhcp());
        sp.putString("ipv4", ethernetConfig.getIpv4());
        sp.putString("dns", ethernetConfig.getDns());
        sp.putString("gateway", ethernetConfig.getGateway());
        sp.commit();
    }
}
