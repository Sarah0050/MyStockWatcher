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

    public void saveStocks(Stock stock) {
        StockDao stockDao = getDaoSession(false).getStockDao();
        stockDao.insertOrReplace(stock);

    }

    public void saveStocks(List<Stock> stocks) {
        StockDao stockDao = getDaoSession(false).getStockDao();
        for (Stock stock : stocks) {
            stockDao.insertOrReplace(stock);
        }
    }

    public Stock queryStocks(String symbol) {
        StockDao stockDao = getDaoSession(false).getStockDao();
        Stock stock = stockDao.queryBuilder().where(StockDao.Properties.Symbol.eq(symbol)).unique();
        return stock;
    }

    public UserSetting querySetting() {
        UserSettingDao userSettingDao = getDaoSession(false).getUserSettingDao();
        UserSetting userSetting = userSettingDao.queryBuilder().unique();
        return userSetting;
    }

    public void saveSetting(UserSetting setting) {
        UserSettingDao userSettingDao = getDaoSession(false).getUserSettingDao();
        userSettingDao.insertOrReplace(setting);
    }


}
