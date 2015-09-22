package com.sarah.mystockwatcher.manager;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sarah.mystockwatcher.DaoMaster;
import com.sarah.mystockwatcher.DaoSession;
import com.sarah.mystockwatcher.Stock;
import com.sarah.mystockwatcher.StockDao;
import com.sarah.mystockwatcher.UserSetting;
import com.sarah.mystockwatcher.UserSettingDao;

import java.util.List;

/**
 * Created by sarah.sun on 2015/9/10.
 */
public class DataManager extends Application {

    private static DataManager dataManager = new DataManager();
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private static Context context = null;

    public static DataManager getInstance() {
        return dataManager;
    }

    public DataManager() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.context = getApplicationContext();
    }

    public void createDaoDB() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "stock-core", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
        }
    }

    public DaoSession getDaoSession(boolean newSession) {
        createDaoDB();
        if (newSession) {
            return daoMaster.newSession();
        }
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
