package ss17.droid.unir.thinknegative;

import android.icu.util.GregorianCalendar;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anne on 28.08.2017.
 */
@SuppressWarnings("Access")
public class Entry implements Comparable <Entry> {

    //unsere Einträge: timestamp/date, Rating, Text, Fotopath
    private int dbId;
    private GregorianCalendar cal;
    private int rating;
    private String text;
    private String fotopath;

    public Entry (int id, int day, int month, int year, int rating, String text, String fotopath) {
        //cal = new GregorianCalendar(year, month, day);
        this.dbId = id;
        cal = new GregorianCalendar(day,month,year);
        this.rating = rating;
        this.text = text;
        this.fotopath = fotopath;
    }

    public int getDbId() {
        return dbId;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public String getFotopath() {
        return fotopath;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(cal.getTime());
    }

    public Date getDueDate() {
        return cal.getTime();
    }

    @Override
    public int compareTo(Entry another) {
        return getDueDate().compareTo(another.getDueDate());
    }

    @Override
    public String toString() {      //brauchen wir das? vielleicht anders?
        return "Entry{" +
                "dbId=" + dbId +
                ", cal=" + cal +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", fotopath='" + fotopath + '\'' +
                '}';
    }
}
