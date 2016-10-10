package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private int queueSize = 10;
  private List<Integer> queue = new ArrayList<Integer>(queueSize);

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Producer producer = new Producer();
    Consumer consumer = new Consumer();

    producer.start();
    consumer.start();
  }

  class Consumer extends Thread {

    @Override public void run() {
      consume();
    }

    private void consume() {
      while (true) {
        synchronized (queue) {
          while (queue.size() == 0) {
            try {
              Log.e("ptq","队列空，等待数据");
              queue.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
              queue.notify();
            }
          }
          queue.remove(0);          //每次移走队首元素
          queue.notify();
          Log.e("ptq","从队列取走一个元素，队列剩余" + queue.size() + "个元素");
        }
      }
    }
  }

  class Producer extends Thread {

    @Override public void run() {
      produce();
    }

    private void produce() {
      while (true) {
        synchronized (queue) {
          while (queue.size() == queueSize) {
            try {
              Log.e("ptq","队列满，等待有空余空间");
              queue.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
              queue.notify();
            }
          }
          queue.add(1);        //每次插入一个元素
          queue.notify();
          Log.e("ptq","向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
        }
      }
    }
  }
}
