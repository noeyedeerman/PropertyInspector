package sit374_team17.propertyinspector.Property;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

import sit374_team17.propertyinspector.Main.Fragment_Home;
import sit374_team17.propertyinspector.Main.Listener;
import sit374_team17.propertyinspector.Note.Fragment_Note_List;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.R;


public class Fragment_Property extends Fragment {

    private static final String ARG_PROPERTY = "property";

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;
private View mView;
    private Property mProperty;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private MenuItem mSearchItem;
    private MenuItem mPropertyItem, mNotesItem, mCriteriaItem;
    private Listener mListener;

    protected List<Note> mNoteList;
    protected DynamoDBMapper mapper;

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
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(ARG_PROPERTY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_property_holder, container, false);
        setHasOptionsMenu(true);
mNoteList = new ArrayList<>();
      //  loadNotes();
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) mView.findViewById(R.id.viewPager_property);

        mPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));


        return mView;
    }



    public void onBackPressed() {
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(0);
            mPropertyItem.setVisible(false);
            mNotesItem.setVisible(true);
            mCriteriaItem.setVisible(true);
        } else {
       mListener.popBackStack(true);

        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return Fragment_Property_Description.newInstance(mProperty);
                case 1:
                   return Fragment_Note_List.newInstance(mProperty);
                //case 2:
                   // return Fragment_Criteria.newInstance(mProperty);
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }


    void loadNotes(){
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
                scanExpression.addFilterCondition("CommentType",
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(new AttributeValue().withS("private")));
                mNoteList = mapper.scan(Note.class, scanExpression);
//                if (mNoteList.size() >= 0) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                          //  mCommentsAdapter = new Adapter_Notes(mListener);
//                            mCommentsAdapter.setNoteList(mNoteList);
//                            mRecyclerView.setAdapter(mCommentsAdapter);
//                        }
//                    });
//                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

//    private void saveComment(boolean isPublic) {
//        String description = editText_comment.getText().toString();
//        mDB_comments =new Note();
//        mDB_comments.setPropertyId(PROPERTY_ID);
//        mDB_comments.setDescription(description);
//        if (isPublic)mDB_comments.setCommentType("public");
//        else  mDB_comments.setCommentType("private");
//        Toast.makeText(getActivity(),"Submitting please wait",Toast.LENGTH_SHORT).show();
//        if (!description.equals(""))
//        {
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    //DynamoDB calls go here
//                    mapper.save(mDB_comments);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(),"Comment submitted successfully",Toast.LENGTH_SHORT).show();
//                            editText_comment.setText("");
//                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                            Fragment fragment_tabs = new Fragment_Tabs();
//                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                            transaction.add(R.id.tabHost_comments, fragment_tabs).commit();
//                        }
//                    });
//                }
//            };
//            Thread mythread = new Thread(runnable);
//            mythread.start();
//        }
//        else
//            Toast.makeText(getActivity(),"Empty post are not allowed",Toast.LENGTH_LONG).show();
//
//        mListener.onSaveComment();
//    }


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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);

        mPropertyItem = menu.findItem(R.id.action_property);
        mPropertyItem.setVisible(false);

        mNotesItem = menu.findItem(R.id.action_notes);
        mNotesItem.setVisible(true);

        mCriteriaItem = menu.findItem(R.id.action_criteria);
        mCriteriaItem.setVisible(true);

        mPropertyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                mPager.setCurrentItem(0);
                mNotesItem.setVisible(true);
                mCriteriaItem.setVisible(true);
                mPropertyItem.setVisible(false);
                return true;
            }
        });

        mNotesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                    mPager.setCurrentItem(1);
                    mNotesItem.setVisible(false);
                mCriteriaItem.setVisible(true);
                    mPropertyItem.setVisible(true);

                return true;
            }
        });

        mCriteriaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                    mPager.setCurrentItem(2);
                    mCriteriaItem.setVisible(false);
                mNotesItem.setVisible(true);
                    mPropertyItem.setVisible(true);
                return true;
            }
        });
    }






}
//private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getContext(),
//                        "sit374_team17.propertyinspector",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, 1);
//            }
//        }
//    }

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

//
//    private void handleBigCameraPhoto() {
//
//        if (mCurrentPhotoPath != null) {
//            // setPic();
//            galleryAddPic();
//            mCurrentPhotoPath = null;
//        }
//
//    }
//
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getActivity().sendBroadcast(mediaScanIntent);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            handleBigCameraPhoto();
//
//        }
//
//    }

//    // Some lifecycle callbacks so that the image can survive orientation change
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable("bitmap", mImageBitmap);
//        outState.putParcelableArrayList("bitmap", mImageBitmap);
//        //outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
//
//        super.onSaveInstanceState(outState);
//    }
////
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
//


