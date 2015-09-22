package com.sarah.mystockwatcher.manager;

import com.sarah.mystockwatcher.Stock;
import com.sarah.mystockwatcher.StockDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah.sun on 2015/9/18.
 */
public class StockDataManager {

    private static final StockDataManager instance = new StockDataManager();

    public static StockDataManager getInstance() {
        return instance;
    }


    public void saveStocks(Stock stock) {
        StockDao stockDao = DataManager.getInstance().getDaoSession(false).getStockDao();
        stockDao.insertOrReplace(stock);

    }

    public List<Stock> queryAllStocks() {
        StockDao stockDao = DataManager.getInstance().getDaoSession(false).getStockDao();
        List<Stock> stocks = stockDao.queryBuilder().orderAsc(StockDao.Properties.Id).list();
        return stocks;
    }

    public Stock queryStocks(String symbol) {
        StockDao stockDao = DataManager.getInstance().getDaoSession(false).getStockDao();
        Stock stock;
        stock = stockDao.queryBuilder().where(StockDao.Properties.Symbol.eq(symbol)).unique();
        if (stock == null) {
            stock = new Stock();
        }
        return stock;
    }

}
