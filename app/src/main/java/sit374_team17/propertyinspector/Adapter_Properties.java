package sit374_team17.propertyinspector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class Adapter_Properties extends RecyclerView.Adapter<Adapter_Properties.ViewHolder> {
    private List<Property> mPropertyList;

    Listener mListener;



    Adapter_Properties(Listener listener) {
        mPropertyList = new ArrayList<>();
        mListener = listener;
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
    public void onBindViewHolder(final Adapter_Properties.ViewHolder holder, int position) {
        holder.mStreetNumber.setText(mPropertyList.get(position).getStreetNumber());
        holder.mStreetName.setText(mPropertyList.get(position).getStreetName());
        holder.mCity.setText(mPropertyList.get(position).getCity());
        holder.mState.setText(mPropertyList.get(position).getState());
        holder.mPostCode.setText(mPropertyList.get(position).getPostCode());
        holder.mBedrooms.setText(mPropertyList.get(position).getBedrooms());
        holder.mBathrooms.setText(String.valueOf(mPropertyList.get(position).getBathrooms()));
        holder.mCars.setText(String.valueOf(mPropertyList.get(position).getCars()));
        holder.mPrice.setText(String.valueOf(mPropertyList.get(position).getPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Property property = mPropertyList.get(holder.getAdapterPosition());
                mListener.onItemClicked(property);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mStreetNumber, mStreetName, mCity, mState, mPostCode, mBedrooms, mBathrooms, mCars, mPrice;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mStreetNumber = (TextView) itemView.findViewById(R.id.textView_streetNumber);
            mStreetName = (TextView) itemView.findViewById(R.id.textView_streetName);
            mCity = (TextView) itemView.findViewById(R.id.textView_city);
            mState = (TextView) itemView.findViewById(R.id.textView_state);
            mPostCode = (TextView) itemView.findViewById(R.id.textView_postCode);
            mBedrooms = (TextView) itemView.findViewById(R.id.textView_bedrooms);
            mBathrooms = (TextView) itemView.findViewById(R.id.textView_bathrooms);
            mCars = (TextView) itemView.findViewById(R.id.textView_cars);
            mPrice = (TextView) itemView.findViewById(R.id.textView_price);
        }
    }
}