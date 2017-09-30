package sit374_team17.propertyinspector.Note;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.StackView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.ArrayList;
import java.util.List;

import sit374_team17.propertyinspector.Comment;
import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;
import sit374_team17.propertyinspector.User.Activity_Login;

import static android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class Fragment_Note_List extends Fragment {
    private static final String ARG_PROPERTY = "comment";
    private static final String ARG_NOTELIST = "noteList";

    protected List<Note> mNoteList;
    protected DynamoDBMapper mapper;
    private Note mComment;
    private Property mProperty;
    private Listener mListener;
    //private DB_CommentHandler mDB_comments;
    private View mView;
    RecyclerView mRecyclerView;
    Adapter_Notes mCommentsAdapter;
    List<Comment> mCommentsList;
    public static String PROPERTY_ID = "";
    private StackView stackView;
    private OnRefreshListener swipeRefreshListner;
    private boolean deleteVisable = false;


    private Listener_onTouch mListener_touch;
    public interface Listener_onTouch {
        void onTouchOutside();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof Listener) {
//            mListener = (Listener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement Listener");
//        }

        try {
            mListener = (Listener) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement Listener");
        }




//        if (context instanceof Listener_onTouch) {
//            mListener_touch = (Listener_onTouch) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement Listener_onTouch");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    public static Fragment_Note_List newInstance(Property property) {
        Fragment_Note_List fragment = new Fragment_Note_List();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        //args.putParcelable(ARG_NOTELIST, (Parcelable) noteList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDB_comments = new DB_CommentHandler(getContext());
        //   mProperty = new DB_PropertyHandler(getContext());
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable((ARG_PROPERTY));
            //   mNoteList = getArguments().getParcelable(ARG_NOTELIST);
        }

    }

    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_note_list, container, false);
        mySwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefresh);

        mCommentsList = new ArrayList<>();
        PROPERTY_ID = mProperty.getId();
        initViews();
        // stackView = (StackView) mView.findViewById(R.id.stackView_note);
        //     Adapter_Note_Stack noteStackAdapter = new Adapter_Note_Stack(getContext(), result);
        //stackView.setAdapter(noteStackAdapter);
        //   noteStackAdapter.notifyDataSetChanged();
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
        return mView;
    }


    private void initViews() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_notes);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
//        layoutManager = new LinearLayoutManager(getContext()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//loadNotes();
        //  refreshNotes();
        //  mySwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setLayoutManager(layoutManager);


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    // tapped on child]
                    if (deleteVisable) {
                        Toast.makeText(getContext(), "Child", Toast.LENGTH_SHORT).show();
                        deleteVisable = false;
                     //   Adapter_Notes.deleteVisable(false)
                    }
                    return false;
                } else {
                  //  Toast.makeText(getContext(), "Outside", Toast.LENGTH_SHORT).show();
                  //  mListener_touch.onTouchOutside();
                    mCommentsAdapter.deleteVisable(false);
                    // Tap occured outside all child-views.
                    // do something
                    return true;
                }
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
//        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
//        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
//        mapper = new DynamoDBMapper(ddbClient);
//        Runnable runnable = new Runnable() {
//            public void run() {
//                //DynamoDB calls go here
//                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//                scanExpression.addFilterCondition("PropertyID", new Condition()
//                        .withComparisonOperator(ComparisonOperator.EQ)
//                        .withAttributeValueList(new AttributeValue().withS(Fragment_Note_List.PROPERTY_ID)));
//                scanExpression.addFilterCondition("Username",
//                        new Condition()
//                                .withComparisonOperator(ComparisonOperator.EQ)
//                                .withAttributeValueList(new AttributeValue().withS(Activity_Login.mUser)));
//                mNoteList = mapper.scan(Note.class, scanExpression);
//                if (mNoteList.size() >= 0) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mCommentsAdapter = new Adapter_Notes(getActivity(),mListener,Fragment_Home.credentialsProvider);
//                            mCommentsAdapter.setNoteList(mNoteList);
//                            mRecyclerView.setAdapter(mCommentsAdapter);
//                        }
//                    });
//                }
//            }
//        };
//        Thread mythread = new Thread(runnable);
//        mythread.start();
//


          /*
 * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
 * performs a swipe-to-refresh gesture.
 */

//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
//                        loadNotes();
//                        mySwipeRefreshLayout.setRefreshing(false);
//                         Toast.makeText(getActivity(),"Updated",Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );


        swipeRefreshListner = new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                loadNotes();
                mySwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
            }
        };
        mySwipeRefreshLayout.setOnRefreshListener(swipeRefreshListner);

        refreshNotes();
//        mySwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mySwipeRefreshLayout.setRefreshing(true);
//                swipeRefreshListner.onRefresh();
//
//            }
//        });


    }


    public void refreshNotes() {
        mySwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mySwipeRefreshLayout.setRefreshing(true);
                swipeRefreshListner.onRefresh();

            }
        });
    }

    public void loadNotes() {
        mCommentsAdapter = new Adapter_Notes(getContext(), mListener, Fragment_Home.credentialsProvider);

        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                scanExpression.addFilterCondition("PropertyID", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withS(Fragment_Note_List.PROPERTY_ID)));
                scanExpression.addFilterCondition("Username",
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(new AttributeValue().withS(Activity_Login.mUser)));
                mNoteList = mapper.scan(Note.class, scanExpression);
                if (mNoteList.size() >= 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCommentsAdapter.setNoteList(mNoteList);

                            mRecyclerView.setAdapter(mCommentsAdapter);


                        }
                    });
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }


    public void onDeleteVisable() {
        deleteVisable = true;
    }
}
