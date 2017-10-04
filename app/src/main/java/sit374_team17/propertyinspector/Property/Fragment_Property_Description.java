package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import sit374_team17.propertyinspector.Comment;
import sit374_team17.propertyinspector.Fragment_Tabs;
import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Note.Adapter_Notes;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;
import sit374_team17.propertyinspector.User.Activity_Login;
import sit374_team17.propertyinspector.Walkthrough.Activity_Walkthrough;

import static android.app.Activity.RESULT_OK;

public class Fragment_Property_Description extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PROPERTY = "property";
    private static final String ARG_NOTELIST = "noteList";

    private Property mProperty;
    protected DynamoDBMapper mapper;
    protected List<Note> mNoteList;
    protected List<Note> mTempList;
    Adapter_Notes mCommentsAdapter;
    View mView;
    ViewPager mViewPager_property;
    Adapter_PropertySwipe mAdapter_slideShow;
    Adapter_Notes mAdapter_note;
    TextView mTextView_description, mTextView_address, mTextView_city, mTextView_state, mTextView_postCode, mTextView_bedrooms, mTextView_bathrooms, mTextView_cars, mTextView_price;
    Button button_post, button_save;
    FloatingActionButton mFab_walktrhough;
    EditText editText_comment;
    Fragment mFragment_tabs;
    Comment mComment;
    Drawable d;

    GoogleMap mGoogleMap;
    MapView mMapView;

    // List<Bitmap> bitmapArray = new ArrayList<>();
    Bitmap mHouse1, mHouse2, mHouse3, mHouse4, mHouse5;
    List<String> mPhotoList;
    int imageArray[];
    //Comment mComment;
    List<Comment> mCommentsList;
    Note mDB_comments;
    private Listener mListener;
    private MenuItem mSearchItem;
    private ImageView mImageView;
    private String mCurrentPhotoPath;
    private Parcelable mImageBitmap;
    private Bitmap mhouse2;
    RecyclerView mRecyclerView;

    protected static String PROPERTY_ID = "";
    private String string_address;
    private ImageView imageView;


    public Fragment_Property_Description() {
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

    public static Fragment_Property_Description newInstance(Property property) {
        Fragment_Property_Description fragment = new Fragment_Property_Description();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROPERTY, property);
        //  args.putParcelable(ARG_NOTELIST, (Parcelable) noteList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
            // mNoteList = getArguments().getParcelable(ARG_NOTELIST);
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property_description, container, false);

        // setHasOptionsMenu(true);

        mViewPager_property = (ViewPager) mView.findViewById(R.id.viewPager_property);
        // button_camera = (ImageButton) mView.findViewById(R.id.button_camera);
        //  button_post = (Button) mView.findViewById(R.id.button_post);
        mFab_walktrhough = (FloatingActionButton) mView.findViewById(R.id.fab_walkthrough);
imageView = mView.findViewById(R.id.imageView);
        //   if(googleServicesAvailable()){
        //      Toast.makeText(getContext(), "Google services available", Toast.LENGTH_SHORT).show();
//initMap();
        // }
imageView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        mListener.goTo_NoteEditActivity(new Note(), "do");
        return true;
    }
});


        //  editText_comment = (EditText) mView.findViewById(R.id.editText_comment);

        // Fragment fragment_tabs = new Fragment_Tabs();
        //  FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.tabHost_comments, fragment_tabs).commit();


        mPhotoList = new ArrayList<>();
        mNoteList = new ArrayList<>();
        PROPERTY_ID = mProperty.getId();
        initViews();


        mFab_walktrhough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Walkthrough.class);
                startActivity(intent);
            }
        });


//        button_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveComment(true);
//            }
//        });
//
//        button_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveComment(false);
//            }
//        });
//


