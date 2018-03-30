package com.example.administrator.modulesdriver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.perporty.ChangeListener;
import com.xsx.perporty.IntegerPerporty;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SerialTestActivity extends AppCompatActivity {

    @BindView(R.id.select_serial_spinner)
    Spinner selectSerialSpinner;
    @BindView(R.id.select_serial_baud_spinner)
    Spinner selectSerialBaudSpinner;
    @BindView(R.id.open_serial_button)
    Button openSerialButton;
    @BindView(R.id.serial_recv_edittext)
    TextView serialRecvEdittext;
    @BindView(R.id.serial_send_edittext)
    EditText serialSendEdittext;
    @BindView(R.id.serial_send_button)
    Button serialSendButton;

    @BindString(R.string.close_serial_button_text)
    String closeSerialButtonText;
    @BindString(R.string.open_serial_button_text)
    String openSerialButtonText;

    private IntegerPerporty deviceFilePerporty = new IntegerPerporty(-1);                    //-1 :no device
    private final int BUFSIZE = 1024;
    private byte[] buf = new byte[BUFSIZE];
    private int readDataSize = 0;
    private String readStr = null;


    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_test);
        ButterKnife.bind(this);

        deviceFilePerporty.addChangeListener(new ChangeListener<Integer>() {
            @Override
            public void changed(Integer oldValue, Integer newValue) {
                if(newValue != null){
                    if(newValue < 0){
                        openSerialButton.setText(R.string.close_serial_button_text);
                        selectSerialSpinner.setEnabled(false);
                        serialSendButton.setEnabled(false);
                    }
                    else{
                        selectSerialSpinner.setEnabled(true);
                        openSerialButton.setText(R.string.open_serial_button_text);
                        serialSendButton.setEnabled(true);
                    }

                }
            }
        });

        timer.schedule(task, 0, 100);
    }

    @Override
    protected void onDestroy() {

        if(timer != null){
            timer.cancel();
            timer = null;
        }

        if (deviceFilePerporty.getValue() >= 0) {
            HardwareControler.close(deviceFilePerporty.getValue());
            deviceFilePerporty.setValue(-1);
        }

        super.onDestroy();
    }

    public void openSerial(View view){

        if(closeSerialButtonText.equals(openSerialButton.getText())){
            HardwareControler.close(deviceFilePerporty.getValue());
            deviceFilePerporty.setValue(-1);
        }
        else
            deviceFilePerporty.setValue(HardwareControler.openSerialPort(selectSerialSpinner.getSelectedItem().toString(), Integer.valueOf(selectSerialBaudSpinner.getSelectedItem().toString()), 8, 1));
    }

    public void sendMsg(View view){

         String str = serialSendEdittext.getText().toString();
            if (str.length() > 0) {
                int ret = HardwareControler.write(deviceFilePerporty.getValue(), str.getBytes());
                if (ret > 0)
                    Toast.makeText(this, "send", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Fail to send!",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"no message to be send", Toast.LENGTH_SHORT).show();
    }

    public void clearRecvMsg(View view){
        serialRecvEdittext.setText(null);
    }

    private Handler recvHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    //check device is opened
                    if(deviceFilePerporty.getValue() < 0)
                        break;

                    //check serial has data to be read
                    if(HardwareControler.select(deviceFilePerporty.getValue(), 0, 0) == 1)
                    {
                        readDataSize = HardwareControler.read(deviceFilePerporty.getValue(), buf, BUFSIZE);
                        if(readDataSize > 0)
                        {
                            readStr = new String(buf, 0, readDataSize);
                            serialRecvEdittext.append(readStr);
                        }
                    }

                    break;
            }

            super.handleMessage(msg);
        }
    };

    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            recvHandler.sendMessage(message);
        }
    };
}
