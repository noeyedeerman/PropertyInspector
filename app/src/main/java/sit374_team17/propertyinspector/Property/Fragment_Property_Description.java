package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
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
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sit374_team17.propertyinspector.Comment;
import sit374_team17.propertyinspector.Fragment_Tabs;
import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Note.Adapter_Note;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;
import sit374_team17.propertyinspector.Walkthrough.Activity_Walkthrough;

import static android.app.Activity.RESULT_OK;

public class Fragment_Property_Description extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PROPERTY = "property";

    private Property mProperty;
    protected DynamoDBMapper mapper;
    protected List<Note> result;
    Adapter_Note mCommentsAdapter;
    View mView;
    ViewPager mViewPager_property;
    Adapter_PropertySwipe mAdapter_slideShow;
    Adapter_Note mAdapter_note;
    TextView mStreetNumber, mStreetName, mCity, mState, mPostCode, mBedrooms, mBathrooms, mCars, mPrice, mDescription, propertyInspectionDate;
    Button button_post, button_save;
    Button mButton_buy;
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
        //  mDB_comments = new DB_CommentHandler(getContext());
        // if (getArguments() != null) {
        //     mComment = getArguments().getParcelable(ARG_PROPERTY);
        //  }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property_description, container, false);

        // setHasOptionsMenu(true);

        mViewPager_property = (ViewPager) mView.findViewById(R.id.viewPager_property);
        // button_camera = (ImageButton) mView.findViewById(R.id.button_camera);
        //  button_post = (Button) mView.findViewById(R.id.button_post);
      mButton_buy = (Button) mView.findViewById(R.id.button_buyInfo);

        //   if(googleServicesAvailable()){
        //      Toast.makeText(getContext(), "Google services available", Toast.LENGTH_SHORT).show();
