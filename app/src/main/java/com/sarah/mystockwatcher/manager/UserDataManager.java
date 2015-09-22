package com.sarah.mystockwatcher.manager;

import com.sarah.mystockwatcher.UserSetting;
import com.sarah.mystockwatcher.UserSettingDao;

/**
 * Created by sarah.sun on 2015/9/18.
 */
public class UserDataManager {

    private static final UserDataManager instance = new UserDataManager();

    public static UserDataManager getInstance() {
        return instance;
    }

    public UserSetting getUserSetting() {
        UserSettingDao userSettingDao = DataManager.getInstance().getDaoSession(false).getUserSettingDao();
        UserSetting userSetting;
        userSetting = userSettingDao.queryBuilder().unique();
        if(userSetting == null){
            userSetting = new UserSetting();
            userSettingDao.insert(userSetting);
        }
        return userSetting;
    }

    private void setUserSetting(UserSetting userSetting) {
        UserSettingDao userSettingDao = DataManager.getInstance().getDaoSession(false).getUserSettingDao();
        userSettingDao.insertOrReplace(userSetting);
    }

    public void storeUserExpectedRate(double expectedRate) {
        UserSetting userSetting = getUserSetting();
        userSetting.setExpectedRate(expectedRate);
        setUserSetting(userSetting);
    }

}
