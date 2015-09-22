package com.sarah.mystockwatcher.manager;

/**
 * Created by sarah.sun on 2015/9/9.
 */
public class YQLManager {

    private static YQLManager yqlManager = new YQLManager();

    public static YQLManager getInstance() {
        return yqlManager;
    }



}
