package sit374_team17.propertyinspector.Note;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.R;

import static android.support.v7.widget.RecyclerView.GONE;
import static android.support.v7.widget.RecyclerView.VISIBLE;

public class Adapter_Notes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mNotesList;
    private Adapter_Note_Stack mNoteStack;
    private String MY_BUCKET = "https://s3-ap-southeast-2.amazonaws.com/propertyinspector-userfiles-mobilehub-4404653/";
    AmazonS3 s3Client;
    private Listener mListener;
    //  private Listener_Note_List mListener_notes;
    Context mContext;
    boolean deleteVisable;
    RecyclerView mRecyclerView;


//
//    interface Listener_Note_List {
//        void onDeleteVisable();
//    }


    Adapter_Notes(Context context, Listener listener, CognitoCachingCredentialsProvider credentialsProvider) {
        mNotesList = new ArrayList<>();
        mContext = context;
        mListener = listener;
        //  mListener_notes = listener_notes;
        s3Client = new AmazonS3Client(credentialsProvider);
        // mContext = context;
    }


//    Adapter_Notes(Context context, Listener_Note_List listener, CognitoCachingCredentialsProvider credentialsProvider) {
//        mNotesList = new ArrayList<>();
//
//       // mListener = listener;
//        mContext = context;
//        s3Client = new AmazonS3Client(credentialsProvider);
//        // mContext = context;
//    }

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

//    @Override
//    public int getItemViewType(int position) {
//        // Just as an example, return 0 or 2 depending on position
//        // Note that unlike in ListView adapters, types don't have to be contiguous
////        if ("text".equals(mNotesList.get(position).getCommentType())) {
////            return 0;
////        } else {
////            return 1;
////        }
//        return 1;
//        //eturn position % 2 * 2;
//        //return position = 0;
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);


        return new ViewHolder(view);
        // case 2:
        //    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note_stack, parent, false);
        //   return new ViewHolder_stack(view);


        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
        // return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String note_title;
        deleteVisable = false;
        if (mNotesList.get(position).getCommentTitle() == null || mNotesList.get(position).getCommentTitle() == "") {
            note_title = "Note Title";
        } else {
            note_title = mNotesList.get(position).getCommentTitle();
        }


        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTitle.setText(note_title);
        viewHolder.mNote.setText(mNotesList.get(position).getDescription());

        if ("text".equals(mNotesList.get(position).getCommentType())) {
            viewHolder.mPhoto.setVisibility(GONE);
        } else {
            viewHolder.mPhoto.setVisibility(VISIBLE);
            Glide.with(mContext)
                    .load(MY_BUCKET.concat(mNotesList.get(position).getPhoto()))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(200, 200) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            viewHolder.mPhoto.setImageBitmap(resource); // Possibly runOnUiThread()
                        }
                    });
        }

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteVisable) {
                    deleteVisable = false;
                    notifyDataSetChanged();
                } else {
                    Note note = mNotesList.get(viewHolder.getAdapterPosition());
                    mListener.onNoteClicked(note);
                }
            }
        });

        viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if (!deleteVisable) {
                    deleteVisable = true;
                }
                viewHolder.mDelete.setVisibility(VISIBLE);
                return true;
            }

        });

        if (deleteVisable) {
            viewHolder.mDelete.setVisibility(VISIBLE);
        } else {
            viewHolder.mDelete.setVisibility(GONE);
        }

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(viewHolder.getAdapterPosition());
            }
        });

        //viewHolder_photo.mImage.setImageDrawable(mContext.getDrawable(mContext, R.drawable.clarke));
        //viewHolder_photo.mImage.setImageResource(mContext.getDrawable(mContext, R.drawable.clarke));

//            case 2:
//                ViewHolder_stack viewHolder_stack = (ViewHolder_stack)holder;
//                viewHolder_stack.mStack.setAdapter(mNoteStack);
//             //   viewHolder_stack.mNote.setText(mNotesList.get(position).getDescription());
//                //viewHolder_photo.mImage.setImageDrawable(mContext.getDrawable(mContext, R.drawable.clarke));
//                //viewHolder_photo.mImage.setImageResource(mContext.getDrawable(mContext, R.drawable.clarke));
//                break;


    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }


    public void deleteVisable(boolean bool) {
        deleteVisable = bool;
    }

//    @Override
//    public void onBindViewHolder(final Adapter_Notes.ViewHolder holder, int position) {
//        holder.mDescription.setText(mNotesList.get(position).getDescription());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //   Comment note = mCommentsList.get(holder.getAdapterPosition());
//                //    mListener.onItemClicked(note);
//            }
//        });
//    }

//    private class ViewHolder_text extends RecyclerView.ViewHolder {
//        private View mView;
//        private TextView mTitle;
//        private TextView mNote;
//
//        ViewHolder_text(View itemView) {
//            super(itemView);
//            mView = itemView;
//            mTitle = (TextView) itemView.findViewById(R.id.textView_title);
//            mNote = (TextView) itemView.findViewById(R.id.textView_note);
//
//        }
//    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitle;
        private TextView mNote;
        private ImageView mPhoto;
        private ImageButton mDelete;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.textView_title);
            mNote = (TextView) itemView.findViewById(R.id.textView_note);
            mPhoto = (ImageView) itemView.findViewById(R.id.imageView_photo);
            mDelete = itemView.findViewById(R.id.button_delete);
            //  itemView.setOnClickListener((OnClickListener) this);
        }


//        @Override
//        public void onClick(View view) {
//            if(deleteVisable) {
//                mDelete.setVisibility(GONE);
//                Toast.makeText(view.getContext(), "Delete: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                deleteVisable = false;
//            } else{
//              //  Note note = mNotesList.get(getAdapterPosition());
//                   //   mListener.goTo_NoteEditActivity(note, "don't");
//                Toast.makeText(view.getContext(), "Go to: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                }
//        }
    }

//    private class ViewHolder_stack extends RecyclerView.ViewHolder {
//        private View mView;
//        private TextView mTitle;
//        private TextView mNote;
//        private ImageView mImage;
//        private StackView mStack;
//
//        ViewHolder_stack(View itemView) {
//            super(itemView);
//            mView = itemView;
//            mStack = (StackView) itemView.findViewById(R.id.stackView_note);
//
//        }
//    }
}