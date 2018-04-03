package com.example.administrator.modulesdriver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xsx.dao.UserDao;
import com.xsx.entitys.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataBaseActivity extends AppCompatActivity {

    @BindView(R.id.dataListView)
    ListView dataListView;
    @BindView(R.id.insertButton)
    Button insertButton;
    @BindView(R.id.deleteButton)
    Button deleteButton;
    @BindView(R.id.freshButton)
    Button freshButton;

    private List<User> userList;
    private List<Map<String, Object>> listItem = new ArrayList<>();
    private UserDao userDao;
    private String[] userFieldName = new String[]{"id", "name", "age", "sex", "phone"};
    private int[] itemIds = new int[]{R.id.userId, R.id.userName, R.id.userAge, R.id.userSex, R.id.userPhone};
    SimpleAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        ButterKnife.bind(this);

        userDao = new UserDao(this);

        showDatas();
    }

    private void showDatas() {
        userList = userDao.all();
        listItem.clear();

        for (User user : userList
                ) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put(userFieldName[0], user.getId());
            showitem.put(userFieldName[1], user.getName());
            showitem.put(userFieldName[2], user.getAge());
            showitem.put(userFieldName[3], user.getSex());
            showitem.put(userFieldName[4], user.getPhone());
            listItem.add(showitem);
        }

        myAdapter = new SimpleAdapter(this, listItem, R.layout.data_base_list_item, userFieldName, itemIds);
        dataListView.setAdapter(myAdapter);
    }

    @OnClick({R.id.insertButton, R.id.deleteButton, R.id.freshButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.insertButton:
                userDao.add(new User("xsx", "1", "m", "111"));
                break;
            case R.id.deleteButton:
                Map<String, Object> showitem = listItem.get(0);
                showitem.put("name", "zx2");
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.freshButton:
                showDatas();
                break;
        }
    }
}
