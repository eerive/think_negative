package ss17.droid.unir.thinknegative;

/**
 * Created by Duc on 31/08/2017.
 */

public class DBList {
    private int id;
    private String title;
    private String content;
    private byte[] image;
    private double mood;



    public DBList(int id, String title, String content, byte[] image, double mood) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.mood = mood;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getMood() {
        return mood;
    }

    public void setMood(double mood) {
        this.mood = mood;
    }

}
