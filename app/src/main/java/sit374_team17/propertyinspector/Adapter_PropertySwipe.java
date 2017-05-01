package sit374_team17.propertyinspector;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.math.BigDecimal;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_PropertySwipe extends PagerAdapter {
  //  private int[] image_resource = {R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property, R.drawable.ic_property};

    private Context context;
    private LayoutInflater layoutInflater;
List<Bitmap> mPhotoList;

    public Adapter_PropertySwipe(Context context) {
        this.context = context;
        mPhotoList = new ArrayList<>();
    }

    public void setPhotoList(List<Bitmap> photoList) {
        mPhotoList.clear();
        mPhotoList.addAll(photoList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.view_property_slideshow, container, false);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.imageView_property);

     //   imageView.setImageResource(mImageList.get(position));
imageView.setImageBitmap(mPhotoList.get(position));
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);

    }
}
