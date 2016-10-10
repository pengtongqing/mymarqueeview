package com.example.mygreendao.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.mygreendao.DB.DBManager;
import com.example.mygreendao.R;
import com.example.mygreendao.model.Story;
import com.example.mygreendao.net.StoryInfoHelper;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private StoryInfoHelper storyInfoHelper;
  private List<Story> storyBeans = new ArrayList<Story>();
  private HandlerThread infoThread;
  private Handler infoHandler;
  private Button qureBtn;
  private DBManager dbManager;
  private Button insertBtn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initDate();
    initView();
  }

  private void initView() {
    qureBtn = (Button) findViewById(R.id.qureBtn);
    qureBtn.setOnClickListener(this);
    insertBtn = (Button) findViewById(R.id.insertBtn);
    insertBtn.setOnClickListener(this);
  }

  private void initDate() {
    storyInfoHelper = StoryInfoHelper.getInstance();
    infoThread = new HandlerThread("getStotyInfo");
    infoThread.start();
    infoHandler = new Handler(infoThread.getLooper());
    infoHandler.post(new Runnable() {
      @Override public void run() {
        storyInfoHelper.getStorys(storyBeans);
      }
    });
    dbManager = DBManager.getInstance(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.qureBtn:
        Log.e("ptq",dbManager.queryStoryList().toString());
        break;

      case R.id.insertBtn:
        dbManager.insertStorys(storyBeans);
        break;
    }
  }
}
