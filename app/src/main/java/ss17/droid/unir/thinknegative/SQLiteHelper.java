package ss17.droid.unir.thinknegative;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Duc on 31/08/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper{


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String title, String content, byte[] image, double mood){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO DBLIST VALUES (NULL, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, content);
        statement.bindBlob(3, image);
        statement.bindDouble(4, mood);

        statement.executeInsert();

    }

    public void updateData(int id, String title, String content, byte[] image ){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE DBLIST SET title = ?, content = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindDouble(1,(double)id);
        statement.bindString(2,title);
        statement.bindString(3,content);
        statement.bindBlob(4,image);

        statement.execute();
        database.close();
    }

    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM DBLIST WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