//initViews();
//        if (mCommentsList.size() >= 0) {
//            mAdapter_note = new Adapter_Notes(mListener);
//            mAdapter_note.setCommentList(mCommentsList);
//            mRecyclerView.setAdapter(mAdapter_note);
//        }
        //  mAdapter_note = new Adapter_Notes(mListener, getContext());
        // mAdapter_note.setCommentList(mCommentsList);
        //  mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_comment);
        //  mRecyclerView.setAdapter(mAdapter_note);


        //  mAdapter_slideShow.setPhotoList(mPhotoList);
        //  mViewPager_property.setAdapter(mAdapter_slideShow);


        // mAdapter_slideShow = new Adapter_PropertySwipe(getContext());

        mTextView_description = mView.findViewById(R.id.textView_description);
        mTextView_price = mView.findViewById(R.id.textView_price);
        mTextView_address = (TextView) mView.findViewById(R.id.textView_address);
        mTextView_city = mView.findViewById(R.id.textView_city);
        mTextView_state = mView.findViewById(R.id.textView_state);
        mTextView_postCode = mView.findViewById(R.id.textView_postCode);
        mTextView_bedrooms = mView.findViewById(R.id.textView_bedrooms);
        mTextView_bathrooms = mView.findViewById(R.id.textView_bathrooms);
        mTextView_cars = mView.findViewById(R.id.textView_cars);

        if (mProperty.getDescription() != null && !Objects.equals(mProperty.getDescription(), "None"))
            mTextView_description.setText(mProperty.getDescription());

        if (mProperty.getPrice() != null && mProperty.getPrice().get(0) != 0) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            numberFormat.setMaximumFractionDigits(0);
            ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);
            mTextView_price.setText(numberFormat.format(mProperty.getPrice().get(0)));
        }

        if (mProperty.getBedrooms() != null && mProperty.getBedrooms().size() > 0 && !Objects.equals(mProperty.getBedrooms().get(0), ""))
            mTextView_bedrooms.setText(String.valueOf(mProperty.getBedrooms().get(0)));

        if (mProperty.getBathrooms() != null && mProperty.getBathrooms().size() > 0 && !Objects.equals(mProperty.getBathrooms().get(0), ""))
            mTextView_bathrooms.setText(String.valueOf(mProperty.getBathrooms().get(0)));

        if (mProperty.getCars() != null && mProperty.getCars().size() > 0 && !Objects.equals(mProperty.getCars().get(0), ""))
            mTextView_cars.setText(String.valueOf(mProperty.getCars().get(0)));

        if (mProperty.getAddress() != null && !Objects.equals(mProperty.getAddress(), "None"))
            mTextView_address.setText(mProperty.getAddress() + ",");

        if (mProperty.getCity() != null && !Objects.equals(mProperty.getCity(), "None"))
            mTextView_city.setText(mProperty.getCity() + ",");

        if (mProperty.getState().size() > 0 && !Objects.equals(mProperty.getState().get(0), "None"))
            mTextView_state.setText(mProperty.getState().get(0) + ",");

        if (mProperty.getPostCode() != null && mProperty.getPostCode() > 0)
            mTextView_postCode.setText(String.valueOf(mProperty.getPostCode()));


        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);

        //Opens up Inspection Notes page/ inspection criteria
//        Button button_inspectionNotes = (Button)mView.findViewById(R.id.button_goToInspectionNotes);
//        button_inspectionNotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create new fragment and transaction
//                Fragment newFragment = new Fragment_Criteria();
//                // consider using Java coding conventions (upper first char class names!!!)
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack
//                transaction.replace(R.id.content_main, newFragment);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commit();
//
//            }
//        });
        //string_address = "New York";
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue of Liberty").snippet("I hope to go there someday"));


        //String location = "41 Cathcart Street Maidstone";
        String location = mProperty.getAddress() + ", " + mProperty.getCity();


        List<Address> addressList = null;

        if (location != null && !location.equals("")) {
            Geocoder geocoder = new Geocoder(getContext());
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = null;

            if (addressList != null && addressList.size() != 0) {
                address = addressList.get(0);
                LatLng latLng = null;
                if (address != null) {
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
                }
                if (latLng != null) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));

                }
                CameraPosition myHouse = null;
                if (address != null) {
                    myHouse = CameraPosition.builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(16).build();
                }
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(myHouse));
            }


            googleMap.setBuildingsEnabled(true);
            googleMap.setContentDescription("cool description?");
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(false);

            //       CameraPosition myHouse = CameraPosition.builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(16).bearing(0).tilt(45).build();

//googleMap.getUiSettings().isZoomControlsEnabled();
            googleMap.getUiSettings().setScrollGesturesEnabled(false);
            //googleMap.setMaxZoomPreference(25);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Toast.makeText(getContext(), "Open Google Maps", Toast.LENGTH_SHORT).show();
                }
            });

            //CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.689247, -74.044502)).zoom(16).bearing(0).tilt(45).build();

            // googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        }
    }

