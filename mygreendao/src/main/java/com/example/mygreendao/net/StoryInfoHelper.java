package com.example.mygreendao.net;

import android.util.Log;
import com.example.mygreendao.model.Story;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/9/26.
 */
public class StoryInfoHelper {

  public static StoryInfoHelper mInstance;

  public static StoryInfoHelper getInstance(){
    if(mInstance == null){
      mInstance = new StoryInfoHelper();
    }
    return mInstance;
  }

  public void getStorys(final List<Story> storyBeans){

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url("http://csc.hivoice.cn/csc/qfs?album=%E7%9D%A1%E5%89%8D%E6%95%85%E4%BA%8B&ver=1").get().build();

    client.newCall(request).enqueue(new Callback() {

      @Override
      public void onResponse(Response response) throws IOException {
        String str = response.body().string();
        try {
          JSONObject object = new JSONObject(str);
          // Log.e("ptq", storyObject.toString());
          int err = object.getInt("err");
          if (err == 0 && object.has("fs")) {
            //storyBeans = new ArrayList<Story>();
            JSONArray fs = object.getJSONArray("fs");
            for (int i = 0; i < fs.length(); i++) {
              Story storyBean = new Story();
              JSONObject storyObject = fs.getJSONObject(i);
              if(storyObject.has("fid")){
                storyBean.setFid(storyObject.getString("fid"));
              }
              if (storyObject.has("fn")) {
                storyBean.setFn(storyObject.getString("fn"));
              }
              if(storyObject.has("spell")){
                storyBean.setSpell(storyObject.getString("spell"));
              }
              if (storyObject.has("artist")) {
                storyBean.setArtist(storyObject
                    .getString("artist"));
              }
              storyBeans.add(storyBean);
            }
           /* Message message = Message.obtain();

            Message message = new Message();
            message.arg1 = 1;
            handler.sendMessage(message);*/
          }
          Log.e("ptq", "111111");
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(Request arg0, IOException arg1) {
        // Log.e("ptq", arg1.toString());
      }
    });
    return ;
  }


}
