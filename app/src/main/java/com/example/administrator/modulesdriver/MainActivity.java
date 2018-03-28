package com.example.administrator.modulesdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ledTest_Item)
    TextView ledTestItem;
    @BindView(R.id.serialTest_Item)
    TextView serialTestItem;
    @BindView(R.id.IICTest_Item)
    TextView IICTestItem;
    @BindView(R.id.ADTest_Item)
    TextView ADTestItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ledTest_Item, R.id.serialTest_Item, R.id.IICTest_Item, R.id.ADTest_Item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ledTest_Item:
                startActivity(new Intent(this, LedTestActivity.class));
                break;
            case R.id.serialTest_Item:
                break;
            case R.id.IICTest_Item:
                break;
            case R.id.ADTest_Item:
                break;
        }
    }
}
