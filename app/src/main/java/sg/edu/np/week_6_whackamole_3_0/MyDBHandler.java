package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WhackAMole.db";
    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public MyDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(UserData.CREATE_TABLE);
        Log.v(TAG, "DB Created: " + UserData.CREATE_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(UserData.DROP_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    public void addUser(UserData userData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
            for(int i = 0; i<10; i++){
                ContentValues values = new ContentValues();
                values.put(UserData.COLUMN_NAME, userData.getMyUserName());
                values.put(UserData.COLUMN_PASSWORD, userData.getMyPassword());
                values.put(UserData.COLUMN_LEVELS, userData.getLevels().get((i)));
                values.put(UserData.COLUMN_SCORES, userData.getScores().get(i));
                Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
                db.insert(UserData.TABLE_NAME, null, values);
            }
            db.close();
    }

    public UserData findUser(String username)
    {

        String query = "SELECT * FROM " + UserData.TABLE_NAME + " WHERE " + UserData.COLUMN_NAME  + " = \"" + username + "\"";
        Log.v(TAG, FILENAME +": Find user form database: " + query);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        String password = null;
        ArrayList<Integer> level = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();

        if(cursor.moveToFirst()){
            password  = cursor.getString(1);
            do{
                level.add(cursor.getInt(2));
                level.add(cursor.getInt(3));
            }while(cursor.moveToNext());
        }
        else{
            Log.v(TAG, FILENAME+ ": No data found!");
            cursor.close();
            db.close();
            return null;
        }
        UserData queryData = new UserData(username, password, level, score);
        Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
        cursor.close();
        db.close();
        return queryData;

    }

    public boolean deleteAccount(String username) {
        String query = "select * form " + UserData.TABLE_NAME + " where " + UserData.COLUMN_NAME + " = \"" + username + "\"";

        Log.v(TAG, FILENAME + ": Database delete user: " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if(cursor.moveToFirst()){
            do{
                db.delete(UserData.TABLE_NAME, UserData.COLUMN_NAME +" =?", new String[] { username });
            }while (cursor.moveToFirst());
        }else {
            Log.v(TAG, FILENAME+ ": No data found!");
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();
        db.close();
        return true;
    }
}
