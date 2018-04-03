package com.example.administrator.modulesdriver;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.dao.UserDao;
import com.xsx.entitys.User;
import com.xsx.perporty.ChangeListener;
import com.xsx.perporty.IntegerPerporty;
import com.xsx.perporty.Perporty;
import com.xsx.perporty.StringPerporty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test_item_listview)
    ListView testItemListView;

    private int[] itemIdArray = {R.string.list_gpio_item, R.string.list_serial_item, R.string.list_iic_item, R.string.list_ad_item, R.string.list_disk_item, R.string.list_database_item};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        testItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemid = itemIdArray[position];

                switch (itemid){
                    case R.string.list_gpio_item:
                        startActivity(new Intent(MainActivity.this, GpioTestActivity.class));
                        break;

                    case R.string.list_serial_item:
                        startActivity(new Intent(MainActivity.this, SerialTestActivity.class));
                        break;

                    case R.string.list_iic_item:
                        break;

                    case R.string.list_ad_item:
                        break;

                    case R.string.list_disk_item:
                        break;

                    case R.string.list_database_item:
                        startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
                        break;
                }
            }
        });

       // timer.schedule(task, 0, 10000);

        //setEthernet();
    }


    private String getLocalIp() {

        try {
            // 获取本地设备的所有网络接口
            Enumeration<NetworkInterface> enumerationNi = NetworkInterface.getNetworkInterfaces();
            while (enumerationNi.hasMoreElements()) {
                NetworkInterface networkInterface = enumerationNi.nextElement();
                String interfaceName = networkInterface.getDisplayName();
                Log.d("xsx", "网络名字" + interfaceName);

                // 如果是有限网卡
                if (interfaceName.equals("eth0")) {
                    Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses();

                    while (enumIpAddr.hasMoreElements()) {
                        // 返回枚举集合中的下一个IP地址信息
                        InetAddress inetAddress = enumIpAddr.nextElement();

                        // 不是回环地址，并且是ipv4的地址
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            Log.i("xsx", inetAddress.getHostAddress() + "   ");

                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }

       } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";

    }

    private void setEthernet(){
       // EthernetManager mEthManager;
       // ConnectivityManager mCM;



        try {
            //获取ETHERNET_SERVICE参数
            String  ETHERNET_SERVICE = (String) Context.class.getField("ETHERNET_SERVICE").get(null);

            Class<?> ethernetManagerClass = Class.forName("android.net.EthernetManager");
            Class<?> iEthernetManagerClass = Class.forName("android.net.IEthernetManager");
            Class<?> staticIpConfigClass = Class.forName("android.net.StaticIpConfiguration");
            Class<?> ipConfigurationClass = Class.forName("android.net.IpConfiguration");

            //获取ethernetManager服务对象
            Object ethernetManager = getSystemService(ETHERNET_SERVICE);

            Object ipConfiguration = ethernetManagerClass.getDeclaredMethod("getConfiguration").invoke(ethernetManager);

            Object staticIpConfig = ipConfigurationClass.getDeclaredMethod("getStaticIpConfiguration").invoke(ipConfiguration);

            Field f = staticIpConfigClass.getField("ipAddress");
            Log.d("xsx", "ipAddress : "+ f.get(staticIpConfig));

           /* Log.d("xsx", "ETHERNET_SERVICE : "+ ETHERNET_SERVICE);

            //获取在EthernetManager中的抽象类mService成员变量
            Field mService = ethernetManagerClass.getDeclaredField("mService");

            //修改private权限
            mService.setAccessible(true);

            //获取抽象类的实例化对象
            Object mServiceObject = mService.get(ethernetManager);

            Object ethernetManagerInstance = ethernetManagerClass.getDeclaredConstructor(Context.class, iEthernetManagerClass).newInstance(this, mServiceObject);

            Method[] methods = iEthernetManagerClass.getDeclaredMethods();

            for (Method ms : methods) {

                if (ms.getName().equals("setEthernetEnabled")){

                    ms.invoke(mServiceObject,true);

                    Log.d("xsx", "mServiceObject : "+mServiceObject);

                }

            }



            Constructor<?> staticIpConfigConstructor = staticIpConfig.getDeclaredConstructor(staticIpConfig);

            Object staticIpConfigInstance = staticIpConfig.newInstance();

            //获取staticIpConfig中所有的成员变量
            Field[] declaredFields = staticIpConfigInstance.getClass().getDeclaredFields();

            for (Field f :declaredFields ) {
                Log.d("xsx", String.format("feild: %s, value:%s", f.getName(), f.get(staticIpConfigInstance)));
                //设置成员变量的值
                if (f.getName().equals("ipAddress")){

                    //设置IP地址和子网掩码
                    f.set(staticIpConfigInstance,linkAddress);

                }else if (f.getName().equals("gateway")){

                    //设置默认网关
//                    f.set(staticIpConfigInstance, inetAddress);

                }else if (f.getName().equals("domains")){

                    f.set(staticIpConfigInstance,"");

                }else if (f.getName().equals("dnsServers")){

                //设置DNS
                    f.set(staticIpConfigInstance,new ArrayList<InetAddress>());

                }

            }*/

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
