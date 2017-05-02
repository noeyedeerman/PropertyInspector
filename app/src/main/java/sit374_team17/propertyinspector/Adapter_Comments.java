package sit374_team17.propertyinspector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class Adapter_Comments extends RecyclerView.Adapter<Adapter_Comments.ViewHolder> {
    private List<Comment> mCommentsList;

    private Listener mListener;
Context mContext;

    Adapter_Comments(Listener listener) {
        mCommentsList = new ArrayList<>();
        mListener = listener;
       // mContext = context;
    }



    public void setCommentList(List<Comment> commentsList) {
        mCommentsList.clear();
        mCommentsList.addAll(commentsList);
        notifyDataSetChanged();
    }

    public Comment removeItem(int position) {
        final Comment comment = mCommentsList.remove(position);
        notifyItemRemoved(position);
        return comment;
    }

    public void addItem(int position, Comment comment) {
        mCommentsList.add(position, comment);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Comment comment = mCommentsList.remove(fromPosition);
        mCommentsList.add(toPosition, comment);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Comment> comment) {
        applyAndAnimateRemovals(comment);
        applyAndAnimateAdditions(comment);
        applyAndAnimateMovedItems(comment);
    }

    private void applyAndAnimateRemovals(List<Comment> newModels) {
        for (int i = mCommentsList.size() - 1; i >= 0; i--) {
            final Comment comment = mCommentsList.get(i);
            if (!newModels.contains(comment)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<Comment> newComments) {
        for (int i = 0, count = newComments.size(); i < count; i++) {
            final Comment comment = newComments.get(i);
            if (!mCommentsList.contains(comment)) {
                addItem(i, comment);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Comment> newComments) {
        for (int toPosition = newComments.size() - 1; toPosition >= 0; toPosition--) {
            final Comment comment = newComments.get(toPosition);
            final int fromPosition = mCommentsList.indexOf(comment);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    @Override
    public Adapter_Comments.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    @Override
    public void onBindViewHolder(final Adapter_Comments.ViewHolder holder, int position) {
        holder.mDescription.setText(mCommentsList.get(position).getDescription());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   Comment comment = mCommentsList.get(holder.getAdapterPosition());
            //    mListener.onItemClicked(comment);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDescription;
        private View mView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDescription = (TextView) itemView.findViewById(R.id.textView_comment);

        }
    }
}