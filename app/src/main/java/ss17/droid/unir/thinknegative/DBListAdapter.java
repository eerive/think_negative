package ss17.droid.unir.thinknegative;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 */

public class DBListAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<DBList> dblistsList;

    public DBListAdapter(Context context, int layout, ArrayList<DBList> dblistsList) {
        this.context = context;
        this.layout = layout;
        this.dblistsList = dblistsList;
    }

    @Override
    public int getCount() {
        return dblistsList.size();
    }

    @Override
    public Object getItem(int position) {
        return dblistsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle, txtContent;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row==null){
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //row = inflater.inflate(layout, null);
            row= LayoutInflater.from(context).inflate(R.layout.fragment_calendar_customgridview,viewGroup,false);
            holder.txtTitle = row.findViewById(R.id.textTitle);
            holder.txtContent = row.findViewById(R.id.textContent);
            holder.imageView = row.findViewById(R.id.imageDB);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();

        }

        DBList dbList = dblistsList.get(position);

        holder.txtTitle.setText(dbList.getTitle());
        holder.txtContent.setText(dbList.getContent());

        byte[] dblistImage = dbList.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(dblistImage, 0, dblistImage.length);

        holder.imageView.setImageBitmap(bitmap);
        return row;
    }
}
