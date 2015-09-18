package com.sarah.mystockwatcher.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sarah.mystockwatcher.R;
import com.sarah.mystockwatcher.Stock;
import com.sarah.mystockwatcher.manager.DataManager;

import java.util.List;

/**
 * Created by sarah.sun on 2015/9/10.
 */
public class StockAdapter extends BaseAdapter {

    private List<Stock> stocks;
    private Context context;

    public StockAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
    }


    @Override
    public int getCount() {
        return stocks.size();
    }

    @Override
    public Object getItem(int position) {
        return stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_stock_item, null);
            viewHolder.tvSymbol = (TextView) convertView.findViewById(R.id.tv_symbol);
            viewHolder.tvDividendAverage = (TextView) convertView.findViewById(R.id.tv_dividend_average);
            viewHolder.tvSBid = (TextView) convertView.findViewById(R.id.tv_bid);
            viewHolder.tvBidGoal = (TextView) convertView.findViewById(R.id.tv_bid_goal);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvSymbol.setText(stocks.get(position).getSymbol());
        viewHolder.tvDividendAverage.setText(String.valueOf(stocks.get(position).getDividendAverage()));
        viewHolder.tvSBid.setText(String.valueOf(stocks.get(position).getOpen()));
        double bidGoal = stocks.get(position).getDividendAverage() / DataManager.getInstance().querySetting().getExpectedRate();
        viewHolder.tvBidGoal.setText(String.valueOf(bidGoal));


        return convertView;
    }


    private class ViewHolder {
        TextView tvSymbol;
        TextView tvDividendAverage;
        TextView tvSBid;
        TextView tvBidGoal;
    }

    public void updateView(List<Stock> stocks){
        this.stocks = stocks;
        notifyDataSetChanged();
    }
}
