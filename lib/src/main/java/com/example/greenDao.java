package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class greenDao {

    private static Entity stock;
    private static Entity userSetting;

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.sarah.mystockwatcher");

        stock = schema.addEntity("Stock");
        userSetting = schema.addEntity("UserSetting");

        createUserSettingProperty();
        createStockProperty();

        new DaoGenerator().generateAll(schema, "app/src-gen");
    }

    private static void createUserSettingProperty() {
        userSetting.addIdProperty();
        userSetting.addDoubleProperty("expectedRate");
        userSetting.addStringProperty("stockSymbols");

    }

    private static void createStockProperty() {
        stock.addIdProperty();
        stock.addStringProperty("symbol").unique();
        stock.addStringProperty("name").unique();
        stock.addDoubleProperty("eps");
        stock.addDoubleProperty("yearHigh");
        stock.addDoubleProperty("yearLow");
        stock.addDoubleProperty("open");
        stock.addStringProperty("percentChange");
        stock.addLongProperty("exDividendDate");
        stock.addLongProperty("dividendPayDate");
        stock.addDoubleProperty("dividendAverage");
    }


//    "symbol":"2002.tw",
//
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
