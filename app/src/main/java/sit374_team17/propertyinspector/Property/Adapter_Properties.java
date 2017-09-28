package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.R;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class Adapter_Properties extends RecyclerView.Adapter<Adapter_Properties.ViewHolder> {
    private List<Property> mPropertyList;
    private Context mContext;
    private Listener mListener;
    private String MY_BUCKET="https://s3-ap-southeast-2.amazonaws.com/propertyinspector-userfiles-mobilehub-4404653/";
    List<Photo> result;
    AmazonS3 s3Client;
    String picDetails="";
    InputStream objectData;
    Bitmap bitmap1;

    public Adapter_Properties(Listener listener, Context context, CognitoCachingCredentialsProvider credentialsProvider, List<Photo> result) {
        mPropertyList = new ArrayList<>();
        mListener = listener;
        mContext = context;
        this.result = result;
        s3Client = new AmazonS3Client(credentialsProvider);
    }

    public void setPropertyList(List<Property> propertiesList) {
        mPropertyList.clear();
        mPropertyList.addAll(propertiesList);
        notifyDataSetChanged();
    }

    public Property removeItem(int position) {
        final Property property = mPropertyList.remove(position);
        notifyItemRemoved(position);
        return property;
    }

    public void addItem(int position, Property property) {
        mPropertyList.add(position, property);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Property property = mPropertyList.remove(fromPosition);
        mPropertyList.add(toPosition, property);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Property> property) {
        applyAndAnimateRemovals(property);
        applyAndAnimateAdditions(property);
        applyAndAnimateMovedItems(property);
    }

    private void applyAndAnimateRemovals(List<Property> newModels) {
        for (int i = mPropertyList.size() - 1; i >= 0; i--) {
            final Property property = mPropertyList.get(i);
            if (!newModels.contains(property)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<Property> newProperties) {
        for (int i = 0, count = newProperties.size(); i < count; i++) {
            final Property property = newProperties.get(i);
            if (!mPropertyList.contains(property)) {
                addItem(i, property);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Property> newProperties) {
        for (int toPosition = newProperties.size() - 1; toPosition >= 0; toPosition--) {
            final Property property = newProperties.get(toPosition);
            final int fromPosition = mPropertyList.indexOf(property);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    @Override
    public Adapter_Properties.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }

    @Override
    public void onBindViewHolder(final Adapter_Properties.ViewHolder holder, final int position) {
        holder.mDescription.setText(String.valueOf(mPropertyList.get(position).getDescription()));
        holder.mAddress.setText(String.valueOf(mPropertyList.get(position).getAddress()));
        holder.mCity.setText(String.valueOf(mPropertyList.get(position).getCity()));
        holder.mState.setText(String.valueOf(mPropertyList.get(position).getState().get(0)));
        holder.mPostCode.setText(String.valueOf(mPropertyList.get(position).getPostCode()));
        holder.mBedrooms.setText(String.valueOf(mPropertyList.get(position).getBedrooms().get(0)));
        holder.mBathrooms.setText(String.valueOf(mPropertyList.get(position).getBathrooms().get(0)));
        if (!mPropertyList.get(position).getCars().isEmpty())
        holder.mCars.setText(String.valueOf(mPropertyList.get(position).getCars().get(0)));
        holder.mPrice.setText("$".concat(String.valueOf(mPropertyList.get(position).getPrice().get(0))));
        picDetails="";
        for (int k=0;k<result.size();k++)
        {
            if (result.get(k).getPropertyId().equals(mPropertyList.get(position).getId()))
            {
                picDetails=result.get(k).getPicDetails();
                mPropertyList.get(position).setPhoto(MY_BUCKET.concat(picDetails));
                break;
            }
        }
        if (!picDetails.equals("")) {
            Glide.with(mContext)
                    .load(MY_BUCKET.concat(picDetails))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(200,200) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            holder.mPhoto.setImageBitmap(resource); // Possibly runOnUiThread()
                        }
                    });
        } else {
            holder.mPhoto.setImageDrawable( getDrawable(mContext, R.drawable.house));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Property property = mPropertyList.get(holder.getAdapterPosition());
                mListener.onPropertyClicked(property);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDescription, mAddress, mCity, mState, mPostCode, mBedrooms, mBathrooms, mCars, mPrice;
        private ImageView mPhoto;
        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDescription = (TextView) itemView.findViewById(R.id.textView_description);
            mAddress = (TextView) itemView.findViewById(R.id.textView_address);
            mCity = (TextView) itemView.findViewById(R.id.textView_city);
            mState = (TextView) itemView.findViewById(R.id.textView_state);
            mPostCode = (TextView) itemView.findViewById(R.id.textView_postCode);
            mBedrooms = (TextView) itemView.findViewById(R.id.textView_bedrooms);
            mBathrooms = (TextView) itemView.findViewById(R.id.textView_bathrooms);
            mCars = (TextView) itemView.findViewById(R.id.textView_cars);
            mPrice = (TextView) itemView.findViewById(R.id.textView_price);

            mPhoto = (ImageView) itemView.findViewById(R.id.imageView_property);
        }
    }
}