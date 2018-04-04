package com.example.administrator.modulesdriver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xsx.entitys.EthernetConfig;
import com.xsx.tools.EthernetSet;
import com.xsx.tools.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Eth0SettingActivity extends Activity {

    @BindView(R.id.dhcp_radio)
    RadioButton dhcpRadio;
    @BindView(R.id.manual_radio)
    RadioButton manualRadio;
    @BindView(R.id.con_type)
    RadioGroup conType;
    @BindView(R.id.ipaddr_edit)
    EditText ipaddrEdit;
    @BindView(R.id.eth_dns_edit)
    EditText ethDnsEdit;
    @BindView(R.id.eth_gw_edit)
    EditText ethGwEdit;
    @BindView(R.id.enterprise_wrapper)
    LinearLayout enterpriseWrapper;
    @BindView(R.id.ethCancelConfigButton)
    Button ethCancelConfigButton;
    @BindView(R.id.ethSaveConfigButton)
    Button ethSaveConfigButton;
    @BindView(R.id.netmask_edit)
    EditText netmaskEdit;

    private EthernetConfig ethernetConfig = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth0_setting);
        ButterKnife.bind(this);

        ethernetConfig = new EthernetConfig();

        MySharedPreferences.getEthernetConfigValue(this, ethernetConfig);
        showEthernetConfig();

        conType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dhcp_radio:
                        ethernetConfig.setDhcp(true);
                        break;
                    case R.id.manual_radio:
                        ethernetConfig.setDhcp(false);
                        break;
                }
                ethernetConfig.setIpv4(getResources().getString(R.string.eth_def_ip));
                ethernetConfig.setDns(getResources().getString(R.string.eth_def_dns));
                ethernetConfig.setGateway(getResources().getString(R.string.eth_def_gateway));
                ethernetConfig.setNetmask(getResources().getString(R.string.eth_def_mask));

                showEthernetConfig();
            }
        });


    }

    @OnClick({R.id.ethCancelConfigButton, R.id.ethSaveConfigButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ethCancelConfigButton:
                this.finish();
                break;
            case R.id.ethSaveConfigButton:
                int error = ipValidate();
                if (error == 0) {
                    MySharedPreferences.setEthernetConfigValue(this, ethernetConfig);
                    EthernetSet.setSystemEthernet(this, ethernetConfig);
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                } else if (error == 1)
                    Toast.makeText(this, "请正确输入 ip", Toast.LENGTH_SHORT).show();
                else if (error == 2)
                    Toast.makeText(this, "请正确输入 dns", Toast.LENGTH_SHORT).show();
                else if (error == 3)
                    Toast.makeText(this, "请正确输入 Gateway", Toast.LENGTH_SHORT).show();
                else if (error == 4)
                    Toast.makeText(this, "请正确输入 网关", Toast.LENGTH_SHORT).show();
                else if (error == 5)
                    Toast.makeText(this, "请检查", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showEthernetConfig() {
        boolean isDhcp = ethernetConfig.isDhcp();

        dhcpRadio.setChecked(isDhcp);
        manualRadio.setChecked(!isDhcp);
        ipaddrEdit.setEnabled(!isDhcp);
        ethDnsEdit.setEnabled(!isDhcp);
        ethGwEdit.setEnabled(!isDhcp);

        ipaddrEdit.setText(ethernetConfig.getIpv4());
        ethDnsEdit.setText(ethernetConfig.getDns());
        ethGwEdit.setText(ethernetConfig.getGateway());
        netmaskEdit.setText(ethernetConfig.getNetmask());
    }

    private int ipValidate() {
        if (ethernetConfig.isDhcp())
            return 0;
        else {

            ethernetConfig.setIpv4(ipaddrEdit.getText().toString());
            ethernetConfig.setDns(ethDnsEdit.getText().toString());
            ethernetConfig.setGateway(ethGwEdit.getText().toString());
            ethernetConfig.setNetmask(netmaskEdit.getText().toString());
            if (isIpAddress(ipaddrEdit.getText().toString()) && isIpAddress(ethDnsEdit.getText().toString()) && isIpAddress(ethGwEdit.getText().toString()))
                return 0;
            else if (!isIpAddress(ipaddrEdit.getText().toString()))
                return 1;
            else if (!isIpAddress(ethDnsEdit.getText().toString()))
                return 2;
            else if (!isIpAddress(ethGwEdit.getText().toString()))
                return 3;
            else if (!isIpAddress(netmaskEdit.getText().toString()))
                return 4;
            else
                return 5;
        }
    }

    private boolean isIpAddress(String value) {
        int start = 0;
        int end = value.indexOf('.');
        int numBlocks = 0;

        while (start < value.length()) {
            if (end == -1) {
                end = value.length();
            }

            try {
                int block = Integer.parseInt(value.substring(start, end));
                if ((block > 255) || (block < 0)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            numBlocks++;

            start = end + 1;
            end = value.indexOf('.', start);
        }
        return numBlocks == 4;
    }
}
