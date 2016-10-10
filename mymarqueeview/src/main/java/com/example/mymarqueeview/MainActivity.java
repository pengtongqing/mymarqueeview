package com.example.mymarqueeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private MarqueeTextView myMarqueetv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myMarqueetv = (MarqueeTextView) findViewById(R.id.myMarqueetv);
    myMarqueetv.setText("我的跑马灯效果,run!run!run!run!run!run!run!run!");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    myMarqueetv.stop();;
  }
}
