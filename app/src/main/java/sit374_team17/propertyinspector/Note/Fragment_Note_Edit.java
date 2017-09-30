package sit374_team17.propertyinspector.Note;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;
import sit374_team17.propertyinspector.User.Activity_Login;

import static sit374_team17.propertyinspector.Main.Fragment_Home.credentialsProvider;

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
Listener_Note_Edit mListener;
    private Button button_save;
    private EditText mEditText_title, mEditText_note;
private ImageView imageView;
    private String mCurrentPhotoPath;
    private Parcelable mImageBitmap;

    public Fragment_Note_Edit() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener_Note_Edit) {
            mListener = (Listener_Note_Edit) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener_Note_Edit");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessages);
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

    String filePath="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  if (mNote.getCommentType() == "private") {

          if ("text".equals(mNote.getCommentType())) {
              mView = inflater.inflate(R.layout.fragment_note_text_edit, container, false);
        } else  if ("photo".equals(mNote.getCommentType())) {
            mView = inflater.inflate(R.layout.fragment_note_photo_edit, container, false);
                 imageView = (ImageView) mView.findViewById(R.id.imageView_image);
              Glide.with(getContext())
                      .load(MY_BUCKET.concat(mNote.getPhoto()))
                      .asBitmap()
                      .into(new SimpleTarget<Bitmap>(200,200) {
                          @Override
                          public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                              imageView.setImageBitmap(resource); // Possibly runOnUiThread()
                          }
                      });

        }

        if (mView != null) {
            button_save = (Button) mView.findViewById(R.id.button_save);
            mEditText_title = (EditText) mView.findViewById(R.id.editText_title);
            mEditText_note = (EditText) mView.findViewById(R.id.editText_note);

            mEditText_title.setText(mNote.getCommentTitle());
            mEditText_note.setText(mNote.getDescription());

            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!filePath.equals(""))
                        savePhotoComment();
                        else
                         saveComment();
                }
            });

        }

        //PROPERTY_ID=mProperty.getId();
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessages,
                new IntentFilter("images"));
        return mView;
    }


    private BroadcastReceiver mMessages = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            filePath=intent.getStringExtra("path");
            MY_FILE=new File(filePath);
            Glide.with(getActivity()).load(intent.getStringExtra("path")).into(imageView);
        }
    };


    private void saveComment() {
        String note = mEditText_note.getText().toString();
        mNote = new Note();
        mNote.setPropertyId(Fragment_Note_List.PROPERTY_ID);
        mNote.setDescription(note);
        mNote.setCommentType("text");
        mNote.setCommentTitle(mEditText_title.getText().toString());
        mNote.setPhoto("None");
        mNote.setUser(Activity_Login.mUser);
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
                            getActivity().finish();
                            mListener.onSave();
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

    private String MY_BUCKET="propertyinspector-userfiles-mobilehub-4404653";
    private String OBJECT_KEY="uploads/propertyinspector_image"+ SystemClock.currentThreadTimeMillis();
    private File MY_FILE;
    private ProgressDialog progress;
    private void savePhotoComment() {
        progress=new ProgressDialog(getActivity());
        progress.setMessage("Uploading Photo. Please wait.!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final String note = mEditText_note.getText().toString();
        mNote = new Note();
        mNote.setPropertyId(Fragment_Note_List.PROPERTY_ID);
        mNote.setDescription(note);
        mNote.setCommentType("photo");
        mNote.setCommentTitle(mEditText_title.getText().toString());
        mNote.setPhoto("None");
        mNote.setUser(Activity_Login.mUser);

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        TransferUtility transferUtility = new TransferUtility(s3, getActivity());
        final TransferObserver observer = transferUtility.upload(
                MY_BUCKET,     /* The bucket to upload to */
                OBJECT_KEY.concat(MY_FILE.getName().substring(MY_FILE.getName().length()-4)),    /* The key for the uploaded object */
                MY_FILE,        /* The file where the data to upload exists */
                CannedAccessControlList.PublicRead
        );


        observer.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                // do something


                if (TransferState.COMPLETED.equals(state)) {
                    if (progress.isShowing())progress.dismiss();
                    mNote.setPhoto(OBJECT_KEY.concat(MY_FILE.getName().substring(MY_FILE.getName().length() - 4)));
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
                                        getActivity().finish();
                                    }
                                });
                            }
                        };
                        Thread mythread = new Thread(runnable);
                        mythread.start();
                    }
                    else
                        Toast.makeText(getActivity(),"Empty post are not allowed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
                progress.setProgress(percentage);
                //Display percentage transfered to user
            }

            @Override
            public void onError(int id, Exception ex) {
                // do something
            }

        });


      //  mListener.onSaveComment();
    }
}
