package sit374_team17.propertyinspector.Walkthrough;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.R;

public class Adapter_Moval extends RecyclerView.Adapter<Adapter_Moval.ViewHolder> {
    private List<Moval> mList;
    private Context mContext;
    private Listener_Walkthrough mListener;

    public Adapter_Moval(Listener_Walkthrough listener, Context context) {
        mList = new ArrayList<>();
        mListener = listener;
        mContext = context;
    }

    public void setList(ArrayList<Moval> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Adapter_Moval.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_walkthrough, parent, false);
        return new Adapter_Moval.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(final Adapter_Moval.ViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moval moval = mList.get(holder.getAdapterPosition());
                mListener.onClick_moval(moval);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) itemView.findViewById(R.id.textView_name);

        }
    }
}