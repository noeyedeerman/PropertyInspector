package sit374_team17.propertyinspector.Note;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.R;

public class Adapter_Note extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mNotesList;
    private Adapter_Note_Stack mNoteStack;

    private Listener mListener;
    Context mContext;

    Adapter_Note(Listener listener) {
        mNotesList = new ArrayList<>();
        mNoteStack = new Adapter_Note_Stack((Context) listener, mNotesList);
        mListener = listener ;
        // mContext = context;
    }

    public void setNoteList(List<Note> notesList) {
        mNotesList.clear();
        mNotesList.addAll(notesList);
        notifyDataSetChanged();
    }

    public Note removeItem(int position) {
        final Note note = mNotesList.remove(position);
        notifyItemRemoved(position);
        return note;
    }

    public void addItem(int position, Note note) {
        mNotesList.add(position, note);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Note note = mNotesList.remove(fromPosition);
        mNotesList.add(toPosition, note);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Note> note) {
        applyAndAnimateRemovals(note);
        applyAndAnimateAdditions(note);
        applyAndAnimateMovedItems(note);
    }

    private void applyAndAnimateRemovals(List<Note> newModels) {
        for (int i = mNotesList.size() - 1; i >= 0; i--) {
            final Note note = mNotesList.get(i);
            if (!newModels.contains(note)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<Note> newNotes) {
        for (int i = 0, count = newNotes.size(); i < count; i++) {
            final Note note = newNotes.get(i);
            if (!mNotesList.contains(note)) {
                addItem(i, note);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Note> newNotes) {
        for (int toPosition = newNotes.size() - 1; toPosition >= 0; toPosition--) {
            final Note note = newNotes.get(toPosition);
            final int fromPosition = mNotesList.indexOf(note);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if ("private".equals(mNotesList.get(position).getCommentType())) {
            return 0;
        } else {
            return 1;
        }

      //eturn position % 2 * 2;
        //return position = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note_text, parent, false);
                return new ViewHolder_text(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note_photo, parent, false);
                return new ViewHolder_photo(view);
           // case 2:
            //    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note_stack, parent, false);
             //   return new ViewHolder_stack(view);


            default:
                return null;
        }

      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
       // return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String note_title;

        if (mNotesList.get(position).getCommentTitle() == null || mNotesList.get(position).getCommentTitle() == ""){
            note_title = "Note Title";
        } else {
            note_title = mNotesList.get(position).getCommentTitle();
        }

        switch (holder.getItemViewType()) {
            case 0:
                final ViewHolder_text viewHolder_text = (ViewHolder_text)holder;
                viewHolder_text.mTitle.setText(note_title);
                viewHolder_text.mNote.setText(mNotesList.get(position).getDescription());

                viewHolder_text.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Note note = mNotesList.get(viewHolder_text.getAdapterPosition());
                        mListener.goTo_NoteEditActivity(note, "don't");
                    }
                });
                break;

            case 1:
                final ViewHolder_photo viewHolder_photo = (ViewHolder_photo)holder;
                viewHolder_photo.mTitle.setText(note_title);
                viewHolder_photo.mNote.setText(mNotesList.get(position).getDescription());

                viewHolder_photo.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Note note = mNotesList.get(viewHolder_photo.getAdapterPosition());
                        mListener.goTo_NoteEditActivity(note, "don't");
                    }
                });
                //viewHolder_photo.mImage.setImageDrawable(mContext.getDrawable(mContext, R.drawable.clarke));
                //viewHolder_photo.mImage.setImageResource(mContext.getDrawable(mContext, R.drawable.clarke));
                break;
//            case 2:
//                ViewHolder_stack viewHolder_stack = (ViewHolder_stack)holder;
//                viewHolder_stack.mStack.setAdapter(mNoteStack);
//             //   viewHolder_stack.mNote.setText(mNotesList.get(position).getDescription());
//                //viewHolder_photo.mImage.setImageDrawable(mContext.getDrawable(mContext, R.drawable.clarke));
//                //viewHolder_photo.mImage.setImageResource(mContext.getDrawable(mContext, R.drawable.clarke));
//                break;
        }


    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

//    @Override
//    public void onBindViewHolder(final Adapter_Note.ViewHolder holder, int position) {
//        holder.mDescription.setText(mNotesList.get(position).getDescription());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //   Comment note = mCommentsList.get(holder.getAdapterPosition());
//                //    mListener.onItemClicked(note);
//            }
//        });
//    }

    private class ViewHolder_text extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitle;
        private TextView mNote;

        ViewHolder_text(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.textView_title);
            mNote = (TextView) itemView.findViewById(R.id.textView_note);

        }
    }

    private class ViewHolder_photo extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitle;
        private TextView mNote;
        private ImageView mImage;

        ViewHolder_photo(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.textView_title);
            mNote = (TextView) itemView.findViewById(R.id.textView_note);
            mImage = (ImageView) itemView.findViewById(R.id.imageView_image);

        }
    }

    private class ViewHolder_stack extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitle;
        private TextView mNote;
        private ImageView mImage;
        private StackView mStack;

        ViewHolder_stack(View itemView) {
            super(itemView);
            mView = itemView;
            mStack = (StackView) itemView.findViewById(R.id.stackView_note);

        }
    }
}