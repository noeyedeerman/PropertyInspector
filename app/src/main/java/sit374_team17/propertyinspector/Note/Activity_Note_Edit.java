package sit374_team17.propertyinspector.Note;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;

public class Activity_Note_Edit extends AppCompatActivity {

    Property mProperty;
    Note mNote;
    private MenuItem mCameraItem;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            mProperty = getIntent().getExtras().getParcelable("property");
            mNote = getIntent().getParcelableExtra("note");

            if (extras != null)
            {
                String method = extras.getString("methodName");

                if (method.equals("do")) {
                    dispatchTakePictureIntent();
                }
            }
        }


goTo_EditNoteFragment(mNote);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void goTo_EditNoteFragment(Note note) {
        Fragment_Note_Edit fragment = Fragment_Note_Edit.newInstance(note);
        replaceFragment(fragment, "Fragment_Note_Edit");


    }

    private void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //    fragmentTransaction.addToBackStack(null);

            fragmentTransaction.replace(R.id.content_note_edit, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note, menu);

        mCameraItem = menu.findItem(R.id.action_camera);

        mCameraItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dispatchTakePictureIntent();

                return false;
            }
        });

        return true;
    }

    private void openCamera(){

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "sit374_team17.propertyinspector",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


//
//   private void setPic() {
//        // Get the dimensions of the View
//       int targetW = mViewPager_property.getWidth();
//        int targetH = mViewPager_property.getHeight();
//
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        mPhotoList.add(bitmap);
//
//
//        //  mViewPager_property..setImageBitmap(bitmap);
//        // mPhotoList.add(bitmap);
//        //  mComment.setPhoto(bitmap);
//        //  mDB_comments.addComment(mComment);
////
////        mCommentsList = mDB_comments.getAllComments();
////
////        for(Comment mComment : mCommentsList){
////            mPhotoList.add(mComment.getPhoto());
////        }
//
//        mAdapter_slideShow.setPhotoList(mPhotoList);
//
//
//    }



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
        this.sendBroadcast(mediaScanIntent);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            handleBigCameraPhoto();
//
//        }
//
//    }

}
