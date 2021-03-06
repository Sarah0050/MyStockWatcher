package com.sarah.mystockwatcher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.sarah.mystockwatcher.UserSetting;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_SETTING".
*/
public class UserSettingDao extends AbstractDao<UserSetting, Long> {

    public static final String TABLENAME = "USER_SETTING";

    /**
     * Properties of entity UserSetting.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ExpectedRate = new Property(1, Double.class, "expectedRate", false, "EXPECTED_RATE");
        public final static Property StockSymbols = new Property(2, String.class, "stockSymbols", false, "STOCK_SYMBOLS");
    };


    public UserSettingDao(DaoConfig config) {
        super(config);
    }
    
    public UserSettingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_SETTING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"EXPECTED_RATE\" REAL," + // 1: expectedRate
                "\"STOCK_SYMBOLS\" TEXT);"); // 2: stockSymbols
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_SETTING\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserSetting entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Double expectedRate = entity.getExpectedRate();
        if (expectedRate != null) {
            stmt.bindDouble(2, expectedRate);
        }
 
        String stockSymbols = entity.getStockSymbols();
        if (stockSymbols != null) {
            stmt.bindString(3, stockSymbols);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserSetting readEntity(Cursor cursor, int offset) {
        UserSetting entity = new UserSetting( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1), // expectedRate
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // stockSymbols
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserSetting entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setExpectedRate(cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1));
        entity.setStockSymbols(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserSetting entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserSetting entity) {
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
