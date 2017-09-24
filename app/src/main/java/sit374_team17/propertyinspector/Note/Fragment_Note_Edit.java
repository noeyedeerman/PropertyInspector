package sit374_team17.propertyinspector.Note;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Note_Edit extends Fragment {

    private static final String ARG_NOTE = "note";


    private String mParam1;
    private String mParam2;

    private MenuItem mNotesItem;

    private View mView;
    private Note mNote;
    private DynamoDBMapper mapper;
    protected static String PROPERTY_ID="";

    private Property mProperty;

    private Button button_save;
    private EditText mEditText_title, mEditText_note;
private ImageView imageView;
    private String mCurrentPhotoPath;
    private Parcelable mImageBitmap;

    public Fragment_Note_Edit() {
    }


    public static Fragment_Note_Edit newInstance(Note note) {
        Fragment_Note_Edit fragment = new Fragment_Note_Edit();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mNote = getArguments().getParcelable(ARG_NOTE);
        }

        //  mDB_comments = new DB_CommentHandler(getContext());
        // if (getArguments() != null) {
        //     mComment = getArguments().getParcelable(ARG_PROPERTY);
        //  }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  if (mNote.getCommentType() == "private") {
          if ("private".equals(mNote.getCommentType())) {
              mView = inflater.inflate(R.layout.fragment_note_text_edit, container, false);
        } else  if ("public".equals(mNote.getCommentType())) {
            mView = inflater.inflate(R.layout.fragment_note_photo_edit, container, false);
                 imageView = (ImageView) mView.findViewById(R.id.imageView);
        }

        if (mView != null) {
            button_save = (Button) mView.findViewById(R.id.button_save);
            mEditText_title = (EditText) mView.findViewById(R.id.editText_title);
            mEditText_note = (EditText) mView.findViewById(R.id.editText_note);

           // mEditText_title.setText(mNote.getCommentTitle());
            mEditText_note.setText(mNote.getDescription());

            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveComment();
                    getActivity().finish();
                }
            });

        }

        //PROPERTY_ID=mProperty.getId();
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);

        return mView;
    }





    private void saveComment() {
        String note = mEditText_note.getText().toString();
        mNote = new Note();
        mNote.setPropertyId(mProperty.getId());
        mNote.setDescription(note);
       // if (isPublic)mNote.setCommentType("public");
        mNote.setCommentType("private");
        Toast.makeText(getActivity(),"Submitting please wait",Toast.LENGTH_SHORT).show();
        if (!note.equals(""))
        {
            Runnable runnable = new Runnable() {
                public void run() {
                    //DynamoDB calls go here
                    mapper.save(mNote);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Note submitted successfully",Toast.LENGTH_SHORT).show();
//                            editText_note.setText("");
//                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                            Fragment fragment_tabs = new Fragment_Tabs();
//                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                            //  transaction.add(R.id.tabHost_comments, fragment_tabs).commit();
                        }
                    });
                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();
        }
        else
            Toast.makeText(getActivity(),"Empty post are not allowed",Toast.LENGTH_LONG).show();

      //  mListener.onSaveComment();
    }
}
