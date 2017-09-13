package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.R;

public class Adapter_Gas extends RecyclerView.Adapter<Adapter_Gas.ViewHolder> {
    private List<Gas> mList;
    private Context mContext;
    private Listener_Walkthrough mListener;

    public Adapter_Gas(Listener_Walkthrough listener, Context context) {
        mList = new ArrayList<>();
        mListener = listener;
        mContext = context;
    }

    public void setList(ArrayList<Gas> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Adapter_Gas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_walkthrough, parent, false);
        return new Adapter_Gas.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(final Adapter_Gas.ViewHolder holder, int position) {
        holder.mLogo.setImageDrawable(mList.get(position).getLogo());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gas gas = mList.get(holder.getAdapterPosition());
                mListener.goTo_5_gas_details(gas);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private ImageView mLogo;
        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) itemView.findViewById(R.id.textView_name);
            mLogo = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}