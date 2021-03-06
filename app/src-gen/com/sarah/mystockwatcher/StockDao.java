package com.sarah.mystockwatcher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.sarah.mystockwatcher.Stock;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STOCK".
*/
public class StockDao extends AbstractDao<Stock, Long> {

    public static final String TABLENAME = "STOCK";

    /**
     * Properties of entity Stock.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Symbol = new Property(1, String.class, "symbol", false, "SYMBOL");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Eps = new Property(3, Double.class, "eps", false, "EPS");
        public final static Property YearHigh = new Property(4, Double.class, "yearHigh", false, "YEAR_HIGH");
        public final static Property YearLow = new Property(5, Double.class, "yearLow", false, "YEAR_LOW");
        public final static Property Open = new Property(6, Double.class, "open", false, "OPEN");
        public final static Property PercentChange = new Property(7, String.class, "percentChange", false, "PERCENT_CHANGE");
        public final static Property ExDividendDate = new Property(8, Long.class, "exDividendDate", false, "EX_DIVIDEND_DATE");
        public final static Property DividendPayDate = new Property(9, Long.class, "dividendPayDate", false, "DIVIDEND_PAY_DATE");
        public final static Property DividendAverage = new Property(10, Double.class, "dividendAverage", false, "DIVIDEND_AVERAGE");
    };


    public StockDao(DaoConfig config) {
        super(config);
    }
    
    public StockDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STOCK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SYMBOL\" TEXT NOT NULL UNIQUE ," + // 1: symbol
                "\"NAME\" TEXT NOT NULL ," + // 2: name
                "\"EPS\" REAL," + // 3: eps
                "\"YEAR_HIGH\" REAL," + // 4: yearHigh
                "\"YEAR_LOW\" REAL," + // 5: yearLow
                "\"OPEN\" REAL," + // 6: open
                "\"PERCENT_CHANGE\" TEXT," + // 7: percentChange
                "\"EX_DIVIDEND_DATE\" INTEGER," + // 8: exDividendDate
                "\"DIVIDEND_PAY_DATE\" INTEGER," + // 9: dividendPayDate
                "\"DIVIDEND_AVERAGE\" REAL);"); // 10: dividendAverage
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STOCK\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Stock entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getSymbol());
        stmt.bindString(3, entity.getName());
 
        Double eps = entity.getEps();
        if (eps != null) {
            stmt.bindDouble(4, eps);
        }
 
        Double yearHigh = entity.getYearHigh();
        if (yearHigh != null) {
            stmt.bindDouble(5, yearHigh);
        }
 
        Double yearLow = entity.getYearLow();
        if (yearLow != null) {
            stmt.bindDouble(6, yearLow);
        }
 
        Double open = entity.getOpen();
        if (open != null) {
            stmt.bindDouble(7, open);
        }
 
        String percentChange = entity.getPercentChange();
        if (percentChange != null) {
            stmt.bindString(8, percentChange);
        }
 
        Long exDividendDate = entity.getExDividendDate();
        if (exDividendDate != null) {
            stmt.bindLong(9, exDividendDate);
        }
 
        Long dividendPayDate = entity.getDividendPayDate();
        if (dividendPayDate != null) {
            stmt.bindLong(10, dividendPayDate);
        }
 
        Double dividendAverage = entity.getDividendAverage();
        if (dividendAverage != null) {
            stmt.bindDouble(11, dividendAverage);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Stock readEntity(Cursor cursor, int offset) {
        Stock entity = new Stock( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // symbol
            cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // eps
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // yearHigh
            cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5), // yearLow
            cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // open
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // percentChange
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // exDividendDate
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // dividendPayDate
            cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10) // dividendAverage
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Stock entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSymbol(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setEps(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setYearHigh(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setYearLow(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
        entity.setOpen(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setPercentChange(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setExDividendDate(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setDividendPayDate(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setDividendAverage(cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Stock entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Stock entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
