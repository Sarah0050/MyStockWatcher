package com.sarah.mystockwatcher;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sarah.mystockwatcher.manager.DataManager;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sarah.sun on 2015/9/9.
 */


public class SyncStock extends AsyncTask {

    private OkHttpClient okHttpClient;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private SyncDataCallback callback;

    public SyncStock(SyncDataCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        String stockPre = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
        String stockNumber = "\"1231.tw\"";
        String stockNext = ")&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String read = stockPre + stockNumber + stockNext;

        String response = "";
        try {
            response = post(read, "");
        } catch (Exception e) {
            Log.d("SyncStock", "error");
        }
        System.out.println(response);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
        JsonObject query = jsonObject.getAsJsonObject("query");
        int count = query.get("count").getAsInt();
        JsonObject results = query.getAsJsonObject("results");


        List<Stock> stocks = new ArrayList<Stock>();
        if(count > 1) {
            for (int i = 0; i < count; i++) {
                JsonArray quote = results.getAsJsonArray("quote");
                JsonObject stockObject = quote.get(i).getAsJsonObject();
                parseStock(stocks, stockObject);
            }

        }else{
            JsonObject stockObject = results.getAsJsonObject("quote");
            parseStock(stocks, stockObject);
        }


        String historyPre = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.dividendhistory%20where%20symbol%20%3D%20";
        String historyNumber = "\"2002.tw\"";
        String historyNext = "%20and%20startDate%20%3D%20%221962-01-01%22%20and%20endDate%20%3D%20%222013-12-31%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String historyRead = historyPre + historyNumber + historyNext;

        String response2 = "";
        try {
            response2 = post(historyRead, "");
        } catch (Exception e) {
            Log.d("SyncHistory", "error");
        }
        System.out.println(response2);
        JsonObject jsonObject2 = (JsonObject) new JsonParser().parse(response2);
        JsonObject query2 = jsonObject2.getAsJsonObject("query");
        JsonObject results2 = query2.get("results").getAsJsonObject();
        JsonArray quote2 = results2.get("quote").getAsJsonArray();

        double sum = 0;
        for (int i = 0; i < quote2.size(); i++) {
            JsonObject historyObject = quote2.get(i).getAsJsonObject();
            sum = sum + historyObject.get("Dividends").getAsFloat();
        }

//        Stock history = DataManager.getInstance().queryStocks(quote2.get(0).getAsJsonObject().get("symbol").getAsString());
//        if (history == null) {
            Stock history = new Stock();
//        }
        history.setDividendAverage(sum / (quote2.size() - 1));
        DataManager.getInstance().saveStocks(history);


        UserSetting userSetting = new UserSetting();
        userSetting.setExpectedRate(0.6);
        DataManager.getInstance().saveSetting(userSetting);





        return stocks;

//            "Bid":"19.00",
//            "EarningsShare":"1.41", //EPS
//
//            "YearLow":"17.55",
//            "YearHigh":"26.80",
//
//            "Name":"CHINA STEEL CORP TWD10",
//
//            "Open":"19.00",
//            "ExDividendDate":"7/24/2015",
//            "DividendPayDate":null,
//            "YearRange":"17.55 - 26.80",
//            "PercentChange":"+0.00%"

    }

    private void parseStock(List<Stock> stocks, JsonObject stockObject) {
        Stock stock = new Stock();
        stock.setSymbol(stockObject.get("symbol").getAsString());
        stock.setEps(stockObject.get("EarningsShare").getAsDouble());
        stock.setName(stockObject.get("Name").getAsString());
        stock.setOpen(stockObject.get("Open").getAsDouble());

        stock.setYearLow(stockObject.get("YearLow").getAsDouble());
        stock.setYearHigh(stockObject.get("YearHigh").getAsDouble());
        stock.setDividendAverage(0.0);
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
        Date exDividendDate = null;
        Date dividendDate = null;
        try {
            exDividendDate = sdf.parse(stockObject.get("ExDividendDate").getAsString());
            dividendDate = sdf.parse(stockObject.get("ExDividendDate").getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (exDividendDate != null) {
            stock.setExDividendDate(exDividendDate.getTime());
        }
        if (dividendDate != null) {
            stock.setDividendPayDate(dividendDate.getTime());
        }
        stock.setPercentChange(stockObject.get("PercentChange").getAsString());
        stocks.add(stock);
        DataManager.getInstance().saveStocks(stocks);
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }


    @Override
    protected void onPostExecute(Object stocks) {
        super.onPostExecute(stocks);
        callback.onSyncDataSuccess((List<Stock>) stocks);

    }


}
