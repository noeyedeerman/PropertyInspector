package sit374_team17.propertyinspector;

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

    private PropertyItemListener mListener;

    interface PropertyItemListener {
        void onItemClicked(Property property);
    }

    Adapter_Properties(PropertyItemListener listener) {
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

        holder.address.setText(mPropertyList.get(position).get_address());
        holder.bedrooms.setText(String.valueOf(mPropertyList.get(position).get_bedrooms()));
        holder.bathrooms.setText(String.valueOf(mPropertyList.get(position).get_bathrooms()));
        holder.garages.setText(String.valueOf(mPropertyList.get(position).get_garages()));
        holder.price.setText(String.valueOf(mPropertyList.get(position).get_price()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Property property = mPropertyList.get(holder.getAdapterPosition());
                mListener.onItemClicked(property);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address, bedrooms, bathrooms, garages, price;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            address = (TextView) itemView.findViewById(R.id.textView_address);
            bedrooms = (TextView) itemView.findViewById(R.id.textView_bedrooms);
            bathrooms = (TextView) itemView.findViewById(R.id.textView_bathrooms);
            garages = (TextView) itemView.findViewById(R.id.textView_garages);
            price = (TextView) itemView.findViewById(R.id.textView_price);
        }
    }
}