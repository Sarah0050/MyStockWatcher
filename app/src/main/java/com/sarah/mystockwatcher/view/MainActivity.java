package com.sarah.mystockwatcher.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sarah.mystockwatcher.R;
import com.sarah.mystockwatcher.Stock;
import com.sarah.mystockwatcher.SyncDataCallback;
import com.sarah.mystockwatcher.SyncStock;
import com.sarah.mystockwatcher.manager.StockDataManager;
import com.sarah.mystockwatcher.manager.UserDataManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Stock> stocks;
    private ListView lvStock;
    private StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStock = (ListView) findViewById(R.id.lv_stock);

    }


    @Override
    protected void onStart() {
        super.onStart();

        initListView();
    }

    private void initListView() {
        stocks = StockDataManager.getInstance().queryAllStocks();
        stockAdapter = new StockAdapter(this, stocks);
        lvStock.setAdapter(stockAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            EditSettingDialog editSettingDialog = new EditSettingDialog(this, editExpectedRatedCallback);
            editSettingDialog.setCanceledOnTouchOutside(false);
            editSettingDialog.show();
            return true;
        }
        if (id == R.id.action_add) {
            AddStockDialog addStockDialog = new AddStockDialog(this, addStockCallback);
            addStockDialog.setCanceledOnTouchOutside(false);
            addStockDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private DialogCallback addStockCallback = new DialogCallback() {

        @Override
        public void clickOk(String symbol) {
            SyncStock syncStock = new SyncStock(syncDataCallback);
            syncStock.execute(symbol);
        }
    };

    private DialogCallback editExpectedRatedCallback = new DialogCallback() {

        @Override
        public void clickOk(String expectedRate) {
            UserDataManager.getInstance().storeUserExpectedRate(Double.valueOf(expectedRate) / 100);
        }
    };

    private SyncDataCallback syncDataCallback = new SyncDataCallback() {
        @Override
        public void onSyncDataSuccess() {
            updateView();
        }
    };

    private void updateView() {
        stockAdapter.updateView();
    }

}