//initMap();
        // }


        //  editText_comment = (EditText) mView.findViewById(R.id.editText_comment);

        // Fragment fragment_tabs = new Fragment_Tabs();
        //  FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.tabHost_comments, fragment_tabs).commit();


        mPhotoList = new ArrayList<>();
        mCommentsList = new ArrayList<>();

        // mCommentsList = mDB_comments.getAllComments();
        //  mAdapter = new Adapter_PropertySwipe(getContext());

        mAdapter_slideShow = new Adapter_PropertySwipe(getContext());

        // initViews();
        mButton_buy.setOnClickListener(new View.OnClickListener() {
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
//            mAdapter_note = new Adapter_Note(mListener);
//            mAdapter_note.setCommentList(mCommentsList);
//            mRecyclerView.setAdapter(mAdapter_note);
//        }
        //  mAdapter_note = new Adapter_Note(mListener, getContext());
        // mAdapter_note.setCommentList(mCommentsList);
        //  mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView_comment);
        //  mRecyclerView.setAdapter(mAdapter_note);

        mPhotoList.add(mProperty.getPhoto());

        mAdapter_slideShow.setPhotoList(mPhotoList);
        mViewPager_property.setAdapter(mAdapter_slideShow);


        // mAdapter_slideShow = new Adapter_PropertySwipe(getContext());

        mStreetNumber = (TextView) mView.findViewById(R.id.textView_streetNumber);
        mStreetName = (TextView) mView.findViewById(R.id.textView_streetName);
        mCity = (TextView) mView.findViewById(R.id.textView_city);
        mState = (TextView) mView.findViewById(R.id.textView_address);
        mPostCode = (TextView) mView.findViewById(R.id.textView_postCode);
        mBedrooms = (TextView) mView.findViewById(R.id.textView_bedrooms);
        mBathrooms = (TextView) mView.findViewById(R.id.textView_bathrooms);
        mCars = (TextView) mView.findViewById(R.id.textView_cars);
        //propertyInspectionDate = (TextView) mView.findViewById(R.id.propertyInspectionDate);
        // mPrice = (TextView) mView.findViewById(R.id.textView_price);


        mStreetNumber.setText(String.valueOf(mProperty.getStreetNumber()));
        mStreetName.setText(String.valueOf(mProperty.getStreetName()));
        mCity.setText(mProperty.getCity());
        //  mState.setText(mProperty.getState());
        mState.setText(mProperty.getState().get(0));
        mPostCode.setText(String.valueOf(mProperty.getPostCode()));
        mBedrooms.setText(String.valueOf(mProperty.getBedrooms().get(0)));
        mBathrooms.setText(String.valueOf(mProperty.getBathrooms().get(0)));
        mCars.setText(String.valueOf(mProperty.getCars().get(0)));
        if (mProperty.getInspection_date() != null)
            propertyInspectionDate.setText(mProperty.getInspection_date());
        //  mPrice.setText(mProperty.getPrice());
        PROPERTY_ID = mProperty.getId();
        //  mPrice.setText(mProperty.getPrice());

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
//        MapsInitializer.initialize(getContext());
//
//        mGoogleMap = googleMap;
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        //googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue of Liberty").snippet("I hope to go there someday"));
//
//
//
//        //String location = "41 Cathcart Street Maidstone";
//        String location = mProperty.getStreetNumber() + " " + mProperty.getStreetName() + ", " + mProperty.getCity();
//
//
//        List<Address> addressList = null;
//
//        if (location != null || !location.equals("")) {
//            Geocoder geocoder = new Geocoder(getContext());
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Address address = null;
//
//            if (addressList != null) {
//                address = addressList.get(0);
//                LatLng latLng = null;
//                if (address != null) {
//                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                }
//                if (latLng != null) {
//                    googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
//
//                }
//                CameraPosition myHouse = null;
//                if (address != null) {
//                    myHouse = CameraPosition.builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(16).build();
//                }
//                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(myHouse));
//            }
//
//
//
//
//googleMap.setBuildingsEnabled(true);
//            googleMap.setContentDescription("cool description?");
//            googleMap.getUiSettings().setMapToolbarEnabled(true);
//            googleMap.getUiSettings().setTiltGesturesEnabled(false);
//
//     //       CameraPosition myHouse = CameraPosition.builder().target(new LatLng(address.getLatitude(), address.getLongitude())).zoom(16).bearing(0).tilt(45).build();
//
////googleMap.getUiSettings().isZoomControlsEnabled();
//            googleMap.getUiSettings().setScrollGesturesEnabled(false);
//            //googleMap.setMaxZoomPreference(25);
//            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    Toast.makeText(getContext(), "Open Google Maps", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            //CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.689247, -74.044502)).zoom(16).bearing(0).tilt(45).build();
//
//            // googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
//        }
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
//    private void initViews() {
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
//                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(Fragment_Home.credentialsProvider);
//        ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
//        mapper = new DynamoDBMapper(ddbClient);
//        Runnable runnable = new Runnable() {
//            public void run() {
//                //DynamoDB calls go here
//                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//                scanExpression.addFilterCondition("PropertyID",new Condition()
//                        .withComparisonOperator(ComparisonOperator.EQ)
//                        .withAttributeValueList(new AttributeValue().withS(Fragment_Property_Description.PROPERTY_ID)));
//                scanExpression.addFilterCondition("CommentType",
//                        new Condition()
//                                .withComparisonOperator(ComparisonOperator.EQ)
//                                .withAttributeValueList(new AttributeValue().withS("private")));
//                result = mapper.scan(Note.class, scanExpression);
//                if (result.size() >= 0) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mCommentsAdapter = new Adapter_Note(mListener);
//                            mCommentsAdapter.setCommentList(result);
//                            mRecyclerView.setAdapter(mCommentsAdapter);
//                        }
//                    });
//                }
//            }
//        };
//        Thread mythread = new Thread(runnable);
//        mythread.start();
//    }


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