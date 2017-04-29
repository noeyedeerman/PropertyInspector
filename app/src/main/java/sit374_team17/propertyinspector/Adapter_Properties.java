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

class Adapter_Properties extends RecyclerView.Adapter<Adapter_Properties.ViewHolder> implements Filterable {
    private List<Property> mPropertyList;
    private List<Property> mFilteredList;

    private PropertyItemListener mListener;

    interface PropertyItemListener {
        void onItemClicked(Property property);
    }

    Adapter_Properties(List<Property> propertyList, PropertyItemListener listener) {
        mPropertyList = propertyList;
        mFilteredList = propertyList;
        mListener = listener;
    }

    @Override
    public Adapter_Properties.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        //return mPropertyList.size();
        return mFilteredList.size();
    }


    @Override
    public void onBindViewHolder(final Adapter_Properties.ViewHolder holder, int position) {

        holder.name.setText(mFilteredList.get(position).get_address());
        // viewHolder.name.setText(mFilteredList.get(i).get_bedrooms());
        //  viewHolder.name.setText(mFilteredList.get(i).get_bathrooms());
        // viewHolder.name.setText(mFilteredList.get(i).get_garages());
        //  viewHolder.name.setText(mFilteredList.get(i).get_price());
        // viewHolder.tv_api_level.setText(mFilteredList.get(i).getImage());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Property property = mPropertyList.get(holder.getAdapterPosition());
                mListener.onItemClicked(property);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, image;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.textView_propertyName);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mPropertyList;
                } else {
                    ArrayList<Property> filteredList = new ArrayList<>();
                    for (Property property : mPropertyList) {
                        if (property.get_address().toLowerCase().contains(charString)) {
                            filteredList.add(property);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Property>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}