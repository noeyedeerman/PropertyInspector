package sit374_team17.propertyinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by callu on 2/05/2017.
 */

public class Fragment_PublicComments extends Fragment {
    private ArrayList<Comment> mCommentsList;

    private static final String ARG_PROPERTY = "comment";

    private Property mComment;



    private Listener mListener;
    private DB_CommentHandler mDB_comments;
    private View mView;
    RecyclerView mRecyclerView;
    private Adapter_Comments mCommentsAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static Fragment_Property newInstance(Property property) {
        Fragment_Property fragment = new Fragment_Property();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB_comments = new DB_CommentHandler(getContext());
        if (getArguments() != null) {
            mComment = getArguments().getParcelable(ARG_PROPERTY);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_public_comments, container, false);


        mCommentsList = new ArrayList<>();

        Comment comment1 = new Comment(0, 0, 0, true, "In efficitur quam congue leo eleifend, ut iaculis eros congue.");
        Comment comment2 = new Comment(1, 0, 0, true, "Aliquam in mi vel leo dignissim dapibus in sed justo.");
        Comment comment3 = new Comment(2, 0, 0, true, "Nunc elit turpis, ornare ut dolor quis, viverra tincidunt nisi. Pellentesque porttitor ut justo bibendum aliquet. Vestibulum ante ipsum primis.");
        Comment comment4 = new Comment(3, 0, 0, true, "Curabitur sagittis faucibus mauris id finibus. Proin lacus lacus, pulvinar ut arcu a, finibus rutrum leo. Sed dapibus.");
        Comment comment5 = new Comment(4, 0, 0, true, "Vestibulum ante ipsum primis in faucibus orci.");

        mCommentsList.add(comment1);
        mCommentsList.add(comment2);
        mCommentsList.add(comment3);
        mCommentsList.add(comment4);
        mCommentsList.add(comment5);


        initViews();


        if (mCommentsList.size() >= 0) {
            mCommentsAdapter = new Adapter_Comments(mListener);
            mCommentsAdapter.setCommentList(mCommentsList);
            mRecyclerView.setAdapter(mCommentsAdapter);
        }


        return mView;
    }


    private void initViews() {
      mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_commentPublic);
       mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;



        layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(layoutManager);
    }

}
