package com.sarah.mystockwatcher.manager;

/**
 * Created by sarah.sun on 2015/9/9.
 */
public class YQLManager {

    private static YQLManager yqlManager = new YQLManager();

    public static YQLManager getInstance() {
        return yqlManager;
    }


    public void readStock() {
        String stockPre = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
        String stock = "\"1231.tw\"" + "," + "\"AAPL\"";
        String stockNext = ")&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String read = stockPre + stock + stockNext;

    }

}
