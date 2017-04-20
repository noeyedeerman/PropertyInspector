package sit374_team17.propertyinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by callu on 19/04/2017.
 */

 class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<Property> mArrayList;
    private ArrayList<Property> mFilteredList;


     DataAdapter(ArrayList<Property> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_property, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.name.setText(mFilteredList.get(i).get_address());
       // viewHolder.name.setText(mFilteredList.get(i).get_bedrooms());
      //  viewHolder.name.setText(mFilteredList.get(i).get_bathrooms());
       // viewHolder.name.setText(mFilteredList.get(i).get_garages());
      //  viewHolder.name.setText(mFilteredList.get(i).get_price());
       // viewHolder.tv_api_level.setText(mFilteredList.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Property> filteredList = new ArrayList<>();

                    for (Property property : mArrayList) {

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

     class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,image;
         ViewHolder(View view) {
            super(view);

            name = (TextView)view.findViewById(R.id.textView_propertyName);

            //  tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);



        }
    }

}