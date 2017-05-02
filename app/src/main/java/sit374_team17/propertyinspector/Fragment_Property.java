package sit374_team17.propertyinspector;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.columnCount;
import static android.app.Activity.RESULT_OK;


public class Fragment_Property extends Fragment {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;

    View mView;
    ViewPager mViewPager_property;
    Adapter_PropertySwipe mAdapter_slideShow;
    Adapter_Comments mAdapter_comments;
    TextView mStreetNumber, mStreetName, mCity, mState, mPostCode, mBedrooms, mBathrooms, mCars, mPrice, mDescription;
    Button button_post, button_save;
    ImageButton button_camera;
    EditText editText_comment;
    Fragment mFragment_tabs;
    Comment mComment;
    Drawable d;

    // List<Bitmap> bitmapArray = new ArrayList<>();
    Bitmap mHouse1, mHouse2, mHouse3, mHouse4, mHouse5;
    List<Bitmap> mPhotoList;
    int imageArray[];
    //Comment mComment;
    List<Comment> mCommentsList;
    DB_CommentHandler mDB_comments;
    private Listener mListener;
    private MenuItem mSearchItem;
    private ImageView mImageView;
    private String mCurrentPhotoPath;
    private Parcelable mImageBitmap;
    private Bitmap mhouse2;
    RecyclerView mRecyclerView;

    public Fragment_Property() {
    }


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
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property, container, false);
        setHasOptionsMenu(true);
        mViewPager_property = (ViewPager) mView.findViewById(R.id.viewPager_property);
        button_camera = (ImageButton) mView.findViewById(R.id.button_camera);
        button_post = (Button) mView.findViewById(R.id.button_post);
        button_save = (Button) mView.findViewById(R.id.button_save);


        editText_comment = (EditText) mView.findViewById(R.id.editText_comment);

        Fragment fragment_tabs = new Fragment_Tabs();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.tabHost_comments, fragment_tabs).commit();


        mPhotoList = new ArrayList<>();
        mCommentsList = new ArrayList<>();

      //  mCommentsList = mDB_comments.getAllComments();
        //  mAdapter = new Adapter_PropertySwipe(getContext());

        mAdapter_slideShow = new Adapter_PropertySwipe(getContext());

        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment(true);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment(false);
            }
        });



//initViews();
//        if (mCommentsList.size() >= 0) {
//            mAdapter_comments = new Adapter_Comments(mListener);
//            mAdapter_comments.setCommentList(mCommentsList);
//            mRecyclerView.setAdapter(mAdapter_comments);
//        }
        //  mAdapter_comments = new Adapter_Comments(mListener, getContext());
        // mAdapter_comments.setCommentList(mCommentsList);
        //  mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_comment);
        //  mRecyclerView.setAdapter(mAdapter_comments);

        populatePhotoList();

        mAdapter_slideShow.setPhotoList(mPhotoList);

        mViewPager_property.setAdapter(mAdapter_slideShow);


        // mAdapter_slideShow = new Adapter_PropertySwipe(getContext());

        mStreetNumber = (TextView) mView.findViewById(R.id.textView_streetNumber);
        mStreetName = (TextView) mView.findViewById(R.id.textView_streetName);
        mCity = (TextView) mView.findViewById(R.id.textView_city);
        mState = (TextView) mView.findViewById(R.id.textView_state);
        mPostCode = (TextView) mView.findViewById(R.id.textView_postCode);
        mBedrooms = (TextView) mView.findViewById(R.id.textView_bedrooms);
        mBathrooms = (TextView) mView.findViewById(R.id.textView_bathrooms);
        mCars = (TextView) mView.findViewById(R.id.textView_cars);
        // mPrice = (TextView) mView.findViewById(R.id.textView_price);


        mStreetNumber.setText(mProperty.getStreetNumber());
        mStreetName.setText(mProperty.getStreetName());
        mCity.setText(mProperty.getCity());
        mState.setText(mProperty.getState());
        mPostCode.setText(mProperty.getPostCode());
        mBedrooms.setText(mProperty.getBedrooms());
        mBathrooms.setText(mProperty.getBathrooms());
        mCars.setText(mProperty.getCars());
        //  mPrice.setText(mProperty.getPrice());


        return mView;
    }

    private void saveComment(boolean isPublic) {
        String description = editText_comment.getText().toString();

        if (mComment != null) {
            if (!description.isEmpty()) {
                if (mComment.getId() < 0) {
                    mComment.setDescription(description);
                        mComment.setIsPublic(isPublic);

                    // if (city.isEmpty()) city = empty;
                    mComment.setDescription(description);
                    mDB_comments.addComment(mComment);
                } else {
                    //  mProperty.set_address(address);
                    //  mDB_properties.updateProperty(mProperty);
                }
                  mListener.onSaveComment();
            }
        }
    }


    private void initViews() {
//      mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_comment);
//       mRecyclerView.setHasFixedSize(true);
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
//
//
//        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void populatePhotoList() {
        mHouse1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.house1);
        mHouse2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.house2);
        mHouse3 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.house3);
        mHouse4 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.house4);
        mHouse5 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.house5);

        mPhotoList.add(mHouse1);
        mPhotoList.add(mHouse2);
        mPhotoList.add(mHouse3);
        mPhotoList.add(mHouse4);
        mPhotoList.add(mHouse5);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        MenuItem camera = menu.findItem(R.id.action_camera);
//        camera.setVisible(false);
//
//        camera.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                dispatchTakePictureIntent();
//
//
//                return true;
//            }
//        });

        mSearchItem = menu.findItem(R.id.action_search);
        mSearchItem.setVisible(false);


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "sit374_team17.propertyinspector",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mViewPager_property.getWidth();
        int targetH = mViewPager_property.getHeight();


        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mPhotoList.add(bitmap);


        //  mViewPager_property..setImageBitmap(bitmap);
        // mPhotoList.add(bitmap);
        //  mComment.setPhoto(bitmap);
        //  mDB_comments.addComment(mComment);
//
//        mCommentsList = mDB_comments.getAllComments();
//
//        for(Comment mComment : mCommentsList){
//            mPhotoList.add(mComment.getPhoto());
//        }

        mAdapter_slideShow.setPhotoList(mPhotoList);


    }


    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            handleBigCameraPhoto();

        }

    }

//    // Some lifecycle callbacks so that the image can survive orientation change
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable("bitmap", mImageBitmap);
//        outState.putParcelableArrayList("bitmap", mImageBitmap);
//        //outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
//
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        mImageBitmap = savedInstanceState.getParcelable("bitmap");
//
//        mImageView.setImageBitmap(mImageBitmap);
//      //  mImageView.setVisibility(
//        //        savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ?
//       //                 ImageView.VISIBLE : ImageView.INVISIBLE
//        //);
//    }


}


