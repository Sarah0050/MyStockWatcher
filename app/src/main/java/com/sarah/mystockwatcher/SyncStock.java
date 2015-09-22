package com.sarah.mystockwatcher;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sarah.mystockwatcher.manager.StockDataManager;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by sarah.sun on 2015/9/9.
 */


public class SyncStock extends AsyncTask<String, String, Object> {

    private OkHttpClient okHttpClient;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private SyncDataCallback callback;

    public SyncStock(SyncDataCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(String... params) {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        String inputSymbol = params[0];
        String stockNumber = "\"" + inputSymbol + ".tw" + "\"";

        String stockPre = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
        String stockNext = ")&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String historyPre = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.dividendhistory%20where%20symbol%20%3D%20";
        String historyNext = "%20and%20startDate%20%3D%20%222006-01-01%22%20and%20endDate%20%3D%20%222015-12-31%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

        String read = stockPre + stockNumber + stockNext;
        String historyRead = historyPre + stockNumber + historyNext;

        String response = "";
        String response2 = "";
        try {
            response = post(read, "");
            response2 = post(historyRead, "");
        } catch (Exception e) {
            Log.d("SyncStock", "error");
        }
        System.out.println(response);
        System.out.println(response2);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
        JsonObject query = jsonObject.getAsJsonObject("query");
        int count = query.get("count").getAsInt();
        JsonObject results = query.getAsJsonObject("results");
        JsonObject stockObject = null;

        if (count > 1) {
            for (int i = 0; i < count; i++) {
                JsonArray quote = results.getAsJsonArray("quote");
                stockObject = quote.get(i).getAsJsonObject();
            }

        } else {
            stockObject = results.getAsJsonObject("quote");
        }

        JsonObject jsonObject2 = (JsonObject) new JsonParser().parse(response2);

        parseStock(inputSymbol, stockObject, jsonObject2);


        return null;
    }


    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public void parseStock(String inputStmbol, JsonObject stockObject, JsonObject jsonObject2) {

        Stock stock = StockDataManager.getInstance().queryStocks(inputStmbol);
        String symbol = stockObject.get("symbol").getAsString().replaceAll("\\D+", "");
        stock.setSymbol(symbol);
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


        JsonObject query2 = jsonObject2.getAsJsonObject("query");
        JsonObject results2 = query2.get("results").getAsJsonObject();
        JsonArray quote2 = results2.get("quote").getAsJsonArray();

        double sum = 0;
        for (int i = 0; i < quote2.size(); i++) {
            JsonObject historyObject = quote2.get(i).getAsJsonObject();
            sum = sum + historyObject.get("Dividends").getAsFloat();
        }
        double average = sum / (quote2.size());

        DecimalFormat df = new DecimalFormat("#.00");
        stock.setDividendAverage(Double.valueOf(df .format(average)));

        StockDataManager.getInstance().saveStocks(stock);
    }

    @Override
    protected void onPostExecute(Object stocks) {
        super.onPostExecute(stocks);
        callback.onSyncDataSuccess();

    }


}
