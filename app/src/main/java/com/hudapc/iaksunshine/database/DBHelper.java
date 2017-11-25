package com.hudapc.iaksunshine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hudapc.iaksunshine.model.City;
import com.hudapc.iaksunshine.model.ForeCast;
import com.hudapc.iaksunshine.model.RequestDailyForeCast;
import com.hudapc.iaksunshine.model.Temperature;
import com.hudapc.iaksunshine.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilmechine on 11/25/17.
 */

public class DBHelper extends SQLiteOpenHelper
{
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "sunshine.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATABASE_SQL = "CREATE TABLE " + DBContract.ForecastEntry.TABLE_NAME + " ("
                + DBContract.ForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBContract.ForecastEntry.COLUMN_CITY_NAME + " TEXT, "
                + DBContract.ForecastEntry.COLUMN_EPOCH_TIME + " INTEGER, "
                + DBContract.ForecastEntry.COLUMN_MAX_TEMP + " REAL, "
                + DBContract.ForecastEntry.COLUMN_MIN_TEMP + " REAL, "
                + DBContract.ForecastEntry.COLUMN_HUMIDITY + " INTEGER, "
                + DBContract.ForecastEntry.COLUMN_PRESSURE + " REAL, "
                + DBContract.ForecastEntry.COLUMN_WEATHER_ID + " INTEGER, "
                + DBContract.ForecastEntry.COLUMN_WEATHER_MAIN + " TEXT, "
                + DBContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION + " TEXT, "
                //+ DBContract.ForecastEntry.COLUMN_WIND_SPEED + " REAL, "
                + DBContract.ForecastEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')));";

        db.execSQL(CREATE_DATABASE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ForecastEntry.TABLE_NAME);
        onCreate(db);
    }

    public void saveForecast(City city, ForeCast data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.ForecastEntry.COLUMN_CITY_NAME, city.getName());
        cv.put(DBContract.ForecastEntry.COLUMN_EPOCH_TIME, data.getDt());
        cv.put(DBContract.ForecastEntry.COLUMN_MAX_TEMP, data.getTemp().getMax());
        cv.put(DBContract.ForecastEntry.COLUMN_MIN_TEMP, data.getTemp().getMin());
        cv.put(DBContract.ForecastEntry.COLUMN_PRESSURE, data.getPressure());
        cv.put(DBContract.ForecastEntry.COLUMN_HUMIDITY, data.getHumidity());
        cv.put(DBContract.ForecastEntry.COLUMN_WEATHER_ID, data.getWeather().get(0).getId());
        cv.put(DBContract.ForecastEntry.COLUMN_WEATHER_MAIN, data.getWeather().get(0).getMain());
        cv.put(DBContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION, data.getWeather().get(0).getDescription());
        //cv.put(DBContract.ForecastEntry.COLUMN_WIND_SPEED, data.);

        long result = db.insert(DBContract.ForecastEntry.TABLE_NAME, null, cv);
        Log.i(TAG, "saveForecast result -> " + result);
        db.close();
    }

    public RequestDailyForeCast getSavedForecast(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ForeCast> weatherItems = new ArrayList<>();

        //create City, which will be added to DailyForecast
        City resultCity = new City();
        resultCity.setName(city);

        //create DailyForecast, which will be filled with WeatherItem
        RequestDailyForeCast result = new RequestDailyForeCast();
        //set city to DailyForecast
        result.setCity(resultCity);
        //set 0-sized list of WeatherItem
        result.setList((ArrayList<ForeCast>) weatherItems);

        Cursor cursor = db.query(DBContract.ForecastEntry.TABLE_NAME,
                null,
                DBContract.ForecastEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city},
                null,
                null,
                null,
                null);
        int total = cursor.getCount();
        if (total > 0) {
            if (cursor.moveToFirst()) {
                do {
                    // PREPARING MODEL
                    //create list weathers, which is only one item inside
                    List<Weather> listWeatherItems = new ArrayList<>();
                    //create the Weathers object, will be inserted to list above
                    Weather weathers = new Weather();
                    //add the Weathers object above to list
                    listWeatherItems.add(weathers);

                    //create temp object
                    Temperature temp = new Temperature();

                    //create WeatherItem object, which will be used to store 16 day forecast
                    ForeCast item = new ForeCast();
                    //set Weathers list to WeatherItem
                    item.setWeather((ArrayList<Weather>) listWeatherItems);
                    //set Temp to WeatherItem
                    item.setTemp(temp);

                    //getting all data from cursor, and set it to WeatherItem
                    item.setDt(cursor.getInt(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_EPOCH_TIME)));
                    item.getWeather().get(0).setId(cursor.getInt(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_WEATHER_ID)));
                    item.getWeather().get(0).setDescription(cursor.getString(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_WEATHER_DESCRIPTION)));
                    item.getTemp().setMax((float) cursor.getDouble(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_MAX_TEMP)));
                    item.getTemp().setMin((float) cursor.getDouble(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_MIN_TEMP)));

                    item.setHumidity(cursor.getInt(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_HUMIDITY)));
                    item.setPressure((float) cursor.getDouble(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_PRESSURE)));
                    //item.setSpeed(cursor.getDouble(cursor.getColumnIndex(DBContract.ForecastEntry.COLUMN_WIND_SPEED)));

                    //finally, add WeatherItem to DailyForecast
                    result.getList().add(item);
                } while (cursor.moveToNext());
            }
        } else {
            //data not found
            Log.w(TAG, "getSavedForecast not found any data!");
        }
        cursor.close();
        db.close();

        Log.d(TAG, "result -> " + result.toString());
        return result;
    }

    public boolean isDataAlreadyExist(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        /*Cursor cursor = db.query(
                true,
                DBContract.ForecastEntry.TABLE_NAME,
                null,
                DBContract.ForecastEntry.COLUMN_CITY_NAME + " LIKE ?",
                new String[]{"%" + city + "%"},
                null,
                null,
                null,
                null);*/

        final String sql = "SELECT * FROM "
                + DBContract.ForecastEntry.TABLE_NAME
                + " WHERE "
                + DBContract.ForecastEntry.COLUMN_CITY_NAME
                + " LIKE '%" + city + "%';";

        Cursor cursor = db.rawQuery(sql,null);

        int total = cursor.getCount();
        Log.d(TAG,"isDataAlreadyExist total -> "+total);
        cursor.close();
        db.close();
        return total > 0;
    }

    public void deleteForUpdate(String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        final String sql = "DELETE FROM "
                + DBContract.ForecastEntry.TABLE_NAME
                + " WHERE "
                + DBContract.ForecastEntry.COLUMN_CITY_NAME
                + " LIKE '%" + city + "%';";
        db.execSQL(sql);
        db.close();
    }
}