//
//
//    private void initMap() {
//        MapView mapView = (MapView) mView.findViewById(R.id.mapView);
//    }
//
//    public boolean googleServicesAvailable() {
//        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
//        int isAvailable = api.isGooglePlayServicesAvailable(getContext());
//        if (isAvailable == ConnectionResult.SUCCESS) {
//            return true;
//        } else if (api.isUserResolvableError(isAvailable)){
//            Dialog dialog = api.getErrorDialog(getActivity(), isAvailable, 0);
//            dialog.show();
//        } else {
//            Toast.makeText(getContext(), "Can't connect to play services", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//
//    }

    private void saveComment(boolean isPublic) {
        String description = editText_comment.getText().toString();
        mDB_comments = new Note();
        mDB_comments.setPropertyId(PROPERTY_ID);
        mDB_comments.setDescription(description);
        if (isPublic) mDB_comments.setCommentType("public");
        else mDB_comments.setCommentType("private");
        Toast.makeText(getActivity(), "Submitting please wait", Toast.LENGTH_SHORT).show();
        if (!description.equals("")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    //DynamoDB calls go here
                    mapper.save(mDB_comments);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Comment submitted successfully", Toast.LENGTH_SHORT).show();
                            editText_comment.setText("");
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                            Fragment fragment_tabs = new Fragment_Tabs();
                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                            //  transaction.add(R.id.tabHost_comments, fragment_tabs).commit();
                        }
                    });
                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();
        } else
            Toast.makeText(getActivity(), "Empty post are not allowed", Toast.LENGTH_LONG).show();

        mListener.onSaveComment();
    }

    //
    private void initViews() {
//        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_commentPrivate);
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager;
//
//
//        layoutManager = new LinearLayoutManager(getContext()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//        mRecyclerView.setLayoutManager(layoutManager);
//
//
//       Note note1 = new Note("0", "public", "Cool Title 1", "This is a note test 1", "1", mProperty.getPhoto());
//       Note note2 = new Note("1", "public", "Cool Title 2", "This is a note test 2", "1", mProperty.getPhoto());
//       Note note3 = new Note("2", "public", "Cool Title 3", "This is a note test 3", "1", mProperty.getPhoto());
//
//       mNoteList.add(note1);
//       mNoteList.add(note2);
//       mNoteList.add(note3);


//       mAdapter_slideShow = new Adapter_PropertySwipe(getContext());
//       mAdapter_slideShow.setNoteList(mNoteList);
//       //   mAdapter_slideShow.setPhotoList(mPhotoList);
//       mViewPager_property.setAdapter(mAdapter_slideShow);
//


        // mCommentsList = mDB_comments.getAllComments();
        //  mAdapter = new Adapter_PropertySwipe(getContext());


        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        mapper = new DynamoDBMapper(ddbClient);
        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                scanExpression.addFilterCondition("PropertyID", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withS(PROPERTY_ID)));
                scanExpression.addFilterCondition("Username",
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(new AttributeValue().withS(Activity_Login.mUser)));
                mTempList = mapper.scan(Note.class, scanExpression);
                //  mNoteList = mapper.scan(Note.class, scanExpression);
                if (mTempList.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter_slideShow = new Adapter_PropertySwipe(getContext(), mListener);
                            for (Note note : mTempList) {
                                if (!"text".equals(note.getCommentType())) {
                                    mNoteList.add(note);
                                }
                            }
imageView.setVisibility(View.GONE);
                            mAdapter_slideShow.setNoteList(mNoteList);
                            mViewPager_property.setAdapter(mAdapter_slideShow);


                        }
                    });
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();


        // mAdapter_slideShow.setNoteList(mNoteList);
        //  mViewPager_property.setAdapter(mAdapter_slideShow);

    }


//
//    private void initViews() {
////      mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_comment);
////       mRecyclerView.setHasFixedSize(true);
////        RecyclerView.LayoutManager layoutManager;
////
////
////
////        layoutManager = new LinearLayoutManager(getContext()){
////            @Override
////            public boolean canScrollVertically() {
////                return false;
////            }
////        };
////
////
////
////        mRecyclerView.setLayoutManager(layoutManager);
//    }
////
//   @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//       super.onCreateOptionsMenu(menu, inflater);
////
////        MenuItem camera = menu.findItem(R.id.action_camera);
////        camera.setVisible(false);
////
////        camera.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
////
////            @Override
////            public boolean onMenuItemClick(MenuItem item) {
////                dispatchTakePictureIntent();
////
////
////                return true;
////            }
////        });
////
//        mSearchItem = menu.findItem(R.id.action_search);
//        mSearchItem.setVisible(false);
////
////
//   }

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

 /*   private void setPic() {
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


    }*/


    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            // setPic();
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
