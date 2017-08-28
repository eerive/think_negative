package ss17.droid.unir.thinknegative;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Anne on 28.08.2017.
 */

public class NegativeDatabase {

    private static final String DATABASE_NAME = "negative.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "entry";

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_RATING = "rating";
    public static final String KEY_TEXT = "text";
    public static final String KEY_FOTOPATH = "fotopath";

    public static final int COLUMN_DATE_INDEX = 1;
    public static final int COLUMN_RATING_INDEX = 2;
    public static final int COLUMN_TEXT_INDEX = 3;
    public static final int COLUMN_FOTOPATH_INDEX = 4;

    private NegativeDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public NegativeDatabase(Context context) {
        dbHelper = new NegativeDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertEntry(Entry entry) {
        ContentValues myEntryValues = new ContentValues();
        myEntryValues.put(KEY_DATE, entry.getFormattedDate());
        myEntryValues.put(KEY_RATING, entry.getRating());
        myEntryValues.put(KEY_TEXT, entry.getText());
        myEntryValues.put(KEY_FOTOPATH, entry.getFotopath());
        return db.insert(DATABASE_TABLE, null, myEntryValues);
    }

    public void deleteEntry() {
        //mach den Eintrag weg!
        //woran soll der identifiziert werden?

    }

    //alle Einträge eines bestimmten Tages
    public ArrayList<Entry> showAllOfCertainDay (ArrayList<Entry> entryList) {

        return entryList;
    }

    private class NegativeDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_DATE
                + " text not null, " + KEY_RATING + " text not null, "+ KEY_TEXT
                +" text not null, "+ KEY_FOTOPATH +" text);";

        public NegativeDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}


