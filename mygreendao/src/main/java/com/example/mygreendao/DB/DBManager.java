package com.example.mygreendao.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.mygreendao.DB.model.DaoMaster;
import com.example.mygreendao.DB.model.DaoSession;
import com.example.mygreendao.DB.model.StoryDao;
import com.example.mygreendao.model.Story;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by user on 2016/9/26.
 */
public class DBManager {
  private final static String dbName = "test_db";
  private static DBManager mInstance;
  private DaoMaster.DevOpenHelper openHelper;
  private Context context;

  public DBManager(Context context) {
    this.context = context;
    openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
  }

  /**
   * 获取单例引用
   */
  public static DBManager getInstance(Context context) {
    if (mInstance == null) {
      synchronized (DBManager.class) {
        if (mInstance == null) {
          mInstance = new DBManager(context);
        }
      }
    }
    return mInstance;
  }

  /**
   * 获取可读数据库
   */
  private SQLiteDatabase getReadableDatabase() {
    if (openHelper == null) {
      openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }
    SQLiteDatabase db = openHelper.getReadableDatabase();
    return db;
  }

  private SQLiteDatabase getWritableDatabase() {
    if (openHelper == null) {
      openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }
    SQLiteDatabase db = openHelper.getWritableDatabase();
    return db;
  }

  /**
   * 插入一条记录
   */

  public void insertStory(Story story) {
    DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
    DaoSession daoSession = daoMaster.newSession();
    StoryDao storyDao = daoSession.getStoryDao();
    storyDao.insert(story);
  }

  public void insertStorys(List<Story> storys) {
    Log.e("ptq","insertStorys1-->" +storys);
    if (storys == null || storys.isEmpty()) {
      return;
    }
    DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
    DaoSession daoSession = daoMaster.newSession();
    StoryDao storyDao = daoSession.getStoryDao();
    Log.e("ptq","insertStorys-->" + storyDao.getTablename());
    storyDao.insertInTx(storys);
  }

  public List<Story> queryStoryList(){
    DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
    DaoSession daoSession = daoMaster.newSession();
    StoryDao storyDao = daoSession.getStoryDao();
    QueryBuilder<Story> queryBuilder =  storyDao.queryBuilder();
    Log.e("ptq","Tablename-->" + storyDao.getTablename());
    return queryBuilder.list();
  }
}
