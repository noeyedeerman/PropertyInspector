package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;

public class Adapter_PropertySwipe extends PagerAdapter {
    //  private int[] image_resource = {R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property};

    private Context context;
    private LayoutInflater layoutInflater;
   List<String> mPhotoList;
    List<Note> mNoteList;

    public Adapter_PropertySwipe(Context context) {
        this.context = context;
        mPhotoList = new ArrayList<>();
        mNoteList = new ArrayList<>();
    }

    public void setPhotoList(List<String> photoList) {
        mPhotoList.clear();
        mPhotoList.addAll(photoList);
        notifyDataSetChanged();
    }

    public void setNoteList(List<Note> noteList) {
        mNoteList.clear();
        mNoteList.addAll(noteList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mNoteList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.view_property_slideshow, container, false);

        TextView title = (TextView) item_view.findViewById(R.id.textView_title);
        TextView note = (TextView) item_view.findViewById(R.id.textView_note);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.imageView_property);

        title.setText(mNoteList.get(position).getCommentTitle());
       note.setText(mNoteList.get(position).getDescription());

        //   imageView.setImageResource(mImageList.get(position));
       // Glide.with(context).load(mPhotoList.get(position)).asBitmap().into(imageView);
        Glide.with(context).load(mNoteList.get(position).getPhoto()).asBitmap().into(imageView);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
   //     container.removeView((LinearLayout)object);
        container.removeView((ConstraintLayout)object);
    }
}
