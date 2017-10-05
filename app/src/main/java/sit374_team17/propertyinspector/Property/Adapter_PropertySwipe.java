package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;

import static sit374_team17.propertyinspector.Main.Fragment_Home.credentialsProvider;

public class Adapter_PropertySwipe extends PagerAdapter {
    //  private int[] image_resource = {R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property};
    private String MY_BUCKET = "https://s3-ap-southeast-2.amazonaws.com/propertyinspector-userfiles-mobilehub-4404653/";
    AmazonS3 s3Client;
    private Context context;
    private LayoutInflater layoutInflater;
    List<String> mPhotoList;
    List<Note> mNoteList;
    private Listener mListener;

    public Adapter_PropertySwipe(Context context, Listener listener) {
        this.context = context;
        mListener = listener;
        mPhotoList = new ArrayList<>();
        mNoteList = new ArrayList<>();
        s3Client = new AmazonS3Client(credentialsProvider);
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
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.view_property_slideshow, container, false);

        TextView title = (TextView) item_view.findViewById(R.id.textView_title);
        TextView note = (TextView) item_view.findViewById(R.id.textView_note);

        final ImageView imageView = (ImageView) item_view.findViewById(R.id.imageView_property);

        title.setText(mNoteList.get(position).getCommentTitle());
        note.setText(mNoteList.get(position).getDescription());

        //   imageView.setImageResource(mImageList.get(position));
        // Glide.with(context).load(mPhotoList.get(position)).asBitmap().into(imageView);

        if (!mNoteList.get(position).getPhoto().equals("None"))
            Glide.with(context)
                    .load(MY_BUCKET.concat(mNoteList.get(position).getPhoto()))
                    .asBitmap()
                    .placeholder(R.drawable.ic_camera_plus).centerCrop()
                    .into(new SimpleTarget<Bitmap>(200, 200) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            imageView.setImageBitmap(resource); // Possibly runOnUiThread()
                        }
                    });
        container.addView(item_view);

        item_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onNoteClicked(mNoteList.get(position));
                return true;
            }
        });
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
