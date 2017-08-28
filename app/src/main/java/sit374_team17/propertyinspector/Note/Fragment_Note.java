//package sit374_team17.propertyinspector.Note;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//
//import java.util.List;
//
//import sit374_team17.propertyinspector.Main.Listener;
//import sit374_team17.propertyinspector.Property.Property;
//import sit374_team17.propertyinspector.R;
//
///**
// * Created by callu on 2/05/2017.
// */
//
//public class Fragment_Note extends Fragment {
//    private static final String ARG_PROPERTY = "comment";
//
//    protected List<Note> result;
//    protected DynamoDBMapper mapper ;
//    private Note mComment;
//    private Property mProperty;
//    private Listener mListener;
//  //  private DB_CommentHandler mDB_comments;
//    private View mView;
//    RecyclerView mRecyclerView;
//    private Adapter_Note mCommentsAdapter;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof Listener) {
//            mListener = (Listener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement Listener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public static Fragment_Note newInstance(Property property) {
//        Fragment_Note fragment = new Fragment_Note();
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_PROPERTY, property);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       // mDB_comments = new DB_CommentHandler(getContext());
//        if (getArguments() != null) {
//           // mComment = getArguments().getParcelable(ARG_PROPERTY);
//        }
//    }
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_note_list, container, false);
//
//        initViews();
//        return mView;
//    }
//
//
//    private void initViews() {
//        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_commentPrivate);
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager;
//
//
//
//        layoutManager = new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//        mRecyclerView.setLayoutManager(layoutManager);
//
////        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
////        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
////        mapper = new DynamoDBMapper(ddbClient);
////        Runnable runnable = new Runnable() {
////            public void run() {
////                //DynamoDB calls go here
////                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
////                scanExpression.addFilterCondition("PropertyID",new Condition()
////                        .withComparisonOperator(ComparisonOperator.EQ)
////                        .withAttributeValueList(new AttributeValue().withS(Fragment_Property.PROPERTY_ID)));
////                scanExpression.addFilterCondition("CommentType",
////                        new Condition()
////                                .withComparisonOperator(ComparisonOperator.EQ)
////                                .withAttributeValueList(new AttributeValue().withS("private")));
////                result = mapper.scan(Note.class, scanExpression);
////                if (result.size() >= 0) {
////                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            mCommentsAdapter = new Adapter_Note(mListener);
////                            mCommentsAdapter.setCommentList(result);
////                            mRecyclerView.setAdapter(mCommentsAdapter);
////                        }
////                    });
////                }
////            }
////        };
////        Thread mythread = new Thread(runnable);
////        mythread.start();
//   }
//
//}
