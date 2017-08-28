package sit374_team17.propertyinspector.Note;

/**
 * Created by callu on 28/08/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sit374_team17.propertyinspector.R;

class Adapter_Note_Stack extends BaseAdapter {

    List<Note> arrayList;
    LayoutInflater inflater;
    ViewHolder holder = null;

    public Adapter_Note_Stack(Context context, List<Note> arrayList) {
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public Note getItem(int pos) {
        // TODO Auto-generated method stub
        return arrayList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.row_note_text, parent, false);
            holder = new ViewHolder();

            holder.text = (TextView) view.findViewById(R.id.textView_note);
            holder.image = (ImageView) view.findViewById(R.id.imageView_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(arrayList.get(pos).getDescription());
        //holder.image.setBackgroundResource(arrayList.get(pos).getImage());

        return view;
    }

    public class ViewHolder {
        TextView text;
        ImageView image;
    }

}