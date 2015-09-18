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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvStock;
    private StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStock = (ListView) findViewById(R.id.lv_stock);
        stockAdapter = new StockAdapter(this, new ArrayList<Stock>());

    }


    @Override
    protected void onStart() {
        super.onStart();
        lvStock.setAdapter(stockAdapter);
        new SyncStock(callback).execute();
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
            return true;
        }
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private SyncDataCallback callback = new SyncDataCallback() {
        @Override
        public void onSyncDataSuccess(List<Stock> stocks) {
            updateView(stocks);
        }
    };

    private void updateView(List<Stock> stocks){
        stockAdapter.updateView(stocks);
    }

}