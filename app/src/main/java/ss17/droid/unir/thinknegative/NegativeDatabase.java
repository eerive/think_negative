package ss17.droid.unir.thinknegative;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public long insertEntry(Entry entry) {      //id müsste automatisch vergeben werden
        ContentValues myEntryValues = new ContentValues();
        myEntryValues.put(KEY_DATE, entry.getFormattedDate());
        myEntryValues.put(KEY_RATING, entry.getRating());
        myEntryValues.put(KEY_TEXT, entry.getText());
        myEntryValues.put(KEY_FOTOPATH, entry.getFotopath());
        // das ist die RowId
        long rowId = db.insert(DATABASE_TABLE,null,myEntryValues);
        return rowId;
    }

    public void deleteEntry() {
        //mach den Eintrag weg!
        //woran soll der identifiziert werden?

    }

    //alle Einträge eines bestimmten Tages
    public ArrayList<Entry> showAllEntriesOfDay (ArrayList<Entry> entryList) {
        // wie genau auf bestimmten Tag festlegen?

        ArrayList<Entry> entries = new ArrayList<Entry>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_DATE, KEY_RATING, KEY_TEXT, KEY_FOTOPATH}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                int rating = cursor.getInt(2);
                String text = cursor.getString(3);
                String fotopath = cursor.getString(4);

                Date formattedDate = null;
                try {
                    formattedDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(Locale.GERMAN);
                cal.setTime(formattedDate);

                entries.add(new Entry(id, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), rating, text, fotopath));
            } while (cursor.moveToNext());
        }
        return entries;
    }

    public void close() {
        db.close();
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
            // was...?
        }
    }
}